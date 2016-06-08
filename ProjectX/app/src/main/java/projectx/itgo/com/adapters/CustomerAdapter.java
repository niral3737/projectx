package projectx.itgo.com.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import projectx.itgo.com.R;

/**
 * Created by Niral on 08-06-2016.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    public Context context;
    private List<String> customersList;

    public CustomerAdapter(List<String> customersList, Context context) {
        this.customersList = customersList;
        this.context = context;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);

        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, final int position) {
        holder.customerTitle.setText(customersList.get(position));

        holder.customerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, customersList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        public TextView customerTitle;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            customerTitle = (TextView) itemView.findViewById(R.id.item_customer_title);
        }
    }
}
