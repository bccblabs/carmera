package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.fmsirvent.ParallaxEverywhere.PEWImageView;
import com.fmsirvent.ParallaxEverywhere.PEWTextView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.ReviewAdapter;
import carmera.io.carmera.models.DataEntryFloat;
import carmera.io.carmera.models.Engine;
import carmera.io.carmera.models.Mpg;
import carmera.io.carmera.models.Transmission;
import carmera.io.carmera.models.TrimData;

/**
 * Created by bski on 7/13/15.
 */
public class TrimDetailsFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.fav_features_container)
    View fav_container;

    @Bind(R.id.improvements_container)
    View improvements_container;

    @Bind(R.id.scroll_container)
    ObservableScrollView container;

    @Bind(R.id.rating_chart)
    HorizontalBarChart rating_chart;
    @Bind(R.id.price_chart)
    HorizontalBarChart price_chart;
    @Bind(R.id.tco_chart)
    HorizontalBarChart tco_chart;

    @Bind(R.id.slider)
    SliderLayout image_slider;

    @Bind (R.id.gen_info)
    TextView gen_info;
    @Bind (R.id.mk_model)
    TextView mk_model;
    @Bind(R.id.trim_name)
    TextView trim_name;
    @Bind(R.id.engine_name)
    TextView engine;
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

    @Bind(R.id.fav_features_recycler)
    RecyclerView fav_features;
    @Bind(R.id.improvements_recyler)
    RecyclerView improvements;

    public String TAG = getClass().getCanonicalName();
    public static final String EXTRA_TRIM_DATA = "extra_trim_data";
    private TrimData trimData;
    private Context context;
    private ReviewAdapter good = new ReviewAdapter(), ugly = new ReviewAdapter();
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
            if (trimData.years != null)
                gen_info.setText(String.format("%d - %d", Collections.min(trimData.years), Collections.max(trimData.years)));
            mk_model.setText(trimData.make + " " + trimData.model.replace(trimData.make, "").replace("_", ""));
            String edmunds_base_url = context.getResources().getString(R.string.edmunds_baseurl);
            List<String> all_urls = new ArrayList<>();
            all_urls.addAll(trimData.images.getExterior());
            all_urls.addAll(trimData.images.getInterior());
            all_urls.addAll(trimData.images.getEngine());

            if (all_urls.size() > 0) {
                for (String url : all_urls) {
                    TextSliderView sliderView = new TextSliderView(getActivity());
                    sliderView.description("")
                            .image(edmunds_base_url + url)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(this);
                    image_slider.addSlider(sliderView);
                }
                image_slider.setPresetTransformer(SliderLayout.Transformer.Stack);
                image_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                image_slider.setCustomAnimation(new DescriptionAnimation());
                image_slider.setDuration(1000);
                image_slider.addOnPageChangeListener(this);
            } else {
                image_slider.setVisibility(View.GONE);
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

        if (trimData.reviews != null) {
            good.addAll(trimData.reviews.fav_features);
            ugly.addAll(trimData.reviews.improvements);
        }
        fav_features.setAdapter(good);
        improvements.setAdapter(ugly);
        ugly.notifyDataSetChanged();
        good.notifyDataSetChanged();
        fav_features.setHasFixedSize(true);
        improvements.setHasFixedSize(true);

        fav_features.setLayoutManager(new LinearLayoutManager(context));
        improvements.setLayoutManager(new LinearLayoutManager(context));

        if (good.getItemCount() < 1)
            fav_container.setVisibility(View.GONE);
        if (ugly.getItemCount() < 1)
            improvements_container.setVisibility(View.GONE);

        MaterialViewPagerHelper.registerScrollView(getActivity(), container, null);
    }

    private void setChart ( HorizontalBarChart chart,  List<DataEntryFloat> data_list, String name) {
        try {
            if (data_list.size() > 0) {
                List<String> x = new ArrayList<>();
                List<BarEntry> y = new ArrayList<>();

                for (int i = 0; i < data_list.size(); i++) {
                    x.add(data_list.get(i).getName());
                    y.add(new BarEntry(data_list.get(i).getValue(), i));
                }
                BarDataSet set = new BarDataSet(y, name);
                set.setBarSpacePercent(20f);
                set.setColors(getColors(x.size()));
                List<BarDataSet> yDataSets = new ArrayList<>();
                yDataSets.add(set);

                BarData data = new BarData(x, yDataSets);
                data.setValueTextSize(20f);
                data.setGroupSpace(18f);
                chart.setData(data);
                chart.setDescription(name);
                chart.animateXY(3000, 3000);

                XAxis xAxis = chart.getXAxis();
                xAxis.setDrawLabels(false);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawLabels(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                xAxis.setTextColor(context.getResources().getColor(R.color.material_blue_grey_800));
                xAxis.setTextSize(15f);
                xAxis.setDrawAxisLine(false);

                YAxis bAxis = chart.getAxisRight();
                bAxis.setDrawGridLines(false);
                bAxis.setDrawAxisLine(false);
                bAxis.setDrawLabels(false);
                bAxis.setSpaceTop(50f);

                YAxis tAxis = chart.getAxisLeft();
                tAxis.setDrawGridLines(false);
                tAxis.setDrawAxisLine(false);
                tAxis.setDrawLabels(false);
                tAxis.setSpaceTop(50f);
                chart.setDrawGridBackground(false);
                chart.setDrawBarShadow(true);
                chart.setHighlightEnabled(false);
                Legend l = chart.getLegend();
                l.setEnabled(false);

            } else {
                chart.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            chart.setVisibility(View.GONE);
            Log.e(TAG, e.getMessage());
        }
    }

    private List<Integer> getColors(int num_graphs) {
        List<Integer> colors = new ArrayList<>();
        for(int i = 0; i < num_graphs; i++) {
            colors.add(ColorTemplate.JOYFUL_COLORS[i%(ColorTemplate.JOYFUL_COLORS.length)]);
        }
        return colors;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public String getEngineName(Engine engine) {
        try {
            String compressor = engine.compressorType;
            if (compressor.toLowerCase().equals("na"))
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
            return String.format("%s speed %s", transmission.numberOfSpeeds, transmission.transmissionType);
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

    @Override
    public void onStop() {
        image_slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
