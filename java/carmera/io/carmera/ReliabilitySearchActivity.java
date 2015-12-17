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
public class ReliabilitySearchActivity extends AppCompatActivity
                                        implements ObservableScrollViewCallbacks {

    @Bind(R.id.complaints_spinner)
    MultiSpinner complaints_spinner;

    @Bind(R.id.recalls_spinner)
    MultiSpinner recalls_spinner;

    @Bind(R.id.basic_container)
    ObservableScrollView mScrollView;

    @Bind(R.id.image)
    View mImageView;

    private ListingsQuery query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reliability_search);
        ButterKnife.bind(this);

        mScrollView.setScrollViewCallbacks(this);
        query = Parcels.unwrap (getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));

        final List<String> complaints = Arrays.asList(getResources().getStringArray(R.array.complaints_array));
        complaints_spinner.setItems(Util.getSpinnerValues(complaints, true), "By Complaints", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List< KeyPairBoolData > items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.tags.add(items.get(i).getName());
                    }
                }

            }
        });

        final List<String> recalls = Arrays.asList(getResources().getStringArray(R.array.recalls_array));
        recalls_spinner.setItems(Util.getSpinnerValues(recalls, true), "By Recalls", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List< KeyPairBoolData > items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.tags.add(items.get(i).getName());
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
