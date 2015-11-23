package carmera.io.carmera.fragments.search_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 11/16/15.
 */
public class PricingMileage extends SearchFragment {
    private Context cxt;

    @Bind(R.id.min_msrp_spinner) MultiSpinner min_msrp_spinner;
    @Bind(R.id.max_msrp_spinner) MultiSpinner max_msrp_spinner;
    @Bind(R.id.min_lease_spinner) MultiSpinner min_lease_spinner;
    @Bind(R.id.max_lease_spinner) MultiSpinner max_lease_spinner;
    @Bind(R.id.max_mileage_spinner) MultiSpinner max_mileage_spinner;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatanceState) {
        View v = inflater.inflate(R.layout.price_mileage_search, container, false);
        ButterKnife.bind(this, v);
        cxt = getActivity();
        init_spinners();
        return v;
    }

    @Override
    public void init_spinners () {

        final List<String> min_msrp = Arrays.asList(cxt.getResources().getStringArray(R.array.min_msrp_array));
        min_msrp_spinner.setItems(Util.getSpinnerValues(min_msrp), "Minimum Price", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (min_msrp.get(i).equals("None"))
                            getApiQuery().msrpmin = 0;
                        else
                            getApiQuery().msrpmin = Integer.valueOf(min_msrp.get(i));
                        break;
                    }
                }
            }
        });

        final List<String> max_msrp = Arrays.asList(cxt.getResources().getStringArray(R.array.max_msrp_array));
        max_msrp_spinner.setItems(Util.getSpinnerValues(max_msrp), "Maximum Price", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (max_msrp.get(i).equals("None"))
                            getApiQuery().msrpmax = Integer.MAX_VALUE;
                        else {
                            getApiQuery().msrpmax = Integer.valueOf(max_msrp.get(i));

                        }
                        break;
                    }
                }
            }
        });
        final List<String> min_lease = Arrays.asList(cxt.getResources().getStringArray(R.array.min_lease_array));
        min_lease_spinner.setItems(Util.getSpinnerValues(min_lease), "Minimum Lease", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (items.get(i).equals("None"))
                            getApiQuery().lpmin = 0;
                        else
                            getApiQuery().lpmin = Integer.valueOf(min_lease.get(i));
                        break;
                    }
                }
            }
        });

        final List<String> max_lease = Arrays.asList(cxt.getResources().getStringArray(R.array.max_lease_array));
        max_lease_spinner.setItems(Util.getSpinnerValues(max_lease), "Maximum Lease", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (max_lease.get(i).equals("None"))
                            getApiQuery().lpmax = Integer.MAX_VALUE;
                        else
                            getApiQuery().lpmax = Integer.valueOf(max_lease.get(i));
                        break;
                    }
                }
            }
        });


        final List<String> max_mileage = Arrays.asList(cxt.getResources().getStringArray(R.array.max_mileage_array));
        max_mileage_spinner.setItems(Util.getSpinnerValues(max_mileage), "Maximum Mileage", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (max_mileage.get(i).equals("None"))
                            getApiQuery().max_mileage = Integer.MAX_VALUE;
                        else
                            getApiQuery().max_mileage = Integer.valueOf(max_mileage.get(i));
                        break;
                    }
                }
            }
        });

    }

}
