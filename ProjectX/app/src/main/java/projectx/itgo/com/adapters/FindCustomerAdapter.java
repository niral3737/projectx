package projectx.itgo.com.adapters;

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
 * Created by Niral on 23-06-2016.
 */
public class FindCustomerAdapter extends RecyclerView.Adapter<FindCustomerAdapter.FindCustomerViewHolder> {
    private List<Customer> customers;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public FindCustomerAdapter(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public FindCustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_customer, parent, false);
        return new FindCustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FindCustomerViewHolder holder, int position) {
        final Customer customer = customers.get(position);

        holder.bind(customer);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void setFilter(List<Customer> filteredCustomers) {
        customers = new ArrayList<>();
        customers.addAll(filteredCustomers);
        notifyDataSetChanged();
    }


    public class FindCustomerViewHolder extends RecyclerView.ViewHolder {

        public TextView customerNameTextView;
        public View divider;
        public ImageView customerLetter;

        public FindCustomerViewHolder(View itemView) {
            super(itemView);
            customerNameTextView = (TextView) itemView.findViewById(R.id.find_customer_name);
            divider = itemView.findViewById(R.id.find_customer_divider);
            customerLetter = (ImageView) itemView.findViewById(R.id.find_customer_item_letter);
        }

        public void bind(Customer customer) {
            customerNameTextView.setText(customer.getFirstName());
            String firstLetter = String.valueOf(customer.getFirstName().charAt(0));
            TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter, generator.getColor(customer.getFirstName()));
            customerLetter.setImageDrawable(textDrawable);

        }
    }
}


