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
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by bski on 7/20/15.
 */
public class BasicSearchFragment extends Fragment implements ScreenShotable {
    public static final String EXTRA_SEARCH_CRIT = "extra_search_crit";

    private GenQuery query = new GenQuery();

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

    @Bind(R.id.build_details)
    ButtonRectangle build_details;

    @Bind(R.id.search_zipcode)
    EditText zipcode;


    @OnClick(R.id.search_listings_base)
    public void search_listings() {
        search_callback.OnSearchListings(Parcels.wrap(this.query));
    }

    @OnClick(R.id.build_details)
    public void build_details() {
        build_query_callback.OnBuildQueryDetails(Parcels.wrap(this.query));
    }

    private Bitmap bitmap;
    private View containerView;

    private Context cxt;

    private HashMap<String, Integer> make_resid_map = new HashMap<>();


    private OnSearchVehiclesListener search_callback = null;

    private OnBuildQueryDetailsListener build_query_callback = null;

    public interface OnSearchVehiclesListener {
        public void OnSearchListings (Parcelable query);
    }

    public interface OnBuildQueryDetailsListener {
        public void OnBuildQueryDetails (Parcelable query);
    }

    @Override
    public void takeScreenShot () {
    }

    @Override
    public Bitmap getBitmap() { return bitmap; }


    public static BasicSearchFragment newInstance () {
        BasicSearchFragment fragment = new BasicSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.base_search, container, false);
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
//        make_resid_map.put ("Land Rover", R.array.land);
        make_resid_map.put ("Lexus", R.array.lexux);
        make_resid_map.put ("Lincoln", R.array.lincoln);
        make_resid_map.put ("Lotus", R.array.lotus);
        make_resid_map.put ("Mazda", R.array.mazda);
        make_resid_map.put ("Mercedes-Benz", R.array.mercedes);
        make_resid_map.put ("Mini", R.array.mini);
//        make_resid_map.put ("Mitsubishi", R.array.mit);
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
                        query.makes.add (items.get(i).getName());
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
                model_spinner.setItems(getModels(selected_makes), "Model(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        query.models.clear();
                        for(int i=0; i<items.size(); i++) {
                            if(items.get(i).isSelected()) {
                                query.models.add (items.get(i).getName());
                                Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
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
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
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
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
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
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
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
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.max_dist = Integer.parseInt(items.get(i).getName());
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        this.containerView = v.findViewById(R.id.basic_search_container);

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
            build_query_callback = (OnBuildQueryDetailsListener) activity;
            search_callback = (OnSearchVehiclesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    private List<KeyPairBoolData> getModels (List<String> makes) {
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<makes.size(); i++) {
            kv_list.addAll(Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(make_resid_map.get(makes.get(i))))));
        }
        return kv_list;
    }
}
