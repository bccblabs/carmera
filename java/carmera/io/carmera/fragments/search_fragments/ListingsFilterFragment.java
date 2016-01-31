package carmera.io.carmera.fragments.search_fragments;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.rey.material.widget.Spinner;
import org.parceler.Parcels;
import java.util.Arrays;
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
 * Created by bski on 1/29/16.
 */
public class ListingsFilterFragment extends DialogFragment{

    @OnClick(R.id.dismiss_btn)
    void onFilter () {
        ListingsFilterFragment.this.dismiss();
    }

    @OnClick (R.id.search_btn)
    void onSearch () {
        callback.onResearchCallback(listingsQuery);
        this.dismiss();
    }

    public static ListingsFilterFragment newInstance () {
        return new ListingsFilterFragment();
    }

    private ListingsQuery listingsQuery;

    private OnResearchListener callback;

    @Bind (R.id.years_spinner) MultiSpinner years_spinner;

    @Bind (R.id.sort_spinner) Spinner sort_spinner;

    @Bind (R.id.bodytype_spinner) MultiSpinner bodytypes_spnr;

    @Bind (R.id.compressor_spinner) MultiSpinner compressors_spnr;

    @Bind (R.id.conditions_spinner) MultiSpinner conditions_spnr;

    @Bind (R.id.drivetrain_spinner) MultiSpinner drivetrains_spnr;

    @Bind(R.id.models_spinner) MultiSpinner models_spnr;

    @Bind (R.id.txn_spinner) MultiSpinner txns_spnr;

    @Bind (R.id.price_spinner) Spinner price_spinner;

