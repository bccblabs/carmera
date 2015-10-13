package carmera.io.carmera.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 10/12/15.
 */
public class BasicSearchFragment extends SearchFragment {

    private Context cxt;

    public static BasicSearchFragment newInstance() {
        return new BasicSearchFragment();
    }

    @Bind(R.id.years_spinner) MultiSpinner years_spinner;

    @Bind(R.id.make_spinner) MultiSpinnerSearch make_spinner;

    @Bind(R.id.model_spinner) MultiSpinnerSearch model_spinner;

    @Bind(R.id.bodytype_spinner) MultiSpinner bodytype_spinner;

    @Bind(R.id.condition_spinner) MultiSpinner condition_spinner;

    @Bind(R.id.basic_container) public ObservableScrollView basic_container;

    private SortedMap<String, Integer> make_resid_map = new TreeMap<>();

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.basic_search, container, false);
        ButterKnife.bind(this, v);
        cxt = getActivity();
        init_spinners();
        return v;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), basic_container, null);
    }

    private List<KeyPairBoolData> getModels (List<String> makes) {
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<makes.size(); i++) {
            kv_list.addAll(Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(make_resid_map.get(makes.get(i))))));
        }
        return kv_list;
    }

    @Override
    public void init_spinners() {
        make_resid_map.put ("Acura", R.array.acura);
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
                getGenQuery().makes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_makes.add(items.get(i).getName());
                        if (items.get(i).getName().equals("Mercedes-Benz"))
                            getGenQuery().makes.add ("mercedes");
                        else
                            getGenQuery().makes.add (items.get(i).getName());
                    }
                }
                model_spinner.setItems(getModels(selected_makes), "Model(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        getGenQuery().models.clear();
                        for(int i=0; i<items.size(); i++) {
                            if(items.get(i).isSelected()) {
                                getGenQuery().models.add (items.get(i).getName());
                            }
                        }
                    }
                });
            }
        });


        model_spinner.setItems(getModels(all_makes), "Models(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                getGenQuery().models.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        getGenQuery().models.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<String> years_types = Arrays.asList(cxt.getResources().getStringArray(R.array.years));
        years_spinner.setItems(years_types, "Choose Year(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().years.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().years.add (Integer.parseInt(years_types.get(i)));
                    }
                }
            }
        });
        final List<String> body_types = Arrays.asList(cxt.getResources().getStringArray(R.array.body_style_array));
        bodytype_spinner.setItems(body_types, "Choose Body Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().bodyTypes.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().bodyTypes.add (body_types.get(i));
                    }
                }
            }
        });

        final List<String> conditions = Arrays.asList(cxt.getResources().getStringArray(R.array.car_state_array));
        condition_spinner.setItems(conditions, "Certified / New / Used", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                getGenQuery().conditions.clear();
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        getGenQuery().conditions.add(conditions.get(i));
                    }
                }
            }
        });
    }
}
