package projectx.itgo.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import javax.net.ssl.HttpsURLConnection;

import projectx.itgo.com.APIServices.ProductService;
import projectx.itgo.com.R;
import projectx.itgo.com.database.DBHelper;
import projectx.itgo.com.models.AppUser;
import projectx.itgo.com.models.Product;
import projectx.itgo.com.utilities.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddProduct extends AppCompatActivity {

    private Toolbar addProductToolbar;
    private ProgressWheel progressWheel;
    private EditText productNameEditText, productCodeEditText, productCompanyEditText, productModelEditText, productTypeEditText, productQuantityEditText, productPriceEditText;
    private Button addProductButton;
    private ProductService productService;
    private String status;
    private Product product;
    private ScrollView addProductScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

//        Getting all UI elements

        addProductToolbar = (Toolbar) findViewById(R.id.add_product_toolbar);
        progressWheel = (ProgressWheel) findViewById(R.id.add_product_activity_progress_wheel);
        productNameEditText = (EditText) findViewById(R.id.add_product_name_edit_text);
        productCodeEditText = (EditText) findViewById(R.id.add_product_code_edit_text);
        productCompanyEditText = (EditText) findViewById(R.id.add_product_company_edit_text);
        productModelEditText = (EditText) findViewById(R.id.add_product_model_edit_text);
        productTypeEditText = (EditText) findViewById(R.id.add_product_type_edit_text);
        productQuantityEditText = (EditText) findViewById(R.id.add_product_quantity_edit_text);
        productPriceEditText = (EditText) findViewById(R.id.add_product_price_edit_text);
        addProductButton = (Button) findViewById(R.id.add_new_product_button);
        addProductScrollView = (ScrollView) findViewById(R.id.add_product_scrollview);


//        toolbar settings
        setSupportActionBar(addProductToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addProductToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        intent checking

        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        if (status.equals("addProductInProcess") || status.equals("addProduct")) {
            getSupportActionBar().setTitle(R.string.add_product);
            product = new Product();
        }else if(status.equals("updateProduct")){
            getSupportActionBar().setTitle(R.string.add_product);
            product = intent.getParcelableExtra("product");

//            setting the values from received product object

            productNameEditText.setText(product.getName());
            productCodeEditText.setText(product.getCode());
            productCompanyEditText.setText(product.getCompany());
            productModelEditText.setText(product.getModel());
            productTypeEditText.setText(product.getType());
            productQuantityEditText.setText(product.getQuantity());
            productPriceEditText.setText(product.getPrice().toString());

//            changing the name of button to Update Product

            addProductButton.setText(R.string.update_product);

        }

//        creating the product service

        productService = ServiceGenerator.createService(ProductService.class, ActivityAddProduct.this);

//        setting on click for add/update product button

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                setting visibilities of main layout and progress wheel
                if(isValid()){
                    if (addProductScrollView.getVisibility() == View.VISIBLE){
                        addProductScrollView.setVisibility(View.GONE);
                    }
                    if(progressWheel.getVisibility() == View.GONE){
                        progressWheel.setVisibility(View.VISIBLE);
                    }

//                    setting all values of product object to send to the server
                    product.setType(productTypeEditText.getText().toString());
                    product.setCode(productCodeEditText.getText().toString());
                    product.setCompany(productCompanyEditText.getText().toString());
                    product.setModel(productModelEditText.getText().toString());
                    product.setName(productNameEditText.getText().toString());

//                    setting price and quantity to 0 if the edit text is empty
                    if(productPriceEditText.getText().toString().isEmpty()){
                        product.setPrice((double) 0);
                    }else {
                        product.setPrice(Double.parseDouble(productPriceEditText.getText().toString()));
                    }
                    if(productQuantityEditText.getText().toString().isEmpty()){
                        product.setQuantity(0);
                    }else {
                        product.setQuantity(Integer.parseInt(productQuantityEditText.getText().toString()));
                    }

//                    performing insert or update depending upon the status variable

                    if(status.equals("addProduct") || status.equals("addProductInProcess")){
//                        calling api for add product
                        Call<String> addProductCall = productService.addProduct(product);
                        addProductCall.clone().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code()== HttpsURLConnection.HTTP_CREATED){
                                    Toast.makeText(ActivityAddProduct.this, R.string.product_added_successfully, Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ActivityAddProduct.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                                }

//                                going to next activity if product is added in process
                                if(status.equals("addProductInProcess")){
                                    Intent intent1 = new Intent(ActivityAddProduct.this, ActivityAddTransactionDetails.class);
                                    product.setId(Long.parseLong(response.body()));
                                    intent1.putExtra("product", product);
                                    startActivity(intent1);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ActivityAddProduct.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }else if(status.equals("updateProduct")){
//                        calling api for updating the product
                        Call<String> updateProductCall = productService.updateProduct(product.getId().toString(),product);
                        updateProductCall.clone().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == HttpsURLConnection.HTTP_OK){
                                    Toast.makeText(ActivityAddProduct.this, R.string.product_updated_successfully, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ActivityAddProduct.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ActivityAddProduct.this, R.string.there_must_be_some_problem, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }

                }
            }
        });


    }

    private boolean isValid() {

        if(productNameEditText.getText().toString().isEmpty()){
            productNameEditText.setError(getString(R.string.please_enter_product_name));
            return false;
        }

        if(productPriceEditText.getText().toString().isEmpty()){
            productPriceEditText.setError(getString(R.string.please_enter_price_per_piece));
            return false;
        }

        if (Double.parseDouble(productPriceEditText.getText().toString())<0){
            productPriceEditText.setError(getString(R.string.please_enter_a_valid_price));
            return false;
        }

        if (productQuantityEditText.getText().toString().isEmpty()){
            productQuantityEditText.setError(getString(R.string.please_enter_available_quantity));
            return false;
        }

        if (Integer.parseInt(productQuantityEditText.getText().toString())<0){
            productQuantityEditText.setError(getString(R.string.please_enter_a_valid_quantity));
            return false;
        }

        return true;
    }
}
