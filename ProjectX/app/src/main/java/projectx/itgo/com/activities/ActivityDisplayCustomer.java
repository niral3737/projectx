package projectx.itgo.com.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import projectx.itgo.com.APIServices.CustomerService;
import projectx.itgo.com.R;
import projectx.itgo.com.database.DBHelper;
import projectx.itgo.com.models.AppUser;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.utilities.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDisplayCustomer extends AppCompatActivity {

    TextView nameTextView, phoneNumberTextView, creditTextView, debitTextView, comapanyNameTextView, addressTextView, emailTextView;
    RelativeLayout phoneNumberLinearLayout, creditLinearLayout, debitLinearLayout, companyNameLinearLayout, addressLinearLayout, emailLinearLayout;
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
        creditTextView = (TextView) findViewById(R.id.display_customer_credit);
        debitTextView = (TextView) findViewById(R.id.display_customer_debit);
        comapanyNameTextView = (TextView) findViewById(R.id.display_customer_company);
        addressTextView = (TextView) findViewById(R.id.display_customer_address);
        emailTextView = (TextView) findViewById(R.id.display_customer_email);

        phoneNumberLinearLayout = (RelativeLayout) findViewById(R.id.phone_number_relative);
        creditLinearLayout = (RelativeLayout) findViewById(R.id.credit_relative);
        debitLinearLayout = (RelativeLayout) findViewById(R.id.debit_relative);
        companyNameLinearLayout = (RelativeLayout) findViewById(R.id.company_relative);
        addressLinearLayout = (RelativeLayout) findViewById(R.id.address_relative);
        emailLinearLayout = (RelativeLayout) findViewById(R.id.email_relative);

        displayCustomerProgressWheel = (ProgressWheel) findViewById(R.id.display_customer_progress_wheel);

        customerService = ServiceGenerator.createService(CustomerService.class,ActivityDisplayCustomer.this);

        nameTextView.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));

        if (!customer.getPhoneNumber().isEmpty()) {
            phoneNumberTextView.setText(customer.getPhoneNumber());
            phoneNumberLinearLayout.setVisibility(View.VISIBLE);
        }

        if (customer.getCredit() != 0) {
            creditTextView.setText(customer.getCredit().toString());
            creditLinearLayout.setVisibility(View.VISIBLE);
        }

        if (customer.getDebit() != 0) {
            debitTextView.setText(customer.getDebit().toString());
            debitLinearLayout.setVisibility(View.VISIBLE);
        }

        if (!customer.getShopName().isEmpty()) {
            comapanyNameTextView.setText(customer.getShopName());
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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                deleteCustomer();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayCustomer.this);
                builder.setTitle("Delete Customer").setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
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
