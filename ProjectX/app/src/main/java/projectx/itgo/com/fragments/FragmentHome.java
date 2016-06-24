package projectx.itgo.com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import projectx.itgo.com.R;
import projectx.itgo.com.activities.ActivityFindCustomer;
import projectx.itgo.com.activities.ActivityFindWholesaler;

public class FragmentHome extends Fragment {

    Button buyButton, sellButton;

    public FragmentHome() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        getting all UI elements


        buyButton = (Button) view.findViewById(R.id.home_buy_button);
        sellButton = (Button) view.findViewById(R.id.home_sell_button);


//        setting on click listeners
//        go to find customer activity
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityFindCustomer.class);
                startActivity(intent);
            }
        });


//        TODO wholesaler logic
//        go to find wholesaler activity
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityFindWholesaler.class);
                startActivity(intent);
            }
        });
    }
}
