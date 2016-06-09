package projectx.itgo.com.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import projectx.itgo.com.APIServices.CustomerService;
import projectx.itgo.com.R;
import projectx.itgo.com.adapters.CustomerAdapter;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.utilities.DividerItemDecoration;
import projectx.itgo.com.utilities.ResourceURI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCustomer extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView customersRecyclerView;
    RelativeLayout customerEmptyRelativeLayout;
    CustomerAdapter customerAdapter;
    FloatingActionButton floatingActionButton;
    List<String> customersList = new ArrayList<>();
    List<Customer> customers;

    public FragmentCustomer() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_add_customer);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Fab clicked", Toast.LENGTH_SHORT).show();
            }
        });
        customersRecyclerView = (RecyclerView) view.findViewById(R.id.customers_recycler_view);
        customerEmptyRelativeLayout = (RelativeLayout) view.findViewById(R.id.customer_empty_relative_layout);

        customersRecyclerView.setHasFixedSize(true);
        customersRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ResourceURI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomerService customerService = retrofit.create(CustomerService.class);

        Call<List<Customer>> call = customerService.getRegularCustomers();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    customers = response.body();
                    if (!(customers.size() > 0)) {
                        customersRecyclerView.setVisibility(View.GONE);
                        customerEmptyRelativeLayout.setVisibility(View.VISIBLE);
                    } else {
                        customersRecyclerView.setVisibility(View.VISIBLE);
                        customerEmptyRelativeLayout.setVisibility(View.GONE);
                        customerAdapter = new CustomerAdapter(customers, getActivity());
                        customersRecyclerView.setAdapter(customerAdapter);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.customers_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        customerAdapter.setFilter(customers);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Customer> filteredModelList = filter(customers, newText);
        customerAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Customer> filter(List<Customer> customers, String query) {
        query = query.toLowerCase();

        final List<Customer> filteredCustomerList = new ArrayList<>();
        for (Customer customer : customers) {
            final String text = customer.getFirstName().toLowerCase();
            if (text.contains(query)) {
                filteredCustomerList.add(customer);
            }
        }
        return filteredCustomerList;
    }
}
