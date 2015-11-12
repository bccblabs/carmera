package carmera.io.carmera.fragments.main_fragments;

//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.octo.android.robospice.SpiceManager;
//import com.octo.android.robospice.persistence.DurationInMillis;
//import com.octo.android.robospice.persistence.exception.SpiceException;
//import com.octo.android.robospice.request.listener.RequestListener;
//
//import org.parceler.Parcels;
//
//import java.util.ArrayList;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import carmera.io.carmera.R;
//import carmera.io.carmera.models.StyleData;
//import carmera.io.carmera.models.Listing;
//import carmera.io.carmera.predicates.CostsPredicate;
//import carmera.io.carmera.predicates.PerformancePredicate;
//import carmera.io.carmera.predicates.RatingsPredicate;
//import carmera.io.carmera.predicates.ReliabilityPredicate;
//import carmera.io.carmera.requests.StyleDataRequest;
//import carmera.io.carmera.utils.Constants;
//import carmera.io.carmera.utils.InMemorySpiceService;
//import carmera.io.carmera.utils.Util;
//import me.gujun.android.taggroup.TagGroup;

/**
 * Created by bski on 8/1/15.
 */
//public class ListingDetailsFragment extends Fragment {
//
//    public final String TAG = getClass().getCanonicalName();
//
//    @Bind(R.id.listing_photos) SlideShowView image_slider;
//
//    @Bind(R.id.listing_car_info) TextView car_info;
//
//    @Bind(R.id.listing_price) TextView price;
//
//    @Bind(R.id.listing_mileage) TextView mileage;
//
//    @Bind (R.id.car_performance) TextView performance;
//
//    @Bind (R.id.car_mpg) TextView mpg;
//
//    @Bind (R.id.performance_tags) TagGroup performance_tags;
//
//    @Bind (R.id.reliability_tags) TagGroup reliability_tags;
//
//    @Bind (R.id.costs_tags) TagGroup costs_tags;
//
//    @Bind (R.id.ratings_tags) TagGroup ratings_tags;
//
//    private Listing listing;
//
//    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);
//
//    private final class StyleDataRequestListener implements RequestListener<StyleData> {
//        @Override
//        public void onRequestFailure (SpiceException spiceException) {
//            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        @Override
//        public void onRequestSuccess (StyleData styleData) {
//            Util.setText(performance, styleData.powertrain.engine.desc);
//            Util.setText(mpg, styleData.powertrain.mpg.desc);
//            ratings_tags.setTags(new ArrayList<>(Collections2.filter(styleData.tags, new CostsPredicate())));
//            performance_tags.setTags(new ArrayList<>(Collections2.filter(styleData.tags, new PerformancePredicate())));
//            costs_tags.setTags(new ArrayList<>(Collections2.filter(styleData.tags, new ReliabilityPredicate())));
//            reliability_tags.setTags(new ArrayList<>(Collections2.filter(styleData.tags, new RatingsPredicate())));
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        this.listing = Parcels.unwrap(args.getParcelable(Constants.EXTRA_LISTING_DATA));
//        setRetainInstance(true);
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.listing_details, container, false);
//        ButterKnife.bind(this, v);
//
//        Util.setText(car_info, String.format("%d %s %s", listing.getYear().getYear(),
//                listing.getMake().getName(),
//                listing.getModel().getName()));
//        Util.setText(price, String.format("$%.0f", listing.getPrices().getListPrice()));
//
//        Util.setText(mileage, String.format("%d Miles", listing.getMileage()));
//        car_info.setText(String.format("%s %s",
//                        listing.snapshot.make,
//                        listing.snapshot.model.replace(listing.snapshot.make, "").replace("_", " ")));
//        trim_name.setText(listing.snapshot.trim);
//        if (listing.sellingprice != null)
//            price.setText(String.format("$ %s", NumberFormat.getNumberInstance(Locale.US).format(listing.sellingprice.intValue())));
//        else
//            price.setVisibility(View.GONE);
//        if (listing.miles != null)
//            mileage.setText(String.format("%s miles", NumberFormat.getNumberInstance(Locale.US).format (listing.miles)));
//        else
//            mileage.setVisibility(View.GONE);
//
//        setTextWrapper(dealer_name, listing.dealername, name_container);
//        setTextWrapper(dealer_email, listing.dealeremail, email_container);
//        setTextWrapper(dealer_phone, listing.dealerFax, phone_container);
//
//        if (listing.getImagelist()!= null) {
//            for (String url : listing.getImagelist()) {
//                TextSliderView sliderView = new TextSliderView(getActivity());
//                if (url.length() > 0) {
//                    sliderView.description("")
//                            .image(url)
//                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
//                            .setOnSliderClickListener(this);
//                    image_slider.addSlider(sliderView);
//                }
//            }
//
//            image_slider.setPresetTransformer(SliderLayout.Transformer.Stack);
//            image_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//            image_slider.setCustomAnimation(new DescriptionAnimation());
//            image_slider.setDuration(2000);
//            image_slider.addOnPageChangeListener(this);
//        } else {
//            image_slider.setVisibility(View.GONE);
//        }
//        return v;
//    }
//
//    @Override
//    public void onViewCreated (View v, Bundle savedInstanceState) {
//        super.onViewCreated(v, savedInstanceState);
//        StyleDataRequest styleDataRequest = new StyleDataRequest(listing.style.id);
//        spiceManager.execute(styleDataRequest, listing.style.id, DurationInMillis.ALWAYS_RETURNED, new StyleDataRequestListener());
//    }
//
//    @Override
//    public void onStart () {
//        super.onStart();
//        spiceManager.start(getActivity());
//    }
//
//    @Override
//    public void onStop () {
//        if (spiceManager.isStarted()) {
//            spiceManager.shouldStop();
//        }
//        super.onStop();
//    }



//}