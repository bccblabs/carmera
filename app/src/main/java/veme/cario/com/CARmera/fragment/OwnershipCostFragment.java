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

    private TextView fuel_cost0_textview;
    private TextView insurance_cost0_textview;
    private TextView repair_cost0_textview;
    private TextView maintenance_cost0_textview;
    private TextView depr_cost0_textview;

    private TextView fuel_cost1_textview;
    private TextView insurance_cost1_textview;
    private TextView repair_cost1_textview;
    private TextView maintenance_cost1_textview;
    private TextView depr_cost1_textview;

    private TextView fuel_cost2_textview;
    private TextView insurance_cost2_textview;
    private TextView repair_cost2_textview;
    private TextView maintenance_cost2_textview;
    private TextView depr_cost2_textview;

    private TextView fuel_cost3_textview;
    private TextView insurance_cost3_textview;
    private TextView repair_cost3_textview;
    private TextView maintenance_cost3_textview;
    private TextView depr_cost3_textview;

    private TextView fuel_cost4_textview;
    private TextView insurance_cost4_textview;
    private TextView repair_cost4_textview;
    private TextView maintenance_cost4_textview;
    private TextView depr_cost4_textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ownership, container, false);
    }
}
