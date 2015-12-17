package carmera.io.carmera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class EquipmentSearchActivity extends AppCompatActivity
                                     implements ObservableScrollViewCallbacks {
    @Bind(R.id.image) View mImageView;
    @Bind(R.id.basic_container) ObservableScrollView mScrollView;

    @Bind(R.id.ext_colors_spinner) MultiSpinner ext_colors_spinner;
    @Bind(R.id.int_colors_spinner) MultiSpinner int_colors_spinner;
    @Bind(R.id.equipments_spinner) MultiSpinnerSearch equipments_spinner;

    private ListingsQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colors_equipments_search);
        ButterKnife.bind(this);

        mScrollView.setScrollViewCallbacks(this);
        query = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));


        final List<String> ext_colors = Arrays.asList(getResources().getStringArray(R.array.color_array));
        ext_colors_spinner.setItems(Util.getSpinnerValues(ext_colors, true), "Exterior Color(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_ext = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        selected_ext.add(ext_colors.get(i));
                    }
                }
                query.api.ext_colors = selected_ext;
            }
        });


        final List<String> int_colors = Arrays.asList(getResources().getStringArray(R.array.color_array));
        int_colors_spinner.setItems(Util.getSpinnerValues(int_colors, true), "Interior Color(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_int = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        selected_int.add(int_colors.get(i));
                    }
                }
                query.api.int_colors = selected_int;
            }
        });

        final List<KeyPairBoolData> equipments = Util.getSpinnerValues(Arrays.asList(getResources().getStringArray(R.array.equipment_array)), true);
        equipments_spinner.setItems(equipments, "Equipments(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_eqp = new ArrayList<>();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_eqp.add(items.get(i).getName());
                    }
                }
                query.api.equipments = selected_eqp;
            }
        });
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
