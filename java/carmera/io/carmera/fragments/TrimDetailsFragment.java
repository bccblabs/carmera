package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.fmsirvent.ParallaxEverywhere.PEWImageView;
import com.fmsirvent.ParallaxEverywhere.PEWTextView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.DataEntryFloat;
import carmera.io.carmera.models.Engine;
import carmera.io.carmera.models.Mpg;
import carmera.io.carmera.models.Transmission;
import carmera.io.carmera.models.TrimData;

/**
 * Created by bski on 7/13/15.
 */
public class TrimDetailsFragment extends Fragment  {
    @Bind(R.id.rating_chart)
    HorizontalBarChart rating_chart;

    @Bind(R.id.price_chart)
    HorizontalBarChart price_chart;

    @Bind(R.id.tco_chart)
    HorizontalBarChart tco_chart;

    @Bind(R.id.scroll_container)
    ObservableScrollView container;


    @Bind(R.id.trim_image)
    PEWImageView trim_image;
    @Bind(R.id.engine_image)
    PEWImageView engine_image;

    @Bind(R.id.trim_name)
    PEWTextView trim_name;
    @Bind(R.id.engine_name)
    PEWTextView engine;

    @Bind(R.id.horsepower)
    TextView hp;
    @Bind(R.id.torque)
    TextView tq;
    @Bind(R.id.transmission)
    TextView transmission;
    @Bind(R.id.drivetrain)
    TextView drivetrain;
    @Bind(R.id.fuel_consumption)
    TextView fuel_consumption;

    @Bind(R.id.consumption_row)
    View consumption_row;
    @Bind(R.id.drivetrain_row)
    View drivetrain_row;
    @Bind(R.id.transmission_row)
    View transmission_row;
    @Bind(R.id.hp_row)
    View hp_row;
    @Bind(R.id.tq_row)
    View tq_row;

    public String TAG = getClass().getCanonicalName();
    public static final String EXTRA_TRIM_DATA = "extra_trim_data";
    private TrimData trimData;
    private Context context;

    public static TrimDetailsFragment newInstance() {
        return new TrimDetailsFragment();
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.trimData = Parcels.unwrap(args.getParcelable(EXTRA_TRIM_DATA));
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.trim_highlights, container, false);
        ButterKnife.bind(this, v);
        try {
            String edmunds_base_url = context.getResources().getString(R.string.edmunds_baseurl);
            if (trimData.getImages().getExterior().size() > 0) {
                Picasso.with(context).load(edmunds_base_url + trimData.getImages().getExterior().get(0))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .centerCrop()
                        .fit()
                        .into(trim_image);
            } else {
                trim_image.setVisibility(View.GONE);
            }

            if (trimData.getImages().getEngine().size() > 1)  {
                Picasso.with(context).load(edmunds_base_url + trimData.getImages().getEngine().get(0))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .centerCrop()
                        .fit()
                        .into(engine_image);

            } else {
                engine_image.setVisibility(View.GONE);
            }

            trim_name.setText(trimData.trim);
            engine.setText(getEngineName(trimData.engine));
            setTextFromTrimData(hp, hp_row, getHP(trimData.engine));
            setTextFromTrimData(tq, tq_row, getTorque(trimData.engine));
            setTextFromTrimData(fuel_consumption, consumption_row, getMPG(trimData.mpg));
            setTextFromTrimData(transmission, transmission_row, getTransmissionName(trimData.transmission));
            setTextFromTrimData(drivetrain, drivetrain_row, trimData.drivenWheels);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return v;
    }

    void setTextFromTrimData(TextView textView, View layout, String text) {
        if (text.equals(null)) {
            layout.setVisibility(View.GONE);
        } else {
            textView.setText(text);
        }
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

    public String getEngineName(Engine engine) {
        try {
            String compressor = engine.compressorType;
            if (compressor.toLowerCase().equals("NA"))
                compressor = "Naturally Aspirated";
            else if (compressor.toLowerCase().equals("supercharger"))
                compressor = "Supercharged";
            else if (compressor.toLowerCase().contains("turbo"))
                compressor = "Turbocharged";
            return String.format(" %.2f L %s %d %s",
                                                    engine.size,
                                                    engine.configuration.toUpperCase(),
                                                    engine.cylinder,
                                                    compressor);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public String getTransmissionName (Transmission transmission) {
        try {
            return String.format("%d speed %s", transmission.numberOfSpeeds, transmission.transmissionType);
        } catch (Exception e) {
            transmission_row.setVisibility(View.GONE);
            return null;
        }
    }

    public String getHP (Engine engine) {
        try {
            return engine.horsepower + " HP";
        } catch (Exception e) {
            return null;
        }
    }

    public String getTorque (Engine engine) {
        try {
            return engine.torque + " LB/FT";
        } catch (Exception e) {
            return null;
        }
    }

    public String getMPG (Mpg mpg) {
        try {
            return String.format("%d / %d MPG", mpg.city, mpg.highway);
        } catch (Exception e) {
            consumption_row.setVisibility(View.GONE);
            return null;
        }
    }
}
