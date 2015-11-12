package carmera.io.carmera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.r0adkll.slidr.Slidr;

import org.parceler.Parcel;
import org.parceler.Parcels;
import org.parceler.guava.collect.Collections2;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.StyleData;
import carmera.io.carmera.models.listings_subdocuments.Address;
import carmera.io.carmera.models.listings_subdocuments.ContactInfo;
import carmera.io.carmera.models.listings_subdocuments.Link;
import carmera.io.carmera.predicates.CostsPredicate;
import carmera.io.carmera.predicates.PerformancePredicate;
import carmera.io.carmera.predicates.RatingsPredicate;
import carmera.io.carmera.predicates.ReliabilityPredicate;
import carmera.io.carmera.requests.StyleDataRequest;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.InMemorySpiceService;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by bski on 11/9/15.
 */
public class ListingDetails extends AppCompatActivity
                            implements  BaseSliderView.OnSliderClickListener,
                                        ViewPagerEx.OnPageChangeListener {

    @Bind (R.id.listing_info_card) CardView listing_info_card;

    @Bind (R.id.specs_card) CardView perf_card;

    @Bind (R.id.cost_card) CardView cost_card;

    @Bind (R.id.review_card) CardView review_card;

    @Bind (R.id.issues_card) CardView issues_card;

    private Listing listing;

    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);


    public class ListingsBasicInfoCard extends Card {
        protected SliderLayout photos;
        public ListingsBasicInfoCard (Context context) {
           super(context, R.layout.listings_basic_info_card_content);
        }
        @Override
        public void setupInnerViewElements (ViewGroup parent, View view) {
            photos = (SliderLayout) parent.findViewById(R.id.listing_photos);

            if (photos != null) {
                photos.setPresetTransformer(SliderLayout.Transformer.Default);
                photos.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                photos.setCustomAnimation(new DescriptionAnimation());
                photos.setDuration(4000);
                photos.addOnPageChangeListener(ListingDetails.this);
                try {
                    for (Link link : listing.getMedia().getPhotos().getLarge().getLinks()) {
                        DefaultSliderView sliderView = new DefaultSliderView(ListingDetails.this);
                        sliderView.image (link.getHref()).setOnSliderClickListener(ListingDetails.this);
                        photos.addSlider(sliderView);
                    }
                } catch (Exception e) {
                    Log.e (getClass().getCanonicalName(), e.getMessage());
                }
            }
        }

    }

    public class CarInfoCard extends Card {
        protected TagGroup tagGroup;
        protected List<String> tags;
        protected int BgdResId;
        protected String desc0, desc1;
        protected TextView desc0_tv, desc1_tv;

        public CarInfoCard (Context cxt, List<String> tags, String desc0, @Nullable String desc1, int background) {
            super (cxt, R.layout.tags_card_content);
            this.tags = tags;
            this.BgdResId = background;
            this.desc0 = desc0;
            this.desc1 = desc1;
        }
        @Override
        public void setupInnerViewElements (ViewGroup parent, View view) {
            if (view == null)
                return;
            this.setBackgroundResourceId(BgdResId);
            tagGroup = (TagGroup) view.findViewById(R.id.tags);
            tagGroup.setTags(this.tags);
            desc0_tv = (TextView) view.findViewById(R.id.desc_line0);
            desc1_tv = (TextView) view.findViewById(R.id.desc_line1);
            Util.setText(desc0_tv, this.desc0);
            Util.setText(desc1_tv, this.desc1);
        }
    }

    public class ListingBasicInfoHeader extends CardHeader {
        protected TextView price_tv, mileage_tv, header_tv;
        protected String text0, text1, title;
        public ListingBasicInfoHeader (Context cxt, @Nullable String text0, @Nullable String text1, String title) {
            super (cxt, R.layout.listing_info_header);
            this.text0 = text0;
            this.text1 = text1;
            this.title = title;
        }
        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            if (view != null) {
                header_tv = (TextView) view.findViewById(R.id.header_text);
                price_tv = (TextView) parent.findViewById(R.id.listing_price);
                mileage_tv = (TextView) parent.findViewById(R.id.listing_mileage);
                Util.setText(price_tv, this.text0);
                Util.setText(mileage_tv, this.text1);
                Util.setText(header_tv, this.title);
            }
        }
    }

    public class ListingBasicInfoExpand extends CardExpand {
        protected TextView name, address, phone, email;
        public ListingBasicInfoExpand (Context cxt) {
            super (cxt, R.layout.basic_info_expand);
        }
        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            if (view == null)
                return;
            name = (TextView) view.findViewById(R.id.dealership_name);
            address = (TextView) view.findViewById(R.id.dealership_address);
            phone = (TextView) view.findViewById(R.id.dealership_phone);
            email = (TextView) view.findViewById(R.id.dealership_email);
            Util.setText(name, listing.getDealer().getName());
            Address addr = listing.getDealer().getAddress();
            if (addr != null)
                Util.setText(address, String.format("%s, %s, %s, %s",   addr.street,
                                                                        addr.city,
                                                                        addr.stateName,
                                                                        addr.zipcode));
            else
                address.setVisibility(View.GONE);

            ContactInfo contact = listing.dealer.contactInfo;
            if (contact != null) {
                Util.setText(phone, contact.phone);
                Util.setText(email, contact.website);
            }
        }
    }

    private final class StyleDataRequestListener implements RequestListener<StyleData> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(ListingDetails.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (final StyleData styleData) {
            Log.i (getClass().getCanonicalName(), "styleId: " + styleData.styleId);
            List<String> costs = new ArrayList<>(Collections2.filter(styleData.tags, new CostsPredicate()));

            List<String> perf = new ArrayList<>(Collections2.filter(styleData.tags, new PerformancePredicate()));

            List<String> reliability =  new ArrayList<>(Collections2.filter(styleData.tags, new ReliabilityPredicate()));

            List<String> review = new ArrayList<>(Collections2.filter(styleData.tags, new RatingsPredicate()));

            if (costs.size() > 0) {
                CarInfoCard costsCard = new CarInfoCard (
                                            ListingDetails.this,
                                            costs,
                                            String.format("Estimated Annual Fuel Cost $%.0f", styleData.estimated_annual_fuel_cost),
                                            null, R.drawable.card_select1);

                ListingBasicInfoHeader hdr = new ListingBasicInfoHeader(ListingDetails.this,
                                                                        null, null,
                                                                        "Prices and Costs");
                costsCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                        if (styleData.prices != null)
                            viewer.putExtra(Constants.EXTRA_PRICES, Parcels.wrap(styleData.prices));
                        if (styleData.costs != null)
                            viewer.putExtra (Constants.EXTRA_COSTS, Parcels.wrap(styleData.costs));
                        startActivity (viewer);
                    }
                });
                costsCard.addCardHeader(hdr);
                cost_card.setCard(costsCard);
            } else {
                cost_card.setVisibility(View.GONE);
            }
            if (perf.size() > 0) {
                String line0 = null,
                        line1 = null;
                try {
                    if (styleData.powertrain.engine.horsepower != null && styleData.powertrain.engine.torque != null)
                        line0 = String.format("%s\n%d hp %d lb/ft", styleData.powertrain.engine.desc,
                                styleData.powertrain.engine.horsepower,
                                styleData.powertrain.engine.torque);
                    else
                        line0 = styleData.powertrain.engine.desc;

                    if (styleData.powertrain.mpg != null && styleData.powertrain.drivenWheels != null)
                        line1 = String.format("%s\n%s", styleData.powertrain.mpg.desc, styleData.powertrain.drivenWheels);
                } catch (NullPointerException ne) {
                    Log.w (this.getClass().getCanonicalName(), ne.getMessage());
                }

                CarInfoCard specsCard = new CarInfoCard (
                                            ListingDetails.this,
                                            perf, line0, line1,
                                            R.drawable.card_select2);

                ListingBasicInfoHeader hdr = new ListingBasicInfoHeader(ListingDetails.this,
                                                null, null,
                                                "Specs");
                specsCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                        if (styleData.powertrain != null)
                            viewer.putExtra (Constants.EXTRA_POWERTRAIN, Parcels.wrap(styleData.powertrain));
                        if (styleData.images != null)
                            viewer.putExtra(Constants.EXTRA_IMAGES, Parcels.wrap(styleData.images));
                        if (styleData.dimensions != null)
                            viewer.putExtra(Constants.EXTRA_DIMENSIONS, Parcels.wrap(styleData.dimensions));
                        startActivity (viewer);
                    }
                });

                specsCard.addCardHeader(hdr);
                perf_card.setCard(specsCard);
            } else {
                perf_card.setVisibility(View.GONE);
            }
            if (reliability.size() > 0) {
                CarInfoCard issuesCard = new CarInfoCard (
                                                ListingDetails.this,
                                                reliability,
                                                null,null,
                                                R.drawable.card_select3);

                ListingBasicInfoHeader hdr = new ListingBasicInfoHeader(ListingDetails.this,
                                                    null, null,
                                                    "Issues");

                issuesCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                        if (styleData.safety != null)
                            viewer.putExtra(Constants.EXTRA_SAFETY, Parcels.wrap(styleData.safety));
                        if (styleData.recalls != null)
                            viewer.putExtra(Constants.EXTRA_RECALLS, Parcels.wrap(styleData.recalls));
                        if (styleData.complaints != null)
                            viewer.putExtra(Constants.EXTRA_CMPL, Parcels.wrap(styleData.complaints));
                        startActivity (viewer);
                    }
                });

                issuesCard.addCardHeader(hdr);
                issues_card.setCard(issuesCard);


            } else {
                issues_card.setVisibility(View.GONE);
            }
            if (review.size() > 0) {
                CarInfoCard reviewsCard = new CarInfoCard (
                        ListingDetails.this,
                        review,
                        null, null,
                        R.drawable.card_select4);
                ListingBasicInfoHeader hdr = new ListingBasicInfoHeader(ListingDetails.this, null, null, "Reviews");
                reviewsCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                        if (styleData.reviews != null)
                            viewer.putExtra(Constants.EXTRA_REVIEW, Parcels.wrap(styleData.reviews));
                        if (styleData.ratings != null)
                            viewer.putExtra(Constants.EXTRA_RATINGS, Parcels.wrap(styleData.ratings));
                        if (styleData.improvements != null)
                            viewer.putExtra(Constants.EXTRA_IMPR, Parcels.wrap (styleData.improvements));
                        if (styleData.favorites != null)
                            viewer.putExtra(Constants.EXTRA_FAV, Parcels.wrap(styleData.favorites));
                        startActivity(viewer);
                    }
                });


                reviewsCard.addCardHeader(hdr);
                review_card.setCard(reviewsCard);
            } else {
                review_card.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_details);
        ButterKnife.bind(this);
        listing = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_DATA));
        Log.i(getClass().getCanonicalName(), new Gson().toJson(listing));



        ListingsBasicInfoCard basic_info_card = new ListingsBasicInfoCard (this);

        ListingBasicInfoHeader basic_info_header = new ListingBasicInfoHeader(this,
                                        String.format("$%.0f", listing.getPrices().getListPrice()),
                                        String.format("%d Miles", listing.getMileage()),
                                        String.format("%d %s %s", listing.getYear().getYear(),
                                                listing.getMake().getName(),
                                                listing.getModel().getName()));

        ListingBasicInfoExpand basic_info_expand = new ListingBasicInfoExpand(this);
        basic_info_header.setButtonExpandVisible(true);
        basic_info_card.addCardHeader(basic_info_header);
        basic_info_card.addCardExpand(basic_info_expand);
        basic_info_card.setBackgroundResourceId(R.drawable.card_select0);
        listing_info_card.setCard(basic_info_card);

        StyleDataRequest styleDataRequest = new StyleDataRequest(listing.style.id);
        spiceManager.execute(styleDataRequest, listing.style.id, DurationInMillis.ALWAYS_RETURNED,
                                                                 new StyleDataRequestListener());

    }

    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start(ListingDetails.this);
    }

    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

}