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
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.ArrayUtils;
import org.parceler.Parcels;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.DataEntryFloat;
import carmera.io.carmera.models.ListingV2;

/**
 * Created by bski on 8/1/15.
 */
public class ListingDetailsFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.scroll_container)
    ObservableScrollView container;

    @Bind(R.id.listing_car_year)
    TextView year;

    @Bind(R.id.car_info)
    TextView car_info;

    @Bind(R.id.listing_price)
    TextView price;

    @Bind(R.id.listing_mileage)
    TextView mileage;

    @Bind(R.id.trim_name)
    TextView trim_name;

    @Bind(R.id.dealer_name)
    TextView dealer_name;

    @Bind(R.id.dealer_email)
    TextView dealer_email;

    @Bind(R.id.dealer_phone)
    TextView dealer_phone;

    @Bind(R.id.price)
    HorizontalBarChart price_chart;

    @Bind(R.id.mileage)
    HorizontalBarChart mileage_chart;

    @Bind(R.id.city_mpg)
    HorizontalBarChart city_mpg_chart;

    @Bind(R.id.hwy_mpg)
    HorizontalBarChart hwy_mpg_chart;

    @Bind(R.id.hp)
    HorizontalBarChart hp_chart;

    @Bind(R.id.torque)
    HorizontalBarChart torque_chart;

    @Bind(R.id.email_container)
    View email_container;

    @Bind(R.id.phone_container)
    View phone_container;

    @Bind(R.id.name_container)
    View name_container;

    @Bind(R.id.slider)
    SliderLayout image_slider;

    public String TAG = getClass().getCanonicalName();
    private ListingV2 listing;
    private List<DataEntryFloat> stats;
    private Context context;

    public static ListingDetailsFragment newInstance() {
        return new ListingDetailsFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.listing = Parcels.unwrap(args.getParcelable(ListingsV2Fragment.EXTRA_LISTING_DATA));
        this.stats = Parcels.unwrap(args.getParcelable(ListingsV2Fragment.EXTRA_LISTINGS_STAT));
        Log.i (TAG, stats.toString());
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listing_details, container, false);
        ButterKnife.bind(this, v);

        year.setText(String.format("%d", listing.year));
        car_info.setText(String.format("%s %s",
                        listing.snapshot.make,
                        listing.snapshot.model.replace(listing.snapshot.make, "").replace("_", " ")));
        trim_name.setText(listing.snapshot.trim);
        if (listing.sellingprice != null)
            price.setText(String.format("$ %s", NumberFormat.getNumberInstance(Locale.US).format(listing.sellingprice.intValue())));
        else
            price.setVisibility(View.GONE);
        if (listing.miles != null)
            mileage.setText(String.format("%s miles", NumberFormat.getNumberInstance(Locale.US).format (listing.miles)));
        else
            mileage.setVisibility(View.GONE);

        setTextWrapper(dealer_name, listing.dealername, name_container);
        setTextWrapper(dealer_email, listing.dealeremail, email_container);
        setTextWrapper(dealer_phone, listing.dealerFax, phone_container);

        if (listing.getImagelist()!= null) {
            for (String url : listing.getImagelist()) {
                TextSliderView sliderView = new TextSliderView(getActivity());
                sliderView.description("")
                        .image(url)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(this);
                image_slider.addSlider(sliderView);
            }

            image_slider.setPresetTransformer(SliderLayout.Transformer.Stack);
            image_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            image_slider.setCustomAnimation(new DescriptionAnimation());
            image_slider.setDuration(2000);
            image_slider.addOnPageChangeListener(this);
        } else {
            image_slider.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        List<DataEntryFloat> price = new ArrayList<>();
        List<DataEntryFloat> mileage = new ArrayList<>();
        List<DataEntryFloat> hp = new ArrayList<>();
        List<DataEntryFloat> tq = new ArrayList<>();
        List<DataEntryFloat> city_mpg = new ArrayList<>();
        List<DataEntryFloat> hwy_mpg = new ArrayList<>();
        for (DataEntryFloat entry : stats) {
            if (entry.name.contains("price")) {
                entry.name = entry.name.replace("_", " ").toUpperCase();
                price.add(entry);
            }
            if (entry.name.contains("mile")) {
                entry.name = entry.name.replace("_", " ").toUpperCase();
                mileage.add(entry);
            }
            if (entry.name.contains("horsepower")) {
                entry.name = entry.name.replace("_", " ").toUpperCase();
                hp.add(entry);
            }
            if (entry.name.contains("torque")) {
                entry.name = entry.name.replace("_", " ").toUpperCase();
                tq.add(entry);
            }
            if (entry.name.contains("city")) {
                entry.name = entry.name.replace("_", " ").toUpperCase();
                city_mpg.add(entry);
            }
            if (entry.name.contains("highway")) {
                entry.name = entry.name.replace("_", " ").toUpperCase();
                hwy_mpg.add(entry);
            }
        }
        setChart(price_chart, price, "price");
        setChart(mileage_chart, mileage, "miles");
        setChart(hp_chart, hp, "horsepower");
        setChart(torque_chart, tq, "torque");
        setChart(city_mpg_chart, city_mpg, "city");
        setChart(hwy_mpg_chart, hwy_mpg, "highway");
        MaterialViewPagerHelper.registerScrollView(getActivity(), container, null);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStop() {
        image_slider.stopAutoCycle();
        super.onStop();
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

    private void setTextWrapper (TextView textview, String text, View container_view) {
        if (text != null)
            textview.setText(text);
        else
            container_view.setVisibility(View.GONE);
    }

    private List<Integer> getColors(int num_graphs) {
        List<Integer> colors = new ArrayList<>();
        for(int i = 0; i < num_graphs; i++) {
            colors.add(ColorTemplate.JOYFUL_COLORS[i%(ColorTemplate.JOYFUL_COLORS.length)]);
        }
        return colors;
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