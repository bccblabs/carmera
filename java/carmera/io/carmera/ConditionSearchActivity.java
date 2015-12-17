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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 12/7/15.
 */
public class ConditionSearchActivity extends AppCompatActivity
                                     implements ObservableScrollViewCallbacks {

    @Bind (R.id.image) View mImageView;
    @Bind (R.id.basic_container) ObservableScrollView mScrollView;


    @Bind(R.id.condition_spinner) MultiSpinner condition_spinner;
    @Bind(R.id.min_msrp_spinner) MultiSpinner min_msrp_spinner;
    @Bind(R.id.max_msrp_spinner) MultiSpinner max_msrp_spinner;
    @Bind(R.id.min_lease_spinner) MultiSpinner min_lease_spinner;
    @Bind(R.id.max_lease_spinner) MultiSpinner max_lease_spinner;
    @Bind(R.id.max_mileage_spinner) MultiSpinner max_mileage_spinner;

    private ListingsQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition_search);
        ButterKnife.bind(this);

        mScrollView.setScrollViewCallbacks(this);
        query = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));


        final List<String> conditions = Arrays.asList(getResources().getStringArray(R.array.car_state_array));
        condition_spinner.setItems(Util.getSpinnerValues(conditions, true), "Certified / New / Used", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.api.conditions.clear();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        query.api.conditions.add(items.get(i).getName());
                    }
                }
            }
        });

        final List<String> min_msrp = Arrays.asList(getResources().getStringArray(R.array.min_msrp_array));
        Collections.sort(min_msrp, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
            }
        });

        min_msrp_spinner.setItems(Util.getSpinnerValues(min_msrp, false), "Minimum Price", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (min_msrp.get(i).equals("None"))
                            query.api.msrpmin = 0;
                        else
                            query.api.msrpmin = Integer.valueOf(min_msrp.get(i));
                        break;
                    }
                }
            }
        });

        final List<String> max_msrp = Arrays.asList(getResources().getStringArray(R.array.max_msrp_array));
        Collections.sort(max_msrp, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o2).compareTo(Integer.valueOf(o1));
            }
        });

        max_msrp_spinner.setItems(Util.getSpinnerValues(max_msrp, false), "Maximum Price", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (max_msrp.get(i).equals("None"))
                            query.api.msrpmax = Integer.MAX_VALUE;
                        else {
                            query.api.msrpmax = Integer.valueOf(max_msrp.get(i));

                        }
                        break;
                    }
                }
            }
        });
        final List<String> min_lease = Arrays.asList(getResources().getStringArray(R.array.min_lease_array));
        Collections.sort(min_lease, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
            }
        });

        min_lease_spinner.setItems(Util.getSpinnerValues(min_lease, false), "Minimum Lease", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (items.get(i).equals("None"))
                            query.api.lpmin = 0;
                        else
                            query.api.lpmin = Integer.valueOf(min_lease.get(i));
                        break;
                    }
                }
            }
        });

        final List<String> max_lease = Arrays.asList(getResources().getStringArray(R.array.max_lease_array));
        Collections.sort(max_lease, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o2).compareTo(Integer.valueOf(o1));
            }
        });
        max_lease_spinner.setItems(Util.getSpinnerValues(max_lease, false), "Maximum Lease", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (max_lease.get(i).equals("None"))
                            query.api.lpmax = Integer.MAX_VALUE;
                        else
                            query.api.lpmax = Integer.valueOf(max_lease.get(i));
                        break;
                    }
                }
            }
        });


        final List<String> max_mileage = Arrays.asList(getResources().getStringArray(R.array.max_mileage_array));
        Collections.sort(max_mileage, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o2).compareTo(Integer.valueOf(o1));
            }
        });
        max_mileage_spinner.setItems(Util.getSpinnerValues(max_mileage, false), "Maximum Mileage", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        if (max_mileage.get(i).equals("None"))
                            query.api.max_mileage = Integer.MAX_VALUE;
                        else
                            query.api.max_mileage = Integer.valueOf(max_mileage.get(i));
                        break;
                    }
                }
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
