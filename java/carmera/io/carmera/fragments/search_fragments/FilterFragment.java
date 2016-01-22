package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.google.gson.Gson;
import com.rey.material.widget.Spinner;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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

    private SortedMap<String, Integer> make_resid_map = new TreeMap<>();

    private List<String> selected_models = new ArrayList<>();

    @Bind(R.id.dismiss_btn)
    ButtonFlat filter_btn;

    @Bind (R.id.makes_spinner)
    MultiSpinner makes_spnr;

    @Bind (R.id.models_spinner)
    MultiSpinner models_spnr;

    @Bind (R.id.years_spinner)
    Spinner years_spinner;

    @Bind (R.id.sort_spinner)
    Spinner sort_spinner;

    @Bind (R.id.bodytype_spinner)
    MultiSpinner bodytypes_spnr;

    @Bind (R.id.txn_spinner)
    MultiSpinner txns_spnr;

    @Bind (R.id.compressor_spinner)
    MultiSpinner compressors_spnr;

    @Bind (R.id.drivetrain_spinner)
    MultiSpinner drivetrains_spnr;

    @Bind (R.id.tags_spinner)
    MultiSpinner tags_spnr;

    @Bind (R.id.conditions_spinner)
    MultiSpinner conditions_spnr;

    @Bind (R.id.mileage_view) View mileage_view;

    @Bind (R.id.price_view) View price_view;


    @Bind (R.id.mpg_spinner) Spinner mpg_spinner;
    @Bind (R.id.hp_spinner) Spinner hp_spinner;
    @Bind (R.id.tq_spinner) Spinner tq_spinner;
    @Bind (R.id.price_spinner) Spinner price_spinner;
    @Bind (R.id.mileage_spinner) Spinner mileage_spinner;
    @Bind (R.id.cylinders_spinner) Spinner cylinders_spnr;

    @OnClick (R.id.dismiss_btn)
    void onFilter () {
        FilterFragment.this.dismiss();
    }

    @OnClick (R.id.search_btn)
    void onSearch () {
        callback.onResearchCallback(listingsQuery);
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
        make_resid_map.put("acura", R.array.acura);
        make_resid_map.put("audi", R.array.audi);
        make_resid_map.put ("aston martin", R.array.astonmartin);
        make_resid_map.put ("bentley", R.array.bentley);
        make_resid_map.put ("bmw", R.array.bmw);
        make_resid_map.put ("buick", R.array.buick);
        make_resid_map.put ("cadillac", R.array.cadillac);
        make_resid_map.put ("chevrolet", R.array.chevrolet);
        make_resid_map.put ("chrysler", R.array.chrysler);
        make_resid_map.put ("dodge", R.array.dodge);
        make_resid_map.put ("ferrari", R.array.ferrari);
        make_resid_map.put ("fiat", R.array.fiat);
        make_resid_map.put ("fisker", R.array.fisker);
        make_resid_map.put ("ford", R.array.ford);
        make_resid_map.put ("fMC", R.array.gmc);
        make_resid_map.put ("honda", R.array.honda);
        make_resid_map.put ("hyundai", R.array.hyundai);
        make_resid_map.put ("infiniti", R.array.infiniti);
        make_resid_map.put ("jaguar", R.array.jaguar);
        make_resid_map.put ("jeep", R.array.jeep);
        make_resid_map.put ("kia", R.array.kia);
        make_resid_map.put ("lamborghini", R.array.lamboghini);
        make_resid_map.put ("land rover", R.array.lr);
        make_resid_map.put ("lexus", R.array.lexus);
        make_resid_map.put ("lincoln", R.array.lincoln);
        make_resid_map.put ("lotus", R.array.lotus);
        make_resid_map.put ("mazda", R.array.mazda);
        make_resid_map.put ("maserati", R.array.maserati);
        make_resid_map.put ("mercedes-benz", R.array.mercedes);
        make_resid_map.put ("mini", R.array.mini);
        make_resid_map.put ("mitsubishi", R.array.mitsubishi);
        make_resid_map.put ("nissan", R.array.nissan);
        make_resid_map.put ("porsche", R.array.porsche);
        make_resid_map.put ("saab", R.array.saab);
        make_resid_map.put ("scion", R.array.scion);
        make_resid_map.put ("smart", R.array.smart);
        make_resid_map.put ("subaru", R.array.subaru);
        make_resid_map.put ("suzuki", R.array.suzuki);
        make_resid_map.put ("toyota", R.array.toyota);
        make_resid_map.put("volkswagen", R.array.volkswagen);
        make_resid_map.put("volvo", R.array.volvo);
        make_resid_map.put ("rolls-royce", R.array.rolls);

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

        if (this.listingsQuery.car.models.size() > 0) {
            makes_spnr.setVisibility(View.GONE);
        }

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

        final ArrayAdapter<String> year_adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn,
                getResources().getStringArray(R.array.years));

        final ArrayAdapter<String> sort_criteria_adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn,
                getResources().getStringArray(R.array.sort_criteria));

        final ArrayAdapter<String> cylinder_adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn,
                getResources().getStringArray(R.array.cylinder_array));

        mpg_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        output_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        year_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        sort_criteria_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        cylinder_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);


        mpg_spinner.setAdapter(mpg_adapter);
        hp_spinner.setAdapter(output_adapter);
        tq_spinner.setAdapter(output_adapter);
        years_spinner.setAdapter(year_adapter);
        sort_spinner.setAdapter(sort_criteria_adapter);
        cylinders_spnr.setAdapter(cylinder_adapter);

        setSingleSpinnerSelection(mpg_spinner, mpg_adapter, Integer.toString(listingsQuery.car.minMpg));
        setSingleSpinnerSelection(hp_spinner, output_adapter, Integer.toString(listingsQuery.car.minHp));
        setSingleSpinnerSelection(tq_spinner, output_adapter, Integer.toString(listingsQuery.car.minTq));
        setSingleSpinnerSelection(years_spinner, year_adapter, Integer.toString(listingsQuery.car.minYr));
        setSingleSpinnerSelection(sort_spinner, sort_criteria_adapter, listingsQuery.sortBy);
        setSingleSpinnerSelection(cylinders_spnr, cylinder_adapter, Integer.toString(listingsQuery.car.minCylinders));

        try {
            years_spinner.setSelection(findPos(year_adapter, Integer.toString(Collections.min(listingsQuery.car.years))));
        } catch (Exception e) {
            years_spinner.setSelection(0);
        }

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

        years_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                FilterFragment.this.listingsQuery.car.minYr = Integer.parseInt(year_adapter.getItem(position));
            }
        });

        cylinders_spnr.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                FilterFragment.this.listingsQuery.car.minCylinders = Integer.parseInt(cylinder_adapter.getItem(position));
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

                FilterFragment.this.listingsQuery.sortBy = selected_sort;
            }
        });

        if (FilterFragment.this.listingsQuery.car.models == null || FilterFragment.this.listingsQuery.car.models.size() < 1) {
            price_view.setVisibility(View.GONE);
            mileage_view.setVisibility(View.GONE);
            makes_spnr.setItems(Util.getSpinnerValues(
                            Arrays.asList(getActivity().getResources().getStringArray(R.array.makes_array)),
                            true,
                            listingsQuery.car.makes),

                    "Makes", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            selected_models = new ArrayList<String>();

                            for (int i = 0; i < items.size(); i++) {
                                if (!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.makes.listIterator(); iter.hasNext(); ) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName().toLowerCase()))
                                            iter.remove();
                                    }
                                }
                                if (items.get(i).isSelected()){
                                    if (!listingsQuery.car.makes.contains(items.get(i).getName().toLowerCase())) {
                                        listingsQuery.car.makes.add(items.get(i).getName().toLowerCase());
                                    }
                                    selected_models.addAll(Arrays.asList(getActivity().getResources().getStringArray(make_resid_map.get(items.get(i).getName().toLowerCase()))));
                                }
                                models_spnr.setItems(Util.getSpinnerValues(
                                                selected_models,
                                                true,
                                                listingsQuery.car.main_models),
                                        "Models", -1, new MultiSpinner.MultiSpinnerListener() {
                                            @Override
                                            public void onItemsSelected(List<KeyPairBoolData> items) {
                                                for(int i=0; i<items.size(); i++) {
                                                    if(!items.get(i).isSelected()) {
                                                        for (Iterator<String> iter = listingsQuery.car.main_models.listIterator(); iter.hasNext();) {
                                                            String test = iter.next();
                                                            if (test.equals(items.get(i).getName()))
                                                                iter.remove();
                                                        }
                                                    } else {
                                                        listingsQuery.car.main_models.add(items.get(i).getName().toLowerCase());
                                                    }
                                                }
                                            }
                                        });
                            }
                            if (selected_models.size() > 0 && models_spnr.getVisibility() == View.INVISIBLE)
                                models_spnr.setVisibility(View.VISIBLE);
                            if (selected_models.size() < 1)
                                models_spnr.setVisibility(View.INVISIBLE);

                        }
                    });

        } else {

            final ArrayAdapter<String> price_mileage_adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn,
                    getResources().getStringArray(R.array.max_mileage_array));
            price_mileage_adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
            setSingleSpinnerSelection(price_spinner, price_mileage_adapter, listingsQuery.max_price);
            setSingleSpinnerSelection(mileage_spinner, price_mileage_adapter, listingsQuery.max_mileage);
            price_spinner.setAdapter(price_mileage_adapter);
            mileage_spinner.setAdapter(price_mileage_adapter);

            price_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(Spinner parent, View view, int position, long id) {
                    FilterFragment.this.listingsQuery.max_price = price_mileage_adapter.getItem(position);
                }
            });
            mileage_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(Spinner parent, View view, int position, long id) {
                    FilterFragment.this.listingsQuery.max_mileage = price_mileage_adapter.getItem(position);
                }
            });

            models_spnr.setItems (Util.getSpinnerValues(
                    FilterFragment.this.listingsQuery.car.models,
                    true,
                    FilterFragment.this.listingsQuery.car.models)
                    ,
                    "Submodels and Trims", -1, new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            for(int i=0; i<items.size(); i++) {
                                if(!items.get(i).isSelected()) {
                                    for (Iterator<String> iter = listingsQuery.car.models.listIterator(); iter.hasNext();) {
                                        String test = iter.next();
                                        if (test.equals(items.get(i).getName()))
                                            iter.remove();
                                    }
                                } else {
                                    listingsQuery.car.models.add(items.get(i).getName().toLowerCase());
                                }
                            }
                        }

                    });
        }



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


        bodytypes_spnr.setItems(Util.getSpinnerValues(
                        Arrays.asList(getActivity().getResources().getStringArray(R.array.body_style_array)),
                        true,
                        listingsQuery.car.bodyTypes),
                        "BodyTypes", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(!items.get(i).isSelected()) {
                        for (Iterator<String> iter = listingsQuery.car.bodyTypes.listIterator(); iter.hasNext();) {
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

        txns_spnr.setItems(Util.getSpinnerValues(
                        Arrays.asList(getActivity().getResources().getStringArray(R.array.txn_array)),
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

        compressors_spnr.setItems(Util.getSpinnerValues(
                        Arrays.asList(getActivity().getResources().getStringArray(R.array.compressor_array)),
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

        drivetrains_spnr.setItems(Util.getSpinnerValues(
                        Arrays.asList(getActivity().getResources().getStringArray(R.array.drivetrain_array)),
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

        tags_spnr.setItems(Util.getSpinnerValues(
                Arrays.asList(getActivity().getResources().getStringArray(R.array.all_tags)),
                true,
                listingsQuery.car.tags),
                "Tags", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(!items.get(i).isSelected()) {
                        for (Iterator<String> iter = listingsQuery.car.tags.listIterator(); iter.hasNext();) {
                            String test = iter.next();
                            if (test.equals(items.get(i).getName()))
                                iter.remove();
                        }
                    } else {
                        listingsQuery.car.tags.add(items.get(i).getName().toLowerCase());
                    }
                }
            }
        });

    }

    public void setSingleSpinnerSelection (Spinner spinner, ArrayAdapter<String> values, String value) {
        try {
            int pos = findPos(values, value);
            spinner.setSelection(pos);
        } catch (Exception e) {
            Log.e (getClass().getCanonicalName(), "error setting single spinner: " + e.getMessage());
            spinner.setSelection(0);
        }

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