    @Bind (R.id.mileage_spinner) Spinner mileage_spinner;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (OnResearchListener) getTargetFragment();
        if (callback == null) {
            callback = (OnResearchListener) getActivity();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_listings_filter, null);
        this.listingsQuery = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_LISTING_QUERY));
        Log.i(this.getClass().getCanonicalName(), new Gson().toJson(listingsQuery, ListingsQuery.class));
        builder.setView(view);
        ButterKnife.bind(this, view);
        init_spinners();

        return builder.create();
    }

        private void init_spinners() {
            final ArrayAdapter<String> sort_criteria_adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_row,
                getResources().getStringArray(R.array.sort_criteria));


            final ArrayAdapter<String> price_mileage_adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_row,
                getResources().getStringArray(R.array.max_mileage_array));


            price_mileage_adapter.setDropDownViewResource(R.layout.view_spinner_row_dropdown);
            sort_criteria_adapter.setDropDownViewResource(R.layout.view_spinner_row_dropdown);


            price_spinner.setAdapter(price_mileage_adapter);
            mileage_spinner.setAdapter(price_mileage_adapter);
            sort_spinner.setAdapter(sort_criteria_adapter);


            Util.setSingleSpinnerSelection(price_spinner, price_mileage_adapter, listingsQuery.max_price);
            Util.setSingleSpinnerSelection(mileage_spinner, price_mileage_adapter, listingsQuery.max_mileage);
            Util.setSingleSpinnerSelection(sort_spinner, sort_criteria_adapter, listingsQuery.sortBy);


            price_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(Spinner parent, View view, int position, long id) {
                    ListingsFilterFragment.this.listingsQuery.max_price = price_mileage_adapter.getItem(position);
                }
            });
            mileage_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(Spinner parent, View view, int position, long id) {
                    ListingsFilterFragment.this.listingsQuery.max_mileage = price_mileage_adapter.getItem(position);
                }
            });
            sort_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(Spinner parent, View view, int position, long id) {
                    String selected_sort = sort_criteria_adapter.getItem(position);
                    if (selected_sort.equals("Price: High to Low"))
                        selected_sort = Constants.PRICE_DESC;
                    else if (selected_sort.equals("Price: Low to High"))
                        selected_sort = Constants.PRICE_ASC;
                    else if (selected_sort.equals("Miles: High to Low"))
                        selected_sort = Constants.MILEAGE_DESC;
                    else if (selected_sort.equals("Miles: Low to High"))
                        selected_sort = Constants.MILEAGE_ASC;
                    else if (selected_sort.equals("HP: High to Low"))
                        selected_sort = Constants.HP_DESC;
                    else if (selected_sort.equals("HP: Low to High"))
                        selected_sort = Constants.HP_ASC;
                    else if (selected_sort.equals("Torque: High to Low"))
                        selected_sort = Constants.TQ_DESC;
                    else if (selected_sort.equals("Torque: Low to High"))
                        selected_sort = Constants.TQ_ASC;
                    else if (selected_sort.equals("MPG: High to Low"))
                        selected_sort = Constants.MPG_DESC;
                    else if (selected_sort.equals("MPG: Low to High"))
                        selected_sort = Constants.MPG_ASC;
                    else if (selected_sort.equals("Recalls: Least to Most"))
                        selected_sort = Constants.RECALLS_ASC;
                    else if (selected_sort.equals("Recalls: Most to Least"))
                        selected_sort = Constants.RECALLS_DESC;
                    else selected_sort = "";

                    ListingsFilterFragment.this.listingsQuery.sortBy = selected_sort;
                }
            });


            years_spinner.setItems(Util.getSpinnerValues(
                            listingsQuery.car.years,
                            true,
                            listingsQuery.car.years),
                    "Years", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.years.listIterator(); iter.hasNext(); ) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.years.add(items.get(i).getName().toLowerCase());
                                }
                            }

                        }
                    });

            bodytypes_spnr.setItems(Util.getSpinnerValues(
                            listingsQuery.car.bodyTypes,
                            true,
                            listingsQuery.car.bodyTypes),
                    "BodyTypes", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.bodyTypes.listIterator(); iter.hasNext(); ) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.bodyTypes.add(items.get(i).getName().toLowerCase());
                                }
                            }
                        }
                    });


            compressors_spnr.setItems(Util.getSpinnerValues(
                            listingsQuery.car.compressors,
                            true,
                            listingsQuery.car.compressors),
                    "Compressors", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for(int i=0; i<items.size(); i++) {
                                if(!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.compressors.listIterator(); iter.hasNext();) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.compressors.add(items.get(i).getName().toLowerCase());
                                }
                            }
                        }
                    });

            conditions_spnr.setItems(Util.getSpinnerValues(
                        Arrays.asList(getActivity().getResources().getStringArray(R.array.conditions_array)),
                        true,
                        listingsQuery.api.conditions),
                "Conditions", -1, new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (!items.get(i).isSelected()) {
                                for (Iterator<String> iter = listingsQuery.api.conditions.listIterator(); iter.hasNext(); ) {
                                    String test = iter.next();
                                    if (test.equals(items.get(i).getName()))
                                        iter.remove();
                                }
                            } else {
                                listingsQuery.api.conditions.add(items.get(i).getName().toLowerCase());
                            }
                        }
                    }
                });

            drivetrains_spnr.setItems(Util.getSpinnerValues(
                            listingsQuery.car.drivenWheels,
                            true,
                            listingsQuery.car.drivenWheels),
                    "Drivetrains", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.drivenWheels.listIterator(); iter.hasNext(); ) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.drivenWheels.add(items.get(i).getName().toLowerCase());
                                }
                            }
                        }
                    });

            models_spnr.setItems(Util.getSpinnerValues(
                            listingsQuery.car.models,
                            true,
                            listingsQuery.car.models),
                    "Models", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.models.listIterator(); iter.hasNext(); ) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.models.add(items.get(i).getName().toLowerCase());
                                }
                            }
                        }
                    }
            );


            txns_spnr.setItems(Util.getSpinnerValues(
                            listingsQuery.car.transmissionTypes,
                            true,
                            listingsQuery.car.transmissionTypes),
                    "Transmissions", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for(int i=0; i<items.size(); i++) {
                                if(!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.transmissionTypes.listIterator(); iter.hasNext();) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.transmissionTypes.add (items.get(i).getName().toLowerCase());
                                }
                            }
                        }
                    });


    }


}
