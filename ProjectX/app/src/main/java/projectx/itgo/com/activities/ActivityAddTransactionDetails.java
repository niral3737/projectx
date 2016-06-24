package projectx.itgo.com.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import projectx.itgo.com.R;
import projectx.itgo.com.models.Customer;
import projectx.itgo.com.models.Product;

public class ActivityAddTransactionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_details);


        Intent intent = getIntent();
        Customer customer = intent.getParcelableExtra("customer");
        Product product = intent.getParcelableExtra("product");

        if (customer == null && product == null){
            Toast.makeText(ActivityAddTransactionDetails.this, "Random Customer and Random Product", Toast.LENGTH_SHORT).show();
        }else if(customer == null){
            Toast.makeText(ActivityAddTransactionDetails.this, "Random Customer", Toast.LENGTH_SHORT).show();
        }else if(product == null){
            Toast.makeText(ActivityAddTransactionDetails.this, "Random Product", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityAddTransactionDetails.this, customer.getId().toString() +" "+ product.getId().toString() + "", Toast.LENGTH_SHORT).show();
        }
        

    }
}
