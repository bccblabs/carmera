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
public class Prices extends Fragment {

    public static Prices newInstance() {
        return new Prices();
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
        carmera.io.carmera.models.car_data_subdocuments.Prices src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_PRICES));

        if (src_data.baseMSRP != null && src_data.baseMSRP > 0) {
            xVals.add(getString(R.string.msrp));
            entries.add (new BarEntry(src_data.baseMSRP, 0));
        }
        if (src_data.usedPrivateParty != null && src_data.usedPrivateParty > 0) {
            xVals.add(getString(R.string.private_party));
            entries.add (new BarEntry(src_data.usedPrivateParty, 1));
        }
        if (src_data.baseInvoice != null && src_data.baseInvoice > 0) {
            xVals.add(getString(R.string.invoice));
            entries.add (new BarEntry(src_data.baseInvoice, 2));
        }
        if (src_data.usedTmvRetail != null && src_data.usedTmvRetail > 0) {
            xVals.add(getString(R.string.tmv));
            entries.add (new BarEntry(src_data.usedTmvRetail, 3));
        }
        if (src_data.usedTradeIn != null && src_data.usedTradeIn > 0) {
            xVals.add(getString(R.string.trade_in));
            entries.add (new BarEntry(src_data.usedTradeIn, 4));
        }

        BarDataSet bar_data_set = new BarDataSet(entries, getString(R.string.prices));

        bar_data_set.setColors(ColorTemplate.VORDIPLOM_COLORS);
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
