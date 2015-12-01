package carmera.io.carmera.fragments.data_fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.parceler.Parcels;

import java.util.ArrayList;

import carmera.io.carmera.CarmeraApp;
import carmera.io.carmera.R;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/11/15.
 */
public class Ratings extends Fragment {

    public static Ratings newInstance() {
        return new Ratings();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bar_chart, container, false);
        BarChart bar_chart = (BarChart) v.findViewById(R.id.chartview);
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        carmera.io.carmera.models.car_data_subdocuments.Ratings src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_RATINGS));

        if (src_data.rating_fun_to_drive != null && src_data.rating_fun_to_drive > 0) {
            xVals.add(getString(R.string.rating_fun_to_drive));
            entries.add (new BarEntry(src_data.rating_fun_to_drive, 0));
        }
        if (src_data.rating_build_quality != null && src_data.rating_build_quality > 0) {
            xVals.add(getString(R.string.rating_build_quality));
            entries.add (new BarEntry(src_data.rating_build_quality, 1));
        }
        if (src_data.rating_comfort != null && src_data.rating_comfort > 0) {
            xVals.add(getString(R.string.rating_comfort));
            entries.add (new BarEntry(src_data.rating_comfort, 2));
        }
        if (src_data.rating_performance != null && src_data.rating_performance > 0) {
            xVals.add(getString(R.string.rating_performance));
            entries.add (new BarEntry(src_data.rating_performance, 3));
        }
        if (src_data.rating_reliability != null && src_data.rating_reliability > 0) {
            xVals.add(getString(R.string.rating_reliability));
            entries.add (new BarEntry(src_data.rating_reliability, 4));
        }
        if (src_data.rating_int != null && src_data.rating_int > 0) {
            xVals.add(getString(R.string.rating_int));
            entries.add (new BarEntry(src_data.rating_int, 5));
        }

        if (src_data.rating_ext != null && src_data.rating_ext > 0) {
            xVals.add(getString(R.string.rating_ext));
            entries.add (new BarEntry(src_data.rating_ext, 6));
        }

        BarDataSet bar_data_set = new BarDataSet(entries, getString(R.string.ratings));

        bar_data_set.setColors(ColorTemplate.LIBERTY_COLORS);
        bar_data_set.setValueTextColor(R.color.card_pressed);
        bar_data_set.setValueTextSize(12f);

        BarData barData = new BarData(xVals, bar_data_set);
        Typeface canaro = CarmeraApp.canaroExtraBold;
        barData.setValueTypeface(canaro);
        bar_chart.getXAxis().setTypeface(canaro);
        bar_chart.setDescription("");
        bar_chart.setData(barData);


        bar_chart.getLegend().setEnabled(false);
        bar_chart.setPinchZoom(false);
        bar_chart.setDrawBarShadow(true);
        bar_chart.setDrawGridBackground(false);
        bar_chart.getAxisLeft().setEnabled(false);
        bar_chart.getAxisRight().setEnabled(false);
        bar_chart.setDrawBarShadow(false);
        bar_chart.setDrawHighlightArrow(false);
        bar_chart.setClickable(false);
        bar_chart.setFilterTouchesWhenObscured(false);
        bar_chart.setTouchEnabled(false);
        bar_chart.setSaveEnabled(false);
        bar_chart.setFocusable(false);
        bar_chart.setScaleEnabled(false);
        bar_chart.setPinchZoom(false);
        return v;
    }

}
