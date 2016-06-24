package projectx.itgo.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import projectx.itgo.com.APIServices.CustomerService;
import projectx.itgo.com.R;
import projectx.itgo.com.adapters.FindCustomerAdapter;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.utilities.CustomRecyclerClickListener;
import projectx.itgo.com.utilities.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFindCustomer extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private CustomerService customerService;
    private Call<List<Customer>> callCustomers;
    private Button newCustomerButton, randomCustomerButton;
    private RecyclerView findCustomerRecyclerView;
    private List<Customer> customers;
    private FindCustomerAdapter findCustomerAdapter;
    private Toolbar toolbar;
    private ProgressWheel progressWheel;
    private TextView emptyTextView;
    private List<Customer> filteredCustomers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_customer);


//        Get UI elements

        newCustomerButton = (Button) findViewById(R.id.find_customer_new_button);
        randomCustomerButton = (Button) findViewById(R.id.find_customer_random_button);
        findCustomerRecyclerView = (RecyclerView) findViewById(R.id.find_customer_recyclerview);
        toolbar = (Toolbar) findViewById(R.id.find_customer_toolbar);
        progressWheel = (ProgressWheel) findViewById(R.id.find_customer_progress_wheel);
        emptyTextView = (TextView) findViewById(R.id.find_customer_empty_text_view);


//        setting the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.find_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        Setting on click listeners for add customer
        newCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewCustomer = new Intent(ActivityFindCustomer.this, ActivityAddCustomer.class);
                intentNewCustomer.putExtra("flag", "insertCustomerInProcess");
                startActivity(intentNewCustomer);
            }
        });

//        setting on click listener for random customer
        randomCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRandomCustomer = new Intent(ActivityFindCustomer.this, ActivityFindProduct.class);
                startActivity(intentRandomCustomer);
            }
        });


//        recycler view settings and layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFindCustomer.this);
        findCustomerRecyclerView.setLayoutManager(linearLayoutManager);


//        onTouch for recycler view
        findCustomerRecyclerView.addOnItemTouchListener(new CustomRecyclerClickListener(ActivityFindCustomer.this, new CustomRecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(ActivityFindCustomer.this, ActivityFindProduct.class);
                intent.putExtra("customer", filteredCustomers.get(position));
                startActivity(intent);
            }
        }));


    }


    //    keeping this step in onResume because the recycler view must refresh the content every time one opens this activity.
    @Override
    protected void onResume() {
        super.onResume();
        //        getting customer data from server and setting progress wheel and if there is data then setting up the adapter
        customerService = ServiceGenerator.createService(CustomerService.class, ActivityFindCustomer.this);
        callCustomers = customerService.getRegularCustomers();

        if (progressWheel.getVisibility() == View.GONE) {
            progressWheel.setVisibility(View.VISIBLE);
        }
        if (findCustomerRecyclerView.getVisibility() == View.VISIBLE) {
            findCustomerRecyclerView.setVisibility(View.GONE);
        }
        callCustomers.clone().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    customers = null;
                    customers = response.body();
                    filteredCustomers.clear();
                    filteredCustomers.addAll(customers);
                    if (customers.isEmpty() && emptyTextView.getVisibility() == View.GONE) {
                        emptyTextView.setVisibility(View.VISIBLE);
                    } else {
                        findCustomerAdapter = new FindCustomerAdapter(customers);
                        findCustomerRecyclerView.setAdapter(findCustomerAdapter);
                    }

                } else {
                    Toast.makeText(ActivityFindCustomer.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                }

                if (progressWheel.getVisibility() == View.VISIBLE) {
                    progressWheel.setVisibility(View.GONE);
                }
                if (findCustomerRecyclerView.getVisibility() == View.GONE) {
                    findCustomerRecyclerView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

                Toast.makeText(ActivityFindCustomer.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                if (progressWheel.getVisibility() == View.VISIBLE) {
                    progressWheel.setVisibility(View.GONE);
                }
                if (findCustomerRecyclerView.getVisibility() == View.GONE) {
                    findCustomerRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customers_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(ActivityFindCustomer.this);

        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        findCustomerAdapter.setFilter(customers);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Customer> filteredCustomers = filter(customers, newText);
        findCustomerAdapter.setFilter(filteredCustomers);
        return true;
    }

    private List<Customer> filter(List<Customer> customerList, String newText) {

        newText = newText.toLowerCase();
        filteredCustomers.clear();
        for (Customer customer : customerList) {
            final String text = customer.getFirstName().toLowerCase();
            if (text.contains(newText)) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }

}
