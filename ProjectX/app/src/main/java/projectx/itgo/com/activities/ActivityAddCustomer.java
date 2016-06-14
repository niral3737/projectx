package projectx.itgo.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import projectx.itgo.com.APIServices.CustomerService;
import projectx.itgo.com.R;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.utilities.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddCustomer extends AppCompatActivity {

    static final int PICK_CONTACT = 1;
    EditText customerEmailEditText, customerFirstNameEditText, customerPhoneNumberEditText, customerLastNameEditText, customerAddressEditText, customerCompanyNameEditText, customerBalanceEditText;
    ScrollView customerScrollView;
    ProgressWheel progressWheel;
    FloatingActionButton chooseContactFloatingActionButton;
    Button addNewCustomerButton;
    Customer customer;
    String flag;
    CustomerService customerService;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        toolbar = (Toolbar) findViewById(R.id.add_customer_toolbar);
        setSupportActionBar(toolbar);

        customerScrollView = (ScrollView) findViewById(R.id.my_scrollview);
        progressWheel = (ProgressWheel) findViewById(R.id.add_customer_activity_progress_wheel);
        assert progressWheel != null;
        progressWheel.setVisibility(View.GONE);
        customerScrollView.setVisibility(View.VISIBLE);
        customerEmailEditText = (EditText) findViewById(R.id.customer_email_edit_text);
        chooseContactFloatingActionButton = (FloatingActionButton) findViewById(R.id.choose_contact_fab);
        customerFirstNameEditText = (EditText) findViewById(R.id.customer_first_name_edit_text);
        customerPhoneNumberEditText = (EditText) findViewById(R.id.customer_phone_number_edit_text);
        customerAddressEditText = (EditText) findViewById(R.id.customer_address_edit_text);
        customerCompanyNameEditText = (EditText) findViewById(R.id.customer_company_name_edit_text);
        customerLastNameEditText = (EditText) findViewById(R.id.customer_last_name_edit_text);
        customerBalanceEditText = (EditText) findViewById(R.id.customer_balance_edit_text);
        addNewCustomerButton = (Button) findViewById(R.id.add_new_customer_button);
        customerEmailEditText.addTextChangedListener(new MyTextWatcher(customerEmailEditText));
        customerService = RetrofitUtil.getCustomerService();


        chooseContactFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        Intent intent = getIntent();

        flag = intent.getStringExtra("flag");

        if (flag.equals("insert")) {

            // toolbar.setTitle(R.string.new_customer);
            getSupportActionBar().setTitle(R.string.new_customer);
            customer = new Customer();

        } else {

            // toolbar.setTitle(R.string.update_customer);
            getSupportActionBar().setTitle(R.string.update_customer);

            customer = intent.getParcelableExtra("customer");
            customerFirstNameEditText.setText(customer.getFirstName());
            customerLastNameEditText.setText(customer.getLastName());
            customerEmailEditText.setText(customer.getEmail());
            customerPhoneNumberEditText.setText(customer.getPhoneNumber());
            customerAddressEditText.setText(customer.getAddress());
            customerCompanyNameEditText.setText(customer.getCompanyName());
            customerBalanceEditText.setText(customer.getBalance().toString());

            addNewCustomerButton.setText(R.string.update_customer_button_text);


        }

        addNewCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    customerScrollView.setVisibility(View.GONE);
                    progressWheel.setVisibility(View.VISIBLE);
                    customer.setAddress(customerAddressEditText.getText().toString());
                    customer.setFirstName(customerFirstNameEditText.getText().toString());
                    customer.setLastName(customerLastNameEditText.getText().toString());
                    customer.setCompanyName(customerCompanyNameEditText.getText().toString());
                    customer.setEmail(customerEmailEditText.getText().toString());
                    customer.setPhoneNumber(customerPhoneNumberEditText.getText().toString());
                    if (!customerBalanceEditText.getText().toString().isEmpty()) {
                        customer.setBalance(Double.parseDouble(customerBalanceEditText.getText().toString()));
                    } else {
                        customer.setBalance((double) 0);
                    }

                    if (flag.equals("insert")) {

                        Call<String> addCustomerCall = customerService.addRegularCustomer(customer);

                        addCustomerCall.clone().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(ActivityAddCustomer.this, getResources().getString(R.string.customer_added_successfully), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ActivityAddCustomer.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ActivityAddCustomer.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        Call<String> updateCustomerCall = customerService.updateCustomer(customer.getId().toString(), customer);
                        updateCustomerCall.clone().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(ActivityAddCustomer.this, getResources().getString(R.string.customer_updated_successfully), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ActivityAddCustomer.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ActivityAddCustomer.this, getResources().getString(R.string.there_must_be_some_problem), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private boolean validate() {
        if (customerFirstNameEditText.getText().toString().isEmpty()) {
            customerFirstNameEditText.setError(getResources().getString(R.string.please_enter_first_name));
            return false;
        }
        if (customerPhoneNumberEditText.getText().toString().isEmpty()) {
            customerPhoneNumberEditText.setError(getResources().getString(R.string.please_enter_phone_number));
            return false;
        }
        if (customerPhoneNumberEditText.getText().toString().length() != 10) {
            customerPhoneNumberEditText.setError(getResources().getString(R.string.please_enter_a_valid_phone_number));
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = customerEmailEditText.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty()) {
            customerEmailEditText.setError(getString(R.string.enter_valid_email));
            requestFocus(customerEmailEditText);
            return false;
        } else {
            customerEmailEditText.setError(null);
            requestFocus(customerEmailEditText);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    String cNumber = "";
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            assert phones != null;
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            cNumber = cNumber.replace(" ", "");
                            if (cNumber.length() > 10) {
                                cNumber = cNumber.substring(cNumber.length() - 10);
                            }
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        setContactDetails(cNumber, name);


                    }
                }
                break;
        }
    }

    private void setContactDetails(String cNumber, String name) {
        customerFirstNameEditText.setText(name);
        customerPhoneNumberEditText.setText(cNumber);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.customer_email_edit_text:
                    validateEmail();
                    break;

            }
        }
    }
}
