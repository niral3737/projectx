package projectx.itgo.com.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import projectx.itgo.com.APIServices.CustomerService;
import projectx.itgo.com.R;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.utilities.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityDisplayCustomer extends AppCompatActivity {

    TextView nameTextView, phoneNumberTextView, balanceTextView, comapanyNameTextView, addressTextView, emailTextView;
    RelativeLayout phoneNumberLinearLayout, balanceLinearLayout, companyNameLinearLayout, addressLinearLayout, emailLinearLayout;
    Toolbar toolbar;
    private Customer customer;
    ProgressWheel displayCustomerProgressWheel;
    CustomerService customerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_customer);
        Intent intent = getIntent();
        customer = intent.getParcelableExtra("customer");
        toolbar = (Toolbar) findViewById(R.id.display_customer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        nameTextView = (TextView) findViewById(R.id.display_customer_name);
        phoneNumberTextView = (TextView) findViewById(R.id.display_customer_phone_number);
        balanceTextView = (TextView) findViewById(R.id.display_customer_balance);
        comapanyNameTextView = (TextView) findViewById(R.id.display_customer_company);
        addressTextView = (TextView) findViewById(R.id.display_customer_address);
        emailTextView = (TextView) findViewById(R.id.display_customer_email);

        phoneNumberLinearLayout = (RelativeLayout) findViewById(R.id.phone_number_relative);
        balanceLinearLayout = (RelativeLayout) findViewById(R.id.balance_relative);
        companyNameLinearLayout = (RelativeLayout) findViewById(R.id.company_relative);
        addressLinearLayout = (RelativeLayout) findViewById(R.id.address_relative);
        emailLinearLayout = (RelativeLayout) findViewById(R.id.email_relative);

        displayCustomerProgressWheel = (ProgressWheel) findViewById(R.id.display_customer_progress_wheel);

        customerService = RetrofitUtil.getCustomerService();

        nameTextView.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));

        if (!customer.getPhoneNumber().isEmpty()) {
            phoneNumberTextView.setText(customer.getPhoneNumber());
            phoneNumberLinearLayout.setVisibility(View.VISIBLE);
        }

        if (customer.getBalance() != 0) {
            balanceTextView.setText(customer.getBalance().toString());
            balanceLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!customer.getCompanyName().isEmpty()) {
            comapanyNameTextView.setText(customer.getCompanyName());
            companyNameLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!customer.getAddress().isEmpty()) {
            addressTextView.setText(customer.getAddress());
            addressLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!customer.getEmail().isEmpty()) {
            emailTextView.setText(customer.getEmail());
            emailLinearLayout.setVisibility(View.VISIBLE);
        }

        /*call functionality*/
        phoneNumberLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_customer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_edit:
                editCustomer();
                return true;
            case R.id.action_delete:
                deleteCustomer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void deleteCustomer() {
        Call<String> deleteCustomerCall = customerService.deleteRegularCustomer(customer.getId().toString());
        displayCustomerProgressWheel.setVisibility(View.VISIBLE);
        deleteCustomerCall.clone().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200){
                    Toast.makeText(ActivityDisplayCustomer.this, getResources().getString(R.string.customer_deleted_successfully), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ActivityDisplayCustomer.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ActivityDisplayCustomer.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void editCustomer() {
        Intent intent = new Intent(ActivityDisplayCustomer.this, ActivityAddCustomer.class);
        intent.putExtra("flag","update");
        intent.putExtra("customer",customer);
        startActivity(intent);
        finish();

    }
}
