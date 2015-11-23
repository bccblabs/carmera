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
 * Created by bski on 10/12/15.
 */
public class MechanicalSearchFragment extends SearchFragment {

    private Context cxt;

    @Bind(R.id.transmission_spinner) MultiSpinner transmission_spinner;

    @Bind(R.id.cylinders_spinner) MultiSpinner cylinders_spinner;

    @Bind(R.id.hp_spinner) MultiSpinner hp_spinner;

    @Bind(R.id.tq_spinner) MultiSpinner tq_spinner;

    @Bind(R.id.mpg_spinner) MultiSpinner mpg_spinner;

    @Bind(R.id.compressor_spinner) MultiSpinner compressors_spinner;

    @Bind(R.id.drivetrain_spinner) MultiSpinner drivetrain_spinner;

    @Bind(R.id.mechanical_container) public ObservableScrollView mechanical_container;


    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatanceState) {
        View v = inflater.inflate(R.layout.mechanical_search, container, false);
        ButterKnife.bind(this, v);
        cxt = getActivity();
        return v;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_spinners();
    }

    @Override
    public void init_spinners() {
        final List<String> transmissions = Arrays.asList(cxt.getResources().getStringArray(R.array.txn_array));
        transmission_spinner.setItems(Util.getSpinnerValues(transmissions), "Choose Transmission Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().transmissionTypes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (transmissions.get(i).equals("Automatic")) {
                            getGenQuery().transmissionTypes.add("UNKNOWN");
                            getGenQuery().transmissionTypes.add("AUTOMATED_MANUAL");
                            getGenQuery().transmissionTypes.add("DIRECT_DRIVE");
                        } else if (transmissions.get(i).equals("Manual")) {
                            getGenQuery().transmissionTypes.add("MANUAL");
                        }
                    }
                }
            }
        });

        final List<String> compressor_types = Arrays.asList(cxt.getResources().getStringArray(R.array.compressor_array));
        compressors_spinner.setItems(Util.getSpinnerValues(compressor_types), "Choose Engine Compressor Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().compressors.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        getGenQuery().compressors.add(compressor_types.get(i));
                    }
                }
            }
        });

        final List<String> drivetrains = Arrays.asList(cxt.getResources().getStringArray(R.array.drivetrain_array));
        drivetrain_spinner.setItems(Util.getSpinnerValues(drivetrains), "Choose Drivetrain(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().drivenWheels.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (drivetrains.get(i).equals("All Wheel Drive"))
                            getGenQuery().drivenWheels.add("four wheel drive");
                        else
                            getGenQuery().drivenWheels.add (drivetrains.get(i));
                    }
                }
            }
        });

        final List<String> cylinders = Arrays.asList(cxt.getResources().getStringArray(R.array.cylinder_array));
        cylinders_spinner.setItems(Util.getSpinnerValues(cylinders), "Choose Cylinder Count", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().cylinders.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        getGenQuery().cylinders.add (Integer.parseInt(cylinders.get(i).replace("+", "")));
                    }
                }
            }
        });

        final List<String> hps = Arrays.asList(cxt.getResources().getStringArray(R.array.output_array));
        hp_spinner.setItems(Util.getSpinnerValues(hps), "Minimum Horsepower", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().minHp = 0;
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        getGenQuery().minHp = Integer.parseInt(hps.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> tqs = Arrays.asList(cxt.getResources().getStringArray(R.array.output_array));
        tq_spinner.setItems(Util.getSpinnerValues(tqs), "Minimum Torque", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().minTq = 0;
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        getGenQuery().minTq = Integer.parseInt(tqs.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> mpgs = Arrays.asList(cxt.getResources().getStringArray(R.array.mpg_array));
        mpg_spinner.setItems(Util.getSpinnerValues(mpgs), "Minimum Highway MPG", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().minMpg = 0;
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        getGenQuery().minMpg = Integer.parseInt(mpgs.get(i).replace("+", ""));
                    }
                }
            }
        });
    }
}
