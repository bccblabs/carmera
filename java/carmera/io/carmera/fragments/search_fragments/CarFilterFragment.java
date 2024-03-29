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

import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by bski on 1/29/16.
 */
public class CarFilterFragment extends DialogFragment {

    public ListingsQuery listingsQuery;

    private List<String> selected_models = new ArrayList<>();

    public OnResearchListener callback = null;

    private SortedMap<String, Integer> make_resid_map = new TreeMap<>();

    @Bind (R.id.makes_spinner)
    MultiSpinner makes_spnr;

    @Bind (R.id.models_spinner)
    MultiSpinner models_spnr;

    @Bind (R.id.years_spinner)
    Spinner years_spinner;

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

    @Bind (R.id.mpg_spinner) Spinner mpg_spinner;

    @Bind (R.id.hp_spinner) Spinner hp_spinner;

    @Bind (R.id.tq_spinner) Spinner tq_spinner;

    @Bind (R.id.cylinders_spinner) Spinner cylinders_spnr;

    @OnClick(R.id.dismiss_btn)
    void onFilter () {
        this.dismiss();
    }

    @OnClick (R.id.search_btn)
    void onSearch () {
        callback.onResearchCallback(listingsQuery);
        this.dismiss();
    }

    public static CarFilterFragment newInstance() {
        return new CarFilterFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (OnResearchListener) activity;
        if (callback == null) {
            callback = (OnResearchListener) activity;
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
        make_resid_map.put ("gmc", R.array.gmc);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_car_filter, null);
        this.listingsQuery = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_LISTING_QUERY));
        Log.i(this.getClass().getCanonicalName(), new Gson().toJson(listingsQuery, ListingsQuery.class));
        builder.setView(view);
        ButterKnife.bind(this, view);
        init_spinners();
        return builder.create();
    }

    private void init_spinners () {
        final ArrayAdapter<String> mpg_adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_row,
                getResources().getStringArray(R.array.mpg_array));

        final ArrayAdapter<String> output_adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_row,
                getResources().getStringArray(R.array.output_array));

        final ArrayAdapter<String> year_adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_row,
                getResources().getStringArray(R.array.years));

        final ArrayAdapter<String> cylinder_adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_row,
                getResources().getStringArray(R.array.cylinder_array));

        mpg_adapter.setDropDownViewResource(R.layout.view_spinner_row_dropdown);
        output_adapter.setDropDownViewResource(R.layout.view_spinner_row_dropdown);
        year_adapter.setDropDownViewResource(R.layout.view_spinner_row_dropdown);
        cylinder_adapter.setDropDownViewResource(R.layout.view_spinner_row_dropdown);

        mpg_spinner.setAdapter(mpg_adapter);
        hp_spinner.setAdapter(output_adapter);
        tq_spinner.setAdapter(output_adapter);
        cylinders_spnr.setAdapter(cylinder_adapter);
        years_spinner.setAdapter(year_adapter);

        if (listingsQuery.car.minMpg != null)
            Util.setSingleSpinnerSelection(mpg_spinner, mpg_adapter, Integer.toString(listingsQuery.car.minMpg));
        else
            Util.setSingleSpinnerSelection(mpg_spinner, mpg_adapter, Integer.toString(0));

        if (listingsQuery.car.minHp != null)
            Util.setSingleSpinnerSelection(hp_spinner, output_adapter, Integer.toString(listingsQuery.car.minHp));
        else
            Util.setSingleSpinnerSelection(hp_spinner, output_adapter, Integer.toString(0));

        if (listingsQuery.car.minTq != null)
            Util.setSingleSpinnerSelection(tq_spinner, output_adapter, Integer.toString(listingsQuery.car.minTq));
        else
            Util.setSingleSpinnerSelection(tq_spinner, output_adapter, Integer.toString(0));

        if (listingsQuery.car.minYr != null)
            Util.setSingleSpinnerSelection(years_spinner, year_adapter, Integer.toString(listingsQuery.car.minYr));
        else
            Util.setSingleSpinnerSelection(years_spinner, year_adapter, Integer.toString(0));

        if (listingsQuery.car.minCylinders != null)
            Util.setSingleSpinnerSelection(cylinders_spnr, cylinder_adapter, Integer.toString(listingsQuery.car.minCylinders));
        else
            Util.setSingleSpinnerSelection(cylinders_spnr, cylinder_adapter, Integer.toString(0));


        mpg_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                CarFilterFragment.this.listingsQuery.car.minMpg = Integer.parseInt(mpg_adapter.getItem(position));
            }
        });
        hp_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                CarFilterFragment.this.listingsQuery.car.minHp = Integer.parseInt(output_adapter.getItem(position));
            }
        });
        tq_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                CarFilterFragment.this.listingsQuery.car.minTq = Integer.parseInt(output_adapter.getItem(position));
            }
        });

        years_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                CarFilterFragment.this.listingsQuery.car.minYr = Integer.parseInt(year_adapter.getItem(position));
            }
        });

        cylinders_spnr.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                CarFilterFragment.this.listingsQuery.car.minCylinders = Integer.parseInt(cylinder_adapter.getItem(position));
            }
        });

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
                            if (items.get(i).isSelected()) {
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
                                            for (int i = 0; i < items.size(); i++) {
                                                if (!items.get(i).isSelected()) {
                                                    for (Iterator<String> iter = listingsQuery.car.main_models.listIterator(); iter.hasNext(); ) {
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
                "Categories", -1, new MultiSpinner.MultiSpinnerListener() {
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

}
