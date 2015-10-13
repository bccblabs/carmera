package carmera.io.carmera.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.utils.MultiSpinner;

/**
 * Created by bski on 10/12/15.
 */
public class MechanicalSearchFragment extends SearchFragment {

    private Context cxt;

    public static MechanicalSearchFragment newInstance() {
        return new MechanicalSearchFragment();
    }

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
        init_spinners();
        return v;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mechanical_container, null);
    }

    @Override
    public void init_spinners() {
        final List<String> transmissions = Arrays.asList(cxt.getResources().getStringArray(R.array.txn_array));
        transmission_spinner.setItems(transmissions, "Choose Transmission Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().transmissionTypes.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        if (transmissions.get(i).equals("Automatic")) {
                            getGenQuery().transmissionTypes.add("UNKNOWN");
                            getGenQuery().transmissionTypes.add("AUTOMATED_MANUAL");
                            getGenQuery().transmissionTypes.add("DIRECT_DRIVE");
                        } else if (transmissions.get(i).equals("Manual")) {
                            getGenQuery().transmissionTypes.add ("MANUAL");
                        }
                    }
                }
            }
        });

        final List<String> compressor_types = Arrays.asList(cxt.getResources().getStringArray(R.array.compressor_array));
        compressors_spinner.setItems(compressor_types, "Choose Engine Compressor Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().compressors.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        if (compressor_types.get(i).equals("Naturally Aspirated"))
                            getGenQuery().compressors.add("NA");
                        else
                            getGenQuery().compressors.add(compressor_types.get(i));
                    }
                }
            }
        });

        final List<String> drivetrains = Arrays.asList(cxt.getResources().getStringArray(R.array.drivetrain_array));
        drivetrain_spinner.setItems(drivetrains, "Choose Drivetrain(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().drivetrains.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        if (drivetrains.get(i).equals("All Wheel Drive"))
                            getGenQuery().drivetrains.add("four wheel drive");
                        else
                            getGenQuery().drivetrains.add (drivetrains.get(i));
                    }
                }
            }
        });

        final List<String> cylinders = Arrays.asList(cxt.getResources().getStringArray(R.array.cylinder_array));
        cylinders_spinner.setItems(cylinders, "Choose Cylinder Count", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().cylinders.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().cylinders.add (Integer.parseInt(cylinders.get(i).replace("+", "")));
                    }
                }
            }
        });

        final List<String> hps = Arrays.asList(cxt.getResources().getStringArray(R.array.output_array));
        hp_spinner.setItems(hps, "Minimum Horsepower", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().min_hp = 0;
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().min_hp = Integer.parseInt(hps.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> tqs = Arrays.asList(cxt.getResources().getStringArray(R.array.output_array));
        tq_spinner.setItems(tqs, "Minimum Torque", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().min_tq = 0;
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().min_tq = Integer.parseInt(tqs.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> mpgs = Arrays.asList(cxt.getResources().getStringArray(R.array.mpg_array));
        mpg_spinner.setItems(mpgs, "Minimum Highway MPG", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().min_tq = 0;
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().min_mpg = mpgs.get(i).replace("+", "");
                    }
                }
            }
        });
    }
}
