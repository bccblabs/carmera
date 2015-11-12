package carmera.io.carmera.fragments.data_fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.CarmeraApp;
import carmera.io.carmera.R;
import carmera.io.carmera.models.car_data_subdocuments.ComplaintDetails;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/11/15.
 */
public class Complaints extends Fragment {

    @Bind(R.id.chartview) PieChart pie_chart;
    private carmera.io.carmera.models.car_data_subdocuments.Complaints src_data;

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_CMPL));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pie_chart, container, false);
        ButterKnife.bind(this, v);
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();
        for (ComplaintDetails entry : src_data.details) {
            xVals.add (entry.getComponent());
            entries.add (new Entry (entry.count, xVals.indexOf(entry.component)));
        }

        PieDataSet pie_data_set = new PieDataSet(entries, "Complaints");
        pie_data_set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pie_data_set.setSliceSpace(2f);
        pie_data_set.setValueTextColor(R.color.card_pressed);
        pie_data_set.setValueTextSize(12f);
        PieData pie_data = new PieData(xVals, pie_data_set);
        Typeface canaro = CarmeraApp.canaroExtraBold;
        pie_data.setValueTypeface(canaro);


        pie_chart.setCenterTextTypeface(canaro);
        pie_chart.setCenterTextSize(10f);
        pie_chart.setCenterTextTypeface(canaro);
        pie_chart.getLegend().setEnabled(false);
        pie_chart.setHoleRadius(20f);
        pie_chart.setDescription("");
        pie_chart.setTransparentCircleRadius(25f);
        pie_chart.setData(pie_data);
        return v;
    }


}
