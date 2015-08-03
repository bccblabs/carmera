package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by bski on 7/20/15.
 */
public class BasicSearchFragment extends Fragment implements ScreenShotable {
    public static final String EXTRA_SEARCH_CRIT = "extra_search_crit";
    public String TAG = getClass().getCanonicalName();
    private GenQuery query;

    @Bind(R.id.make_spinner)
    MultiSpinnerSearch make_spinner;

    @Bind(R.id.model_spinner)
    MultiSpinnerSearch model_spinner;

    @Bind(R.id.bodytype_spinner)
    MultiSpinnerSearch bodytype_spinner;

    @Bind(R.id.condition_spinner)
    MultiSpinnerSearch condition_spinner;

    @Bind(R.id.radius_spinner)
    MultiSpinnerSearch radius_spinner;

    @Bind(R.id.search_listings_base)
    ButtonRectangle search_listings;

    @Bind(R.id.transmission_spinner)
    MultiSpinnerSearch transmission_spinner;

    @Bind(R.id.cylinders_spinner)
    MultiSpinnerSearch cylinders_spinner;

    @Bind(R.id.hp_spinner)
    MultiSpinnerSearch hp_spinner;

    @Bind(R.id.tq_spinner)
    MultiSpinnerSearch tq_spinner;

    @Bind(R.id.mpg_spinner)
    MultiSpinnerSearch mpg_spinner;

    @Bind(R.id.ext_colors_spinner)
    MultiSpinnerSearch ext_colors_spinner;

    @Bind(R.id.int_colors_spinner)
    MultiSpinnerSearch int_colors_spinner;

    @Bind(R.id.compressor_spinner)
    MultiSpinnerSearch compressors_spinner;

    @Bind(R.id.drivetrain_spinner)
    MultiSpinnerSearch drivetrain_spinner;

    @Bind(R.id.equipments_spinner)
    MultiSpinnerSearch equipments_spinner;

    @Bind(R.id.search_zipcode)
    EditText zipcode;

    @Bind(R.id.years_spinner)
    MultiSpinnerSearch years_spinner;


    @Bind(R.id.search_min_price)
    MaterialEditText min_price;

    @Bind(R.id.search_max_price)
    MaterialEditText max_price;

    @Bind(R.id.search_max_mileage)
    MaterialEditText max_mileage;

    @OnClick(R.id.search_listings_base)
    public void search_listings() {

        if (!zipcode.getText().toString().equals(""))
            this.query.setZipcode(Integer.parseInt(zipcode.getText().toString()));
        if (!max_price.getText().toString().equals(""))
            this.query.setMax_price(Integer.parseInt(max_price.getText().toString()));
        if (!min_price.getText().toString().equals(""))
            this.query.setMin_price(Integer.parseInt(min_price.getText().toString()));
        if (!max_mileage.getText().toString().equals(""))
            this.query.setMax_mileage(Integer.parseInt(max_mileage.getText().toString()));
        search_callback.OnSearchListings(Parcels.wrap(this.query));
    }

    private Context cxt;

    private HashMap<String, Integer> make_resid_map = new HashMap<>();


    private OnSearchVehiclesListener search_callback = null;

    public interface OnSearchVehiclesListener {
        public void OnSearchListings (Parcelable query);
    }

    @Override
    public void takeScreenShot () {
    }

    @Override
    public Bitmap getBitmap() { return null; }


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
                            query.models.add ("mercedes");
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

        final List<KeyPairBoolData> years_types = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.years)));
        years_spinner.setItems(years_types, "Choose Years", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.years.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.years.add (Integer.parseInt(items.get(i).getName()));
                    }
                }
            }
        });
        final List<KeyPairBoolData> body_types = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.body_style_array)));
        bodytype_spinner.setItems(body_types, "Body Type(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.bodyTypes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.bodyTypes.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> conditions = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.car_state_array)));
        condition_spinner.setItems(conditions, "Conditions(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.conditions.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.conditions.add(items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> radius = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.range_array)));
        radius_spinner.setItems(radius, "Max Distance (miles)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.max_dist = Integer.parseInt(items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> transmissions = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.txn_array)));
        transmission_spinner.setItems(transmissions, "Transmission Type(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (items.get(i).getName().equals("Automatic")) {
                            query.transmissionTypes.add("UNKNOWN");
                            query.transmissionTypes.add("AUTOMATED_MANUAL");
                            query.transmissionTypes.add("DIRECT_DRIVE");
                        }
                        query.transmissionTypes.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> compressor_types = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.compressor_array)));
        compressors_spinner.setItems(compressor_types, "Engine Compressor Type(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (items.get(i).getName().equals("Naturally Aspirated"))
                            query.compressors.add("NA");
                        else
                            query.compressors.add(items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> drivetrains = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.drivetrain_array)));
        drivetrain_spinner.setItems(drivetrains, "Drivetrain (s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (items.get(i).getName().equals("All Wheel Drive"))
                            query.drivetrains.add("four wheel drive");
                        query.drivetrains.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> cylinders = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.cylinder_array)));
        cylinders_spinner.setItems(cylinders, "Cylinder Count", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.cylinders.add (Integer.parseInt(items.get(i).getName().replace("+", "")));
                    }
                }
            }
        });

        final List<KeyPairBoolData> outputs = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.output_array)));
        hp_spinner.setItems(outputs, "Horsepower", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.min_hp = Integer.parseInt(items.get(i).getName().replace("+", ""));
                    }
                }
            }
        });
        tq_spinner.setItems(outputs, "Torque", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.min_tq = Integer.parseInt(items.get(i).getName().replace("+", ""));
                    }
                }
            }
        });

        final List<KeyPairBoolData> mpgs = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.mpg_array)));
        mpg_spinner.setItems(mpgs, "MPG", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.min_mpg = items.get(i).getName().replace("+", "");
                    }
                }
            }
        });

        final List<KeyPairBoolData> colors = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.color_array)));
        ext_colors_spinner.setItems(colors, "Exterior Color(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.ext_colors.add (items.get(i).getName());
                    }
                }
            }
        });

        int_colors_spinner.setItems(colors, "Interior Color(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.int_colors.add (items.get(i).getName());
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
