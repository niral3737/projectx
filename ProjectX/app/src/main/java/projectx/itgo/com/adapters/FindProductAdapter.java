package projectx.itgo.com.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import projectx.itgo.com.R;
import projectx.itgo.com.models.Product;

/**
 * Created by Niral on 24-06-2016.
 */
public class FindProductAdapter extends RecyclerView.Adapter<FindProductAdapter.FindProductViewHolder> {

    private List<Product> products;

    public FindProductAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public FindProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_product, parent, false);
        return new FindProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FindProductViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setFilter(List<Product> filteredProducts) {
        products = new ArrayList<>();
        products.addAll(filteredProducts);
        notifyDataSetChanged();
    }

    public class FindProductViewHolder extends RecyclerView.ViewHolder {

        public TextView productNameTextView, productQuantityTextView, productCompanyTextView, productModelTextView, productBlankTextView, productCodeTextView, productTypeTextView, productPriceTextView;
        public View divider;
        public CardView cardView;

        public FindProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = (TextView) itemView.findViewById(R.id.item_find_product_name);
            productQuantityTextView = (TextView) itemView.findViewById(R.id.item_find_product_quantity);
            productCompanyTextView = (TextView) itemView.findViewById(R.id.item_find_product_company);
            productModelTextView = (TextView) itemView.findViewById(R.id.item_find_product_model);
            productBlankTextView = (TextView) itemView.findViewById(R.id.item_find_product_blank);
            productCodeTextView = (TextView) itemView.findViewById(R.id.item_find_product_code);
            productTypeTextView = (TextView) itemView.findViewById(R.id.item_find_product_type);
            productPriceTextView = (TextView) itemView.findViewById(R.id.item_find_product_price);

            divider = itemView.findViewById(R.id.item_find_product_divider);

            cardView = (CardView) itemView.findViewById(R.id.find_product_card_view);

        }

        public void bind(Product product) {
            productNameTextView.setText(product.getName());
            productQuantityTextView.setText(product.getQuantity().toString());
            productCompanyTextView.setText(product.getCompany());
            productModelTextView.setText(product.getModel());
            productCodeTextView.setText(product.getCode());
            productTypeTextView.setText(product.getType());
            productPriceTextView.setText(product.getPrice().toString());
        }
    }
}
