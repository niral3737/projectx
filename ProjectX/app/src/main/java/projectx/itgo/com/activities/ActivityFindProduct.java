package projectx.itgo.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import projectx.itgo.com.APIServices.ProductService;
import projectx.itgo.com.R;
import projectx.itgo.com.adapters.FindProductAdapter;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.models.Product;
import projectx.itgo.com.utilities.CustomRecyclerClickListener;
import projectx.itgo.com.utilities.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFindProduct extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Button newProductButton, randomProductButton;
    private Customer customer;
    private Call<List<Product>> productCall;
    private RecyclerView findProductRecyclerView;
    private Toolbar toolbar;
    private ProgressWheel progressWheel;
    private TextView emptyTextView;
    private List<Product> filteredProducts = new ArrayList<>();
    private List<Product> products;
    private ProductService productService;
    private FindProductAdapter findProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);

        Intent intent = getIntent();
        customer = intent.getParcelableExtra("customer");

//        Getting all UI elements
        newProductButton = (Button) findViewById(R.id.find_product_new_button);
        randomProductButton = (Button) findViewById(R.id.find_product_random_button);
        findProductRecyclerView = (RecyclerView) findViewById(R.id.find_product_recyclerview);
        toolbar = (Toolbar) findViewById(R.id.find_product_toolbar);
        progressWheel = (ProgressWheel) findViewById(R.id.find_product_progress_wheel);
        emptyTextView = (TextView) findViewById(R.id.find_product_empty_text_view);

//        setting the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.find_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        setting on click of new product button
        newProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityFindProduct.this, ActivityAddProduct.class);
                intent.putExtra("status", "addProductInProcess");
                intent.putExtra("customer", customer);
                startActivity(intent);
            }
        });

//        setting on click for random customer
        randomProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO on click random product
                Intent intent1 = new Intent(ActivityFindProduct.this, ActivityAddTransactionDetails.class);
                intent1.putExtra("customer", customer);
                startActivity(intent1);
            }
        });

//        recycler view setting and layout manager

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFindProduct.this);
        findProductRecyclerView.setLayoutManager(linearLayoutManager);


//        on touch for recycler view items

        findProductRecyclerView.addOnItemTouchListener(new CustomRecyclerClickListener(ActivityFindProduct.this, new CustomRecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent2 = new Intent(ActivityFindProduct.this, ActivityAddTransactionDetails.class);
                intent2.putExtra("customer", customer);
                intent2.putExtra("product", filteredProducts.get(position));
                startActivity(intent2);
            }
        }));

    }

    //    keeping this step in onResume because the recycler view must refresh the content every time one opens this activity.
    @Override
    protected void onResume() {
        super.onResume();
        //        getting customer data from server and setting progress wheel and if there is data then setting up the adapter
        productService = ServiceGenerator.createService(ProductService.class, ActivityFindProduct.this);
        productCall = productService.getProducts();

        if (progressWheel.getVisibility() == View.GONE) {
            progressWheel.setVisibility(View.VISIBLE);
        }
        if (findProductRecyclerView.getVisibility() == View.VISIBLE) {
            findProductRecyclerView.setVisibility(View.GONE);
        }
        productCall.clone().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    products = null;
                    products = response.body();
                    filteredProducts.clear();
                    filteredProducts.addAll(products);

                    if (products.isEmpty() && emptyTextView.getVisibility() == View.GONE) {
                        emptyTextView.setVisibility(View.VISIBLE);
                    } else {
                        findProductAdapter = new FindProductAdapter(products);
                        findProductRecyclerView.setAdapter(findProductAdapter);
                    }
                } else {
                    Toast.makeText(ActivityFindProduct.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                }

                if (progressWheel.getVisibility() == View.VISIBLE) {
                    progressWheel.setVisibility(View.GONE);
                }
                if (findProductRecyclerView.getVisibility() == View.GONE) {
                    findProductRecyclerView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ActivityFindProduct.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                if (progressWheel.getVisibility() == View.VISIBLE) {
                    progressWheel.setVisibility(View.GONE);
                }
                if (findProductRecyclerView.getVisibility() == View.GONE) {
                    findProductRecyclerView.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customers_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(ActivityFindProduct.this);

        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        findProductAdapter.setFilter(products);
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
        filter(products, newText);
        findProductAdapter.setFilter(filteredProducts);
        return true;
    }

    private void filter(List<Product> productList, String newText) {
        newText = newText.toLowerCase();
        filteredProducts.clear();
        for (Product product : productList) {
            final String name = product.getName().toLowerCase();
            final String code = product.getCode().toLowerCase();
            final String company = product.getCompany().toLowerCase();
            final String type = product.getType().toLowerCase();
            final String model = product.getModel().toLowerCase();
            final String quantity = product.getQuantity().toString().toLowerCase();
            final String price = product.getPrice().toString().toLowerCase();
            boolean flag = false;
            if (name.contains(newText) || code.contains(newText) || company.contains(newText)
                    || type.contains(newText) || model.contains(newText) || quantity.contains(newText)
                    || price.contains(newText)) {
                filteredProducts.add(product);
            }
            if(code.contains(newText))
                flag = true;
            if (company.contains(newText))
                flag = true;

        }
    }

}
