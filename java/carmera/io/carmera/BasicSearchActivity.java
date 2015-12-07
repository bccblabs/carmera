package carmera.io.carmera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 12/7/15.
 */
public class BasicSearchActivity extends AppCompatActivity
                                 implements ObservableScrollViewCallbacks {


    @Bind (R.id.image) View mImageView;
    @Bind (R.id.basic_container) ObservableScrollView mScrollView;

    @Bind (R.id.years_spinner) MultiSpinner years_spinner;
    @Bind (R.id.make_spinner) MultiSpinnerSearch make_spinner;
    @Bind (R.id.model_spinner) MultiSpinnerSearch model_spinner;
    @Bind (R.id.bodytype_spinner) MultiSpinner bodytype_spinner;
    @Bind (R.id.tags_spinner) MultiSpinner tags_spinner;


    private ListingsQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_search);
        ButterKnife.bind(this);

        mScrollView.setScrollViewCallbacks(this);
        query = Parcels.unwrap (getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        final SortedMap<String, Integer> make_resid_map = new TreeMap<>();

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
        make_resid_map.put("Volkswagen", R.array.volkswagen);
        make_resid_map.put("Volvo", R.array.volvo);

        List<String> all_makes = new ArrayList<>();
        all_makes.addAll(make_resid_map.keySet());

        final List<KeyPairBoolData> makes = Util.getSpinnerValues(all_makes);

        make_spinner.setItems(makes, "Make(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_makes = new ArrayList<String>();
                query.car.makes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_makes.add(items.get(i).getName());
                        if (items.get(i).getName().equals("Mercedes-Benz"))
                            query.car.makes.add ("mercedes");
                        else
                            query.car.makes.add (items.get(i).getName());
                    }
                }
                model_spinner.setItems(getModels(make_resid_map, selected_makes), "Model(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        query.car.models.clear();
                        for(int i=0; i<items.size(); i++) {
                            if(items.get(i).isSelected()) {
                                query.car.models.add (items.get(i).getName());
                            }
                        }
                    }
                });
            }
        });


        model_spinner.setItems(getModels(make_resid_map, all_makes), "Models(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.models.clear();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        query.car.models.add(items.get(i).getName());
                    }
                }
            }
        });

        final List<String> years_types = Arrays.asList(getResources().getStringArray(R.array.years));
        years_spinner.setItems(Util.getSpinnerValues(years_types), "Choose Year(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.years.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.years.add (Integer.parseInt(years_types.get(i)));
                    }
                }
            }
        });
        final List<String> body_types = Arrays.asList(getResources().getStringArray(R.array.body_style_array));
        bodytype_spinner.setItems(Util.getSpinnerValues(body_types), "Choose Body Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.bodyTypes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.bodyTypes.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<String> tags = Arrays.asList(getResources().getStringArray(R.array.tags_array));
        tags_spinner.setItems(Util.getSpinnerValues(tags), "Popular Search", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.tags.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.tags.add(items.get(i).getName());
                    }
                }

            }
        });
    }

    private List<KeyPairBoolData> getModels (SortedMap<String, Integer> make_resid_map, List<String> makes) {
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<makes.size(); i++) {
            kv_list.addAll(Util.getSpinnerValues(Arrays.asList(getResources().getStringArray(make_resid_map.get(makes.get(i))))));
        }
        return kv_list;
    }


    @Override
    public void onBackPressed() {
        Intent returned_intent = new Intent();
        returned_intent.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, query));
        setResult(Activity.RESULT_OK, returned_intent);
        finish();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }


    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
