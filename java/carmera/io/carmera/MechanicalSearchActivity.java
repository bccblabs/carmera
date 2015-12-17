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
public class MechanicalSearchActivity extends AppCompatActivity
                                      implements ObservableScrollViewCallbacks {

    @Bind(R.id.image) View mImageView;
    @Bind(R.id.basic_container) ObservableScrollView mScrollView;

    @Bind(R.id.transmission_spinner) MultiSpinner transmission_spinner;
    @Bind(R.id.cylinders_spinner) MultiSpinner cylinders_spinner;
    @Bind(R.id.hp_spinner) MultiSpinner hp_spinner;
    @Bind(R.id.tq_spinner) MultiSpinner tq_spinner;
    @Bind(R.id.mpg_spinner) MultiSpinner mpg_spinner;
    @Bind(R.id.compressor_spinner) MultiSpinner compressors_spinner;
    @Bind(R.id.drivetrain_spinner) MultiSpinner drivetrain_spinner;

    private ListingsQuery query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mechanical_search);
        ButterKnife.bind(this);

        mScrollView.setScrollViewCallbacks(this);
        query = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));

        final List<String> transmissions = Arrays.asList(getResources().getStringArray(R.array.txn_array));
        transmission_spinner.setItems(Util.getSpinnerValues(transmissions, true), "Choose Transmission Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.transmissionTypes.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (transmissions.get(i).equals("Automatic")) {
                            query.car.transmissionTypes.add("UNKNOWN");
                            query.car.transmissionTypes.add("AUTOMATED_MANUAL");
                            query.car.transmissionTypes.add("DIRECT_DRIVE");
                        } else if (transmissions.get(i).equals("Manual")) {
                            query.car.transmissionTypes.add("MANUAL");
                        }
                    }
                }
            }
        });

        final List<String> compressor_types = Arrays.asList(getResources().getStringArray(R.array.compressor_array));
        compressors_spinner.setItems(Util.getSpinnerValues(compressor_types, true), "Choose Engine Compressor Type(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.compressors.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.compressors.add(compressor_types.get(i));
                    }
                }
            }
        });

        final List<String> drivetrains = Arrays.asList(getResources().getStringArray(R.array.drivetrain_array));
        drivetrain_spinner.setItems(Util.getSpinnerValues(drivetrains, true), "Choose Drivetrain(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.drivenWheels.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        if (drivetrains.get(i).equals("All Wheel Drive"))
                            query.car.drivenWheels.add("four wheel drive");
                        else
                            query.car.drivenWheels.add (drivetrains.get(i));
                    }
                }
            }
        });

        final List<String> cylinders = Arrays.asList(getResources().getStringArray(R.array.cylinder_array));
        cylinders_spinner.setItems(Util.getSpinnerValues(cylinders, true), "Choose Cylinder Count", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.cylinders.clear();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.cylinders.add (Integer.parseInt(cylinders.get(i).replace("+", "")));
                    }
                }
            }
        });

        final List<String> hps = Arrays.asList(getResources().getStringArray(R.array.output_array));
        hp_spinner.setItems(Util.getSpinnerValues(hps, true), "Minimum Horsepower", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.minHp = 0;
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.minHp = Integer.parseInt(hps.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> tqs = Arrays.asList(getResources().getStringArray(R.array.output_array));
        tq_spinner.setItems(Util.getSpinnerValues(tqs, true), "Minimum Torque", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.minTq = 0;
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.minTq = Integer.parseInt(tqs.get(i).replace("+", ""));
                    }
                }
            }
        });

        final List<String> mpgs = Arrays.asList(getResources().getStringArray(R.array.mpg_array));
        mpg_spinner.setItems(Util.getSpinnerValues(mpgs, true), "Minimum Highway MPG", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                query.car.minMpg = 0;
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        query.car.minMpg = Integer.parseInt(mpgs.get(i).replace("+", ""));
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
