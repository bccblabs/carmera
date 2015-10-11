package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 7/20/15.
 */
public class BasicSearchFragment extends Fragment {
    public static final String EXTRA_SEARCH_CRIT = "extra_search_crit";
    public String TAG = getClass().getCanonicalName();
    private GenQuery query;

    @Bind(R.id.make_spinner)
    MultiSpinnerSearch make_spinner;

    @Bind(R.id.model_spinner)
    MultiSpinnerSearch model_spinner;

    @Bind(R.id.bodytype_spinner)
    MultiSpinner bodytype_spinner;

    @Bind(R.id.condition_spinner)
    MultiSpinner condition_spinner;

    @Bind(R.id.transmission_spinner)
    MultiSpinner transmission_spinner;

    @Bind(R.id.cylinders_spinner)
    MultiSpinner cylinders_spinner;

    @Bind(R.id.hp_spinner)
    MultiSpinner hp_spinner;

    @Bind(R.id.tq_spinner)
    MultiSpinner tq_spinner;

    @Bind(R.id.mpg_spinner)
    MultiSpinner mpg_spinner;

    @Bind(R.id.ext_colors_spinner)
    MultiSpinner ext_colors_spinner;

    @Bind(R.id.int_colors_spinner)
    MultiSpinner int_colors_spinner;

    @Bind(R.id.compressor_spinner)
    MultiSpinner compressors_spinner;

    @Bind(R.id.drivetrain_spinner)
    MultiSpinner drivetrain_spinner;

    @Bind(R.id.equipments_spinner)
    MultiSpinnerSearch equipments_spinner;

    @Bind(R.id.years_spinner)
    MultiSpinner years_spinner;

    @OnClick(R.id.search_listings_base)
    public void search_listings() {
    }

    private Context cxt;

    private SortedMap<String, Integer> make_resid_map = new TreeMap<>();


    private OnSearchVehiclesListener search_callback = null;

    public interface OnSearchVehiclesListener {
        void OnSearchListings (Parcelable query);
    }

    public static BasicSearchFragment newInstance () {
        BasicSearchFragment fragment = new BasicSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.base_search, container, false);
        query = new GenQuery();
        ButterKnife.bind(this, v);
        make_resid_map.put("Acura", R.array.acura);
        make_resid_map.put ("Audi", R.array.audi);
        make_resid_map.put ("Aston Martin", R.array.astonmartin);
        make_resid_map.put ("Bentley", R.array.bentley);
        make_resid_map.put ("BMW", R.array.bmw);
        make_resid_map.put ("Buick", R.array.buick);
        make_resid_map.put ("Cadillac", R.array.cadillac);
        make_resid_map.put ("Chevrolet", R.array.chevrolet);
        make_resid_map.put ("Chrysler", R.array.chrysler);
        make_resid_map.put ("Dodge", R.array.dodge);
        make_resid_map.put ("Ferrari", R.array.ferrari);
        make_resid_map.put ("Fiat", R.array.fiat);
        make_resid_map.put ("Fisker", R.array.fisker);
        make_resid_map.put ("Ford", R.array.ford);
        make_resid_map.put ("GMC", R.array.gmc);
        make_resid_map.put ("Honda", R.array.honda);
        make_resid_map.put ("Hyundai", R.array.hyundai);
        make_resid_map.put ("Infiniti", R.array.infiniti);
        make_resid_map.put ("Jaguar", R.array.jaguar);
        make_resid_map.put ("Jeep", R.array.jeep);
        make_resid_map.put ("Kia", R.array.kia);
        make_resid_map.put ("Lamborghini", R.array.lamboghini);
        make_resid_map.put ("Land Rover", R.array.lr);
        make_resid_map.put ("Lexus", R.array.lexus);
        make_resid_map.put ("Lincoln", R.array.lincoln);
        make_resid_map.put ("Lotus", R.array.lotus);
        make_resid_map.put ("Mazda", R.array.mazda);
        make_resid_map.put ("Mercedes-Benz", R.array.mercedes);
        make_resid_map.put ("Mini", R.array.mini);
        make_resid_map.put ("Mitsubishi", R.array.mitsubishi);
        make_resid_map.put ("Nissan", R.array.nissan);
        make_resid_map.put ("Porsche", R.array.porsche);
        make_resid_map.put ("Scion", R.array.scion);
        make_resid_map.put ("Smart", R.array.smart);
        make_resid_map.put ("Subaru", R.array.subaru);
        make_resid_map.put ("Suzuki", R.array.suzuki);
        make_resid_map.put ("Toyota", R.array.toyota);
        make_resid_map.put ("Volkswagen", R.array.volkswagen);
        make_resid_map.put ("Volvo", R.array.volvo);

        List<String> all_makes = new ArrayList<>();
        all_makes.addAll(make_resid_map.keySet());

        final List<KeyPairBoolData> makes = Util.getSpinnerValues(all_makes);


