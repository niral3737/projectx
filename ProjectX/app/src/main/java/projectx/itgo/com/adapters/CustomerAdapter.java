package projectx.itgo.com.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import projectx.itgo.com.R;
import projectx.itgo.com.models.Customer;

/**
 * Created by Niral on 08-06-2016.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    public Context context;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    private List<Customer> customersList;

    public CustomerAdapter(List<Customer> customersList, Context context) {
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
        holder.customerTitle.setText(customersList.get(position).getFirstName());
//        if(customersList.get(position).getBalance()>0){
//            holder.balance.setTextColor(context.getResources().getColor(R.color.balance_positive));
//        }else{
//            holder.balance.setTextColor(context.getResources().getColor(R.color.balance_negative));
//        }
        holder.balance.setText(customersList.get(position).getCredit().toString());
        String firstLetter = String.valueOf(customersList.get(position).getFirstName().charAt(0));

        TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter, generator.getColor(customersList.get(position).getFirstName()));

        holder.customerLetter.setImageDrawable(textDrawable);
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public void setFilter(List<Customer> customersList) {
        this.customersList = new ArrayList<>();
        this.customersList.addAll(customersList);
        notifyDataSetChanged();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        public TextView customerTitle;
        public ImageView customerLetter;
        public TextView balance;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            customerTitle = (TextView) itemView.findViewById(R.id.customer_item_title);
            customerLetter = (ImageView) itemView.findViewById(R.id.customer_item_letter);
            balance = (TextView) itemView.findViewById(R.id.customer_balance);
        }
    }
}
