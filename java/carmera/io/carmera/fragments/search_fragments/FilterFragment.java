package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.gc.materialdesign.views.ButtonFlat;
import com.google.gson.Gson;
import com.rey.material.widget.Spinner;

import org.parceler.Parcels;
import java.util.Iterator;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 11/17/15.
 */
public class FilterFragment extends DialogFragment {

    private ListingsQuery listingsQuery;

    private OnResearchListener callback = null;

    @Bind(R.id.filter_btn) ButtonFlat filter_btn;

    @Bind (R.id.makes_spinner)
    MultiSpinner makes_spnr;

    @Bind (R.id.models_spinner)
    MultiSpinner models_spnr;

    @Bind (R.id.years_spinner)
    MultiSpinner years_spnr;

    @Bind (R.id.bodytype_spinner)
    MultiSpinner bodytypes_spnr;

    @Bind (R.id.txn_spinner)
    MultiSpinner txns_spnr;

    @Bind (R.id.compressor_spinner)
    MultiSpinner compressors_spnr;

    @Bind (R.id.cylinders_spinner)
    MultiSpinner cylinders_spnr;

    @Bind (R.id.drivetrain_spinner)
    MultiSpinner drivetrains_spnr;

    @Bind (R.id.tags_spinner)
    MultiSpinner tags_spnr;


    @Bind (R.id.mpg_spinner) Spinner mpg_spinner;
    @Bind (R.id.hp_spinner) Spinner hp_spinner;
    @Bind (R.id.tq_spinner) Spinner tq_spinner;

    @OnClick (R.id.filter_btn)
    void onFilter () {
        callback.onResearchCallback(listingsQuery);
        FilterFragment.this.dismiss();
    }

    @OnClick (R.id.cancel_button)
    void onCancel () {
        FilterFragment.this.dismiss();
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (OnResearchListener) getTargetFragment();
        if (callback == null) {
            callback = (OnResearchListener) getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.filter_fragment, null);
        this.listingsQuery = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_LISTING_QUERY));
        Log.i(this.getClass().getCanonicalName(), new Gson().toJson(listingsQuery, ListingsQuery.class));
        builder.setView(view);
        ButterKnife.bind(this, view);
        init_spinners();

        return builder.create();
    }
    private int findPos (ArrayAdapter<String> adapter, String item) {
        for (Integer i = 0; i < adapter.getCount(); i++) {
            Log.i (getClass().getCanonicalName(), "arrayadapter item " +  adapter.getItem(i));
            if (item.equals(adapter.getItem(i)))
                return i;
        }
        return -1;
    }

    private void init_spinners() {
        final ArrayAdapter<String> mpg_adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn,
                getResources().getStringArray(R.array.mpg_array));

        final ArrayAdapter<String> output_adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn,
                getResources().getStringArray(R.array.output_array));

        mpg_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        output_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        mpg_spinner.setAdapter(mpg_adapter);
        hp_spinner.setAdapter(output_adapter);
        tq_spinner.setAdapter(output_adapter);
        Log.i(getClass().getCanonicalName(), "min mpg " + listingsQuery.car.minMpg);
        mpg_spinner.setSelection(findPos(mpg_adapter, Integer.toString(listingsQuery.car.minMpg)));
        hp_spinner.setSelection(findPos(output_adapter, Integer.toString(listingsQuery.car.minHp)));
        tq_spinner.setSelection(findPos(output_adapter, Integer.toString(listingsQuery.car.minTq)));

        mpg_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                FilterFragment.this.listingsQuery.car.minMpg = Integer.parseInt(mpg_adapter.getItem(position));
            }
        });
        hp_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                FilterFragment.this.listingsQuery.car.minHp = Integer.parseInt(output_adapter.getItem(position));
            }
        });
        tq_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                FilterFragment.this.listingsQuery.car.minTq = Integer.parseInt(output_adapter.getItem(position));
            }
        });

        if (listingsQuery.car.makes.size() > 0) {
            makes_spnr.setItems(Util.getSelectedValues(listingsQuery.car.makes), "Filter Makes", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.makes.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            makes_spnr.setVisibility(View.GONE);
        }
        if (listingsQuery.car.models.size() > 0) {
            models_spnr.setItems(Util.getSelectedValues(listingsQuery.car.main_models), "Filter Models", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.main_models.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                    }
            });
        } else {
            models_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.years.size() > 0) {
            years_spnr.setItems(Util.getIntSelectedValues(listingsQuery.car.years), "Filter Years", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<Integer> iter = listingsQuery.car.years.listIterator(); iter.hasNext();) {
                                Integer test = iter.next();
                                if (test.equals(Integer.parseInt(items.get(i).getName())))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            years_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.bodyTypes.size() > 0) {
            bodytypes_spnr.setItems(Util.getSelectedValues(listingsQuery.car.bodyTypes), "Filter BodyTypes", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.bodyTypes.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            bodytypes_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.transmissionTypes.size() > 0) {
            txns_spnr.setItems(Util.getSelectedValues(listingsQuery.car.transmissionTypes), "Filter Transmissions", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.transmissionTypes.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            txns_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.compressors.size() > 0) {
            compressors_spnr.setItems(Util.getSelectedValues(listingsQuery.car.compressors), "Filter Compressors", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.compressors.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            compressors_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.cylinders.size() > 0) {
            cylinders_spnr.setItems(Util.getIntSelectedValues(listingsQuery.car.cylinders), "Filter Cylinders", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<Integer> iter = listingsQuery.car.cylinders.listIterator(); iter.hasNext();) {
                                Integer test = iter.next();
                                if (test.equals(Integer.parseInt(items.get(i).getName())))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            cylinders_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.drivenWheels.size() > 0) {
            drivetrains_spnr.setItems(Util.getSelectedValues(listingsQuery.car.drivenWheels), "Filter Drivetrains", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.drivenWheels.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            drivetrains_spnr.setVisibility(View.GONE);
        }

        if (listingsQuery.car.tags.size() > 0) {
            tags_spnr.setItems(Util.getSelectedValues(listingsQuery.car.tags), "Filter Tags", -1, new MultiSpinner.MultiSpinnerListener() {
                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {
                    for(int i=0; i<items.size(); i++) {
                        if(!items.get(i).isSelected()) {
                            for (Iterator<String> iter = listingsQuery.car.tags.listIterator(); iter.hasNext();) {
                                String test = iter.next();
                                if (test.equals(items.get(i).getName()))
                                    iter.remove();
                            }
                        }
                    }
                }
            });
        } else {
            tags_spnr.setVisibility(View.GONE);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
