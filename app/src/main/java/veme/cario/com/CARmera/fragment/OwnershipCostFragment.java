package veme.cario.com.CARmera.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 11/10/14.
 */
public class OwnershipCostFragment extends Fragment {
    private LinearLayout price_overlay; // new, used
    private LinearLayout running_overlay; // fuel, insurance, maintenance, repair, insurance

    private TextView avg_new_price;
    private TextView avg_used_price;

    private TextView fuel_sum;
    private TextView insurance_sum;
    private TextView maintenance_sum;
    private TextView repair_sum;
    private TextView dep_sum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ownership, container, false);
    }
}