        make_spinner.setItems(makes, "Make(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_makes = new ArrayList<String>();
                query.makes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_makes.add(items.get(i).getName());
                        if (items.get(i).getName().equals("Mercedes-Benz"))
                            query.makes.add ("mercedes");
                        else
                            query.makes.add (items.get(i).getName());
                    }
                }
                model_spinner.setItems(getModels(selected_makes), "Model(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        query.models.clear();
                        for(int i=0; i<items.size(); i++) {
                            if(items.get(i).isSelected()) {
                                query.models.add (items.get(i).getName());
                            }
                        }
                    }
                });
            }
        });


        model_spinner.setItems(getModels(all_makes), "Models(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.models.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.models.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<String> years_types = Arrays.asList(cxt.getResources().getStringArray(R.array.years));
        years_spinner.setItems(years_types, "Choose Year(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.years.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.years.add (Integer.parseInt(years_types.get(i)));
                    }
                }
            }
        });
        final List<String> body_types = Arrays.asList(cxt.getResources().getStringArray(R.array.body_style_array));
        bodytype_spinner.setItems(body_types, "Choose Body Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.bodyTypes.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.bodyTypes.add (body_types.get(i));
                    }
                }
            }
        });

        final List<String> conditions = Arrays.asList(cxt.getResources().getStringArray(R.array.car_state_array));
        condition_spinner.setItems(conditions, "Certified / New / Used", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.conditions.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.conditions.add(conditions.get(i));
                    }
                }
            }
        });

        final List<String> transmissions = Arrays.asList(cxt.getResources().getStringArray(R.array.txn_array));
        transmission_spinner.setItems(transmissions, "Choose Transmission Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.transmissionTypes.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        if (transmissions.get(i).equals("Automatic")) {
                            query.transmissionTypes.add("UNKNOWN");
                            query.transmissionTypes.add("AUTOMATED_MANUAL");
                            query.transmissionTypes.add("DIRECT_DRIVE");
                        } else if (transmissions.get(i).equals("Manual")) {
                            query.transmissionTypes.add ("MANUAL");
                        }
                    }
                }
            }
        });

        final List<String> compressor_types = Arrays.asList(cxt.getResources().getStringArray(R.array.compressor_array));
        compressors_spinner.setItems(compressor_types, "Choose Engine Compressor Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.compressors.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        if (compressor_types.get(i).equals("Naturally Aspirated"))
                            query.compressors.add("NA");
                        else
                            query.compressors.add(compressor_types.get(i));
                    }
                }
            }
        });

        final List<String> drivetrains = Arrays.asList(cxt.getResources().getStringArray(R.array.drivetrain_array));
        drivetrain_spinner.setItems(drivetrains, "Choose Drivetrain(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.drivetrains.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        if (drivetrains.get(i).equals("All Wheel Drive"))
                            query.drivetrains.add("four wheel drive");
                        else
                            query.drivetrains.add (drivetrains.get(i));
                    }
                }
            }
        });

        final List<String> cylinders = Arrays.asList(cxt.getResources().getStringArray(R.array.cylinder_array));
        cylinders_spinner.setItems(cylinders, "Choose Cylinder Count", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.cylinders.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.cylinders.add (Integer.parseInt(cylinders.get(i).replace("+", "")));
                    }
                }
            }
        });

        final List<String> hps = Arrays.asList(cxt.getResources().getStringArray(R.array.output_array));
        hp_spinner.setItems(hps, "Minimum Horsepower", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.min_hp = 0;
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.min_hp = Integer.parseInt(hps.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> tqs = Arrays.asList(cxt.getResources().getStringArray(R.array.output_array));
        tq_spinner.setItems(tqs, "Minimum Torque", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.min_tq = 0;
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.min_tq = Integer.parseInt(tqs.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> mpgs = Arrays.asList(cxt.getResources().getStringArray(R.array.mpg_array));
        mpg_spinner.setItems(mpgs, "Minimum Highway MPG", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                query.min_tq = 0;
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.min_mpg = mpgs.get(i).replace("+", "");
                    }
                }
            }
        });

        final List<String> ext_colors = Arrays.asList(cxt.getResources().getStringArray(R.array.color_array));
        ext_colors_spinner.setItems(ext_colors, "Exterior Color(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.ext_colors.add (ext_colors.get(i));
                    }
                }
            }
        });

        final List<String> int_colors = Arrays.asList(cxt.getResources().getStringArray(R.array.color_array));
        int_colors_spinner.setItems(int_colors, "Interior Color(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        query.int_colors.add (int_colors.get(i));
                    }
                }
            }
        });

        final List<KeyPairBoolData> equipments = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.equipment_array)));
        equipments_spinner.setItems(equipments, "Equipments(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.equipments.add (items.get(i).getName());
                    }
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        this.cxt = activity;
        try {
            search_callback = (OnSearchVehiclesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate (savedBundleInstance);

//        if (args != null) {
//            Parcelable search_creteria = args.getParcelable(EXTRA_SEARCH_CRIT);
//            query = Parcels.unwrap(search_creteria);
//            Log.i (TAG, "not null");
//        } else {
//            Log.i(TAG, "null");
//        }


    }

    private List<KeyPairBoolData> getModels (List<String> makes) {
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<makes.size(); i++) {
            kv_list.addAll(Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(make_resid_map.get(makes.get(i))))));
        }
        return kv_list;
    }
}
