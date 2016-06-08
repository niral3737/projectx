package projectx.itgo.com.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import projectx.itgo.com.R;
import projectx.itgo.com.adapters.CustomerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCustomer extends Fragment {

    EditText editTextSearch;
    RecyclerView customersRecyclerView;
    CustomerAdapter customerAdapter;
    List<String> customersList = new ArrayList<>();

    public FragmentCustomer() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        editTextSearch = (EditText) view.findViewById(R.id.customer_search);
        customersRecyclerView = (RecyclerView) view.findViewById(R.id.customers_recycler_view);
        customersRecyclerView.setHasFixedSize(true);
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getCustomers();

        customerAdapter = new CustomerAdapter(customersList, getActivity());
        customersRecyclerView.setAdapter(customerAdapter);

        addTextListener();
    }

    private void addTextListener() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charSequence = charSequence.toString().toLowerCase();

                final List<String> filteredList = new ArrayList<>();

                for (int j = 0; j < customersList.size(); j++) {

                    final String text = customersList.get(j).toLowerCase();
                    if (text.contains(charSequence)) {

                        filteredList.add(customersList.get(j));
                    }
                }

                customersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                customerAdapter = new CustomerAdapter(filteredList,getActivity());
                customersRecyclerView.setAdapter(customerAdapter);
                customerAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getCustomers() {
        customersList.add("Tarun Motors");
        customersList.add("Umiya Motors");
        customersList.add("Ambika Motors");
        customersList.add("Sainath Garage");
        customersList.add("Umiya Parts");
        customersList.add("Tarun Motors");
        customersList.add("Umiya Motors");
        customersList.add("Ambika Motors");
        customersList.add("Sainath Garage");
        customersList.add("Umiya Parts");
        customersList.add("Tarun Motors");
        customersList.add("Umiya Motors");
        customersList.add("Ambika Motors");
        customersList.add("Sainath Garage");
        customersList.add("Umiya Parts");
    }


}
