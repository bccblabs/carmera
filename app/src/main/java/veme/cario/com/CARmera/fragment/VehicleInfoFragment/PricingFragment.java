package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

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
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.TMV;
import veme.cario.com.CARmera.requests.TMVRequest;

/**
 * Created by bski on 12/18/14.
 */
public class PricingFragment extends Fragment {
    private static String JSON_HASH_KEY_NEW;
    private String TAG = PricingFragment.class.getSimpleName();
    private static String JSON_HASH_KEY_USED;
    private XYPlot plot;
    private LineAndPointFormatter new_price_format, used_price_format;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private List<Double> new_price = new ArrayList<Double>(), used_price = new ArrayList<Double>();
    private XYSeries new_car_series, used_car_series;

    private final class NewPricingRequestListener implements RequestListener<TMV> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            PricingFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(TMV tmv) {
            if (tmv.getTmv().getNationalBasePrice()!= null) {
                append_num (tmv.getTmv().getNationalBasePrice().getBaseMSRP(), true);
                append_num(tmv.getTmv().getNationalBasePrice().getBaseInvoice(), true);
                append_num(tmv.getTmv().getNationalBasePrice().getTmv(), true);
            }
            if (tmv.getTmv().getTotalWithOptions()!= null) {
                append_num(tmv.getTmv().getTotalWithOptions().getBaseMSRP(), true);
                append_num(tmv.getTmv().getTotalWithOptions().getBaseInvoice(), true);
                append_num(tmv.getTmv().getTotalWithOptions().getTmv(), true);
            }
            if (tmv.getTmv().getOfferPrice()!= null)
                append_num(tmv.getTmv().getOfferPrice(), true);
            Log.i(TAG, " " + new_price.size());

            new_price_format = new LineAndPointFormatter();
            new_price_format.setPointLabelFormatter(new PointLabelFormatter());
            new_price_format.configure(getActivity(), R.xml.line_point_formatter_with_plf1);
            new_car_series = new SimpleXYSeries(
                    new_price,          // SimpleXYSeries takes a List so turn our array into a List
                    SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                    "New Price");
            plot.addSeries(new_car_series, new_price_format);
            plot.redraw();
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
            if (tmv.getTmv().getNationalBasePrice()!= null) {
                append_num(tmv.getTmv().getNationalBasePrice().getUsedTmvRetail(), false);
                append_num(tmv.getTmv().getNationalBasePrice().getUsedPrivateParty(), false);
                append_num(tmv.getTmv().getNationalBasePrice().getUsedTradeIn(), false);
            }
            if (tmv.getTmv().getTotalWithOptions()!= null) {
                append_num(tmv.getTmv().getTotalWithOptions().getUsedTmvRetail(), false);
                append_num(tmv.getTmv().getTotalWithOptions().getUsedPrivateParty(), false);
                append_num(tmv.getTmv().getTotalWithOptions().getUsedTradeIn(), false);
            }
            if (tmv.getTmv().getOfferPrice()!= null)
                append_num(tmv.getTmv().getCertifiedUsedPrice(), false);
            Number[] series1Numbers = {41000, 42000, 43000, 52000, 55000, 56000};

            used_price_format = new LineAndPointFormatter();
            used_price_format.setPointLabelFormatter(new PointLabelFormatter());
            used_price_format.configure(getActivity(), R.xml.line_point_formatter_with_plf2);

            used_car_series = new SimpleXYSeries(
                    Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                    SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                    "Used Price");
            Log.i(TAG, " " + used_price.size());
            plot.addSeries(used_car_series, used_price_format);
            plot.redraw();
        }
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

        plot = (XYPlot) getView().findViewById(R.id.pricing_plot);
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
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

    private void append_num (String num, boolean is_new) {
        if (num == null)
            return;
        if (is_new)
            PricingFragment.this.new_price.add(Double.parseDouble(num));
        else
            PricingFragment.this.used_price.add(Double.parseDouble(num));
    }
}
