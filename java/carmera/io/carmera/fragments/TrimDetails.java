package carmera.io.carmera.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.TrimDetailsViewer;
import carmera.io.carmera.models.DataEntryFloat;
import carmera.io.carmera.models.TrimData;

/**
 * Created by bski on 7/13/15.
 */
public class TrimDetails extends Fragment  {
    @Bind(R.id.rating_chart)
    HorizontalBarChart rating_chart;
    @Bind(R.id.price_chart)
    HorizontalBarChart price_chart;
    @Bind(R.id.tco_chart)
    HorizontalBarChart tco_chart;
    @Bind(R.id.scroll_container)
    ObservableScrollView container;


    private String TAG = getClass().getCanonicalName();

    private TrimData trimData;


    public static TrimDetails newInstance() {
        return new TrimDetails();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.trimData = Parcels.unwrap(args.getParcelable(TrimDetailsViewer.EXTRA_TRIM_DATA));
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.trim_highlights, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        String rating = "Drivers' Rating",
                pricing = "Pricing",
                tco = "True Cost To Own";
        setChart(tco_chart, trimData.getAvgTco(), tco);
        setChart(price_chart, trimData.getAvgPrice(), pricing);
        setChart(rating_chart, trimData.getAvgRating(), rating);
        MaterialViewPagerHelper.registerScrollView(getActivity(), container, null);
    }

    private void setChart ( HorizontalBarChart chart,  List<DataEntryFloat> data_list, String name) {
        if (data_list.size() > 0) {
            List<String> x = new ArrayList<>();
            List<BarEntry> y = new ArrayList<>();

            for (int i = 0; i < data_list.size(); i++) {
                x.add(data_list.get(i).getName());
                y.add(new BarEntry(data_list.get(i).getValue(), i));
            }
            BarDataSet set = new BarDataSet(y, name);
            set.setBarSpacePercent(35f);
            List<BarDataSet> yDataSets = new ArrayList<>();
            yDataSets.add(set);
            BarData data = new BarData(x, yDataSets);
            data.setValueTextSize(10f);
            chart.setData(data);
            chart.setDescription(name);
            chart.animateXY(3000, 3000);
        } else {
            chart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
