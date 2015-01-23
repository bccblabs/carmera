package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.TMV;
import veme.cario.com.CARmera.requests.TMVRequest;

public class PricingFragment extends Fragment {
    private static String JSON_HASH_KEY_NEW;
    private String TAG = PricingFragment.class.getSimpleName();
    private static String JSON_HASH_KEY_USED;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private Map<Double, String> price_map = new HashMap<Double, String>();
    private final class NewPricingRequestListener implements RequestListener<TMV> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            PricingFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(TMV tmv) {
            boolean has_data = false;
            if (PricingFragment.this.price_map.size() > 0)
                has_data = true;
            if (tmv.getTmv().getNationalBasePrice()!= null) {
                append_num (tmv.getTmv().getNationalBasePrice().getBaseMSRP(), "New Base MSRP");
                append_num(tmv.getTmv().getNationalBasePrice().getBaseInvoice(), "New Base Invoice");
                append_num(tmv.getTmv().getNationalBasePrice().getTmv(), "New Base TMV");
            }
            if (tmv.getTmv().getTotalWithOptions()!= null) {
                append_num(tmv.getTmv().getTotalWithOptions().getBaseMSRP(), "New MSRP with Options");
                append_num(tmv.getTmv().getTotalWithOptions().getBaseInvoice(), "New Invoice with Options");
                append_num(tmv.getTmv().getTotalWithOptions().getTmv(),"New TMV with Options");
            }
            if (tmv.getTmv().getOfferPrice()!= null)
                append_num(tmv.getTmv().getOfferPrice(), "New Offer Price");

            if (has_data)
                bar_graph();
        }
    }

    private final class UsedPricingRequestListener implements RequestListener<TMV> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            PricingFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(TMV tmv) {
            boolean has_data = false;
            if (PricingFragment.this.price_map.size() > 0)
                has_data = true;
            if (tmv.getTmv().getNationalBasePrice()!= null) {
                append_num(tmv.getTmv().getNationalBasePrice().getUsedTmvRetail(), "Used Base TMV");
                append_num(tmv.getTmv().getNationalBasePrice().getUsedPrivateParty(), "Used Base Private Party");
                append_num(tmv.getTmv().getNationalBasePrice().getUsedTradeIn(), "Used Base Trade-In");
            }
            if (tmv.getTmv().getTotalWithOptions()!= null) {
                append_num(tmv.getTmv().getTotalWithOptions().getUsedTmvRetail(), "Used Base with Options");
                append_num(tmv.getTmv().getTotalWithOptions().getUsedPrivateParty(), "Used Private Party with Options");
                append_num(tmv.getTmv().getTotalWithOptions().getUsedTradeIn(), "Used Trade-In with Options");
            }
            if (tmv.getTmv().getOfferPrice()!= null)
                append_num(tmv.getTmv().getCertifiedUsedPrice(), "Certified Used Price");
            if (has_data)
                bar_graph();
        }
    }

    private void bar_graph () {
        GraphView graphView =  (GraphView) getView().findViewById(R.id.graph);
        DataPoint[] points = new DataPoint[price_map.size()];
        int i = 0;
        for (Double price : price_map.keySet() ) {
            int label_idx = 3 * (i + 1 );
            points[i++] = new DataPoint(label_idx, price);

        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(points);
        series.setSpacing(50);
        series.setDrawValuesOnTop(false);
        series.setValuesOnTopColor(Color.RED);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String msg = price_map.get(dataPoint.getY());
                Toast.makeText(getActivity(), msg + ": $" +dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if (price_map.get(data.getY()).contains("Used"))
                    return getResources().getColor(R.color.sky_blue);
                return getResources().getColor(R.color.light_spring_green);
            }
        });


        graphView.getGridLabelRenderer().setHighlightZeroLines(false);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.Ivory));
        graphView.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);

        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.addSeries(series);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pricing, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }


    private void initUIComponents () {
        performRequest();
    }

    @Override
    public void onStart() {
        super.onStart();
        Time now = new Time();
        now.setToNow();
        JSON_HASH_KEY_NEW = getArguments().getString("vehicle_id") + "new_pricing" + now.toString();
        JSON_HASH_KEY_USED = getArguments().getString("vehicle_id") + "used_pricing" + now.toString();
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(TMV.class, JSON_HASH_KEY_NEW,
                new NewPricingRequestListener());
        spiceManager.addListenerIfPending(TMV.class, JSON_HASH_KEY_USED,
                new UsedPricingRequestListener());

    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        PricingFragment.this.getActivity().setProgressBarIndeterminate(true);
        TMVRequest newTMVRequest = new TMVRequest("new", getArguments().getString("vehicle_id"));
        TMVRequest usedTMVRequest = new TMVRequest("used", getArguments().getString("vehicle_id"));
        spiceManager.execute(newTMVRequest, JSON_HASH_KEY_NEW, DurationInMillis.ALWAYS_RETURNED,
                new NewPricingRequestListener());
        spiceManager.execute(usedTMVRequest, JSON_HASH_KEY_USED, DurationInMillis.ALWAYS_RETURNED,
                new UsedPricingRequestListener());

    }

    private void append_num (String num, String name) {
        if (num == null) return;
        PricingFragment.this.price_map.put (Double.parseDouble(num), name);
    }
}
