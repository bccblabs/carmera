package carmera.io.carmera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import org.parceler.Parcels;
import org.parceler.guava.collect.Collections2;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.cards.CarInfoDetailsCard;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.ResponseMessage;
import carmera.io.carmera.models.StyleData;
import carmera.io.carmera.models.listings_subdocuments.Link;
import carmera.io.carmera.models.queries.LeadQuery;
import carmera.io.carmera.predicates.CostsPredicate;
import carmera.io.carmera.predicates.RatingsPredicate;
import carmera.io.carmera.predicates.ReliabilityPredicate;
import carmera.io.carmera.requests.LeadRequest;
import carmera.io.carmera.requests.StyleDataRequest;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by bski on 11/9/15.
 */
public class ListingDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    @Bind (R.id.loading_view) View loading_view;
    @Bind (R.id.content) View content_container;
    @Bind (R.id.listing_info_card) CardView listing_info_card;
    @Bind (R.id.specs_card) CardView specs_card;
    @Bind (R.id.issues_card) CardView issues_card;
    @Bind (R.id.reviews_card) CardView reviews_card;
    @Bind (R.id.costs_card) CardView costs_card;
    @Bind (R.id.prices_card) CardView prices_card;
    @Bind (R.id.equipments_card) CardView equipments_card;
    @Bind (R.id.toolbar) Toolbar toolbar;
    @Bind (R.id.listing_details_title) TextView listing_details_title;

    private Listing listing;

    private String server_address;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private SharedPreferences sharedPreferences;

    public class ListingsBasicInfoCard extends Card {
        protected TextView yr_mk_md;
        protected TextView price;
        protected TextView mileage;
        protected TextView dealer_address, dealer_info;
        protected SliderLayout photos;
        protected List<String> stock_images;

        public ListingsBasicInfoCard (Context context, List<String> stock_photos) {
           super(context, R.layout.listings_basic_info_card_content);
            this.stock_images = stock_photos;
        }

        @Override
        public void setupInnerViewElements (ViewGroup parent, View view) {
            photos = (SliderLayout) parent.findViewById(R.id.listing_photos);
            dealer_address = (TextView) parent.findViewById(R.id.dealer_address);
            dealer_info = (TextView) parent.findViewById(R.id.dealer_name);
            if (listing.dealer.name != null ) {
                Util.setText(dealer_info, listing.dealer.name);
            }
            if (listing.dealer.getAddress() != null) {
                Util.setText(dealer_address,
                        String.format("%s\n%s, %s",
                                listing.dealer.getAddress().getStreet(),
                                listing.dealer.getAddress().getCity(),
                                listing.dealer.getAddress().getStateName()
                        )
                );
            }
            yr_mk_md = (TextView) parent.findViewById(R.id.year_make_model_trim);
            price = (TextView) parent.findViewById(R.id.price);
            mileage = (TextView) parent.findViewById(R.id.mileage);

            Util.setText(yr_mk_md,String.format("%d %s %s", listing.getYear().getYear(),
                    listing.getMake().getName(),
                    listing.getModel().getName()));
            Util.setText(price, Util.formatCurrency(listing.getMin_price()));
            NumberFormat milefmt = NumberFormat.getIntegerInstance(Locale.US);
            Util.setText(mileage, String.format("%s miles", milefmt.format(listing.getMileage())));
            photos.setPresetTransformer(SliderLayout.Transformer.Default);
            photos.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            photos.setCustomAnimation(new DescriptionAnimation());
            photos.addOnPageChangeListener(ListingDetails.this);

            if (photos != null) {
                try {
                    for (Link link : listing.getMedia().getPhotos().getLarge().getLinks()) {
                        DefaultSliderView sliderView = new DefaultSliderView(ListingDetails.this);
                        sliderView.image(link.getHref()).setOnSliderClickListener(ListingDetails.this);
                        photos.addSlider(sliderView);
                    }
                } catch (Exception e) {
                    photos.setVisibility(View.GONE);
                }
            }


        }
    }

    private final class LeadRequestListener implements RequestListener<ResponseMessage> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {

        }
        @Override
        public void onRequestSuccess (final ResponseMessage msg) {
            MaterialDialog dialog = new MaterialDialog.Builder(ListingDetails.this)
                    .content("Your request is recorded, your dealership will reach out to you shortly!")
                    .positiveText("Got It!")
                    .show();
        }
    }

    private final class StyleDataRequestListener implements RequestListener<StyleData> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(ListingDetails.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final StyleData styleData) {
            Log.i(getClass().getCanonicalName(), "styleId: " + styleData.styleId);
            ListingsBasicInfoCard basic_info_card = new ListingsBasicInfoCard(ListingDetails.this, styleData.images);
            basic_info_card.setBackgroundResourceId(R.drawable.card_bgd0);
            listing_info_card.setCard(basic_info_card);


            if (styleData.powertrain != null || styleData.dimensions != null) {
                String line0 = null;
                try {
                    if (styleData.powertrain.engine.horsepower != null && styleData.powertrain.engine.torque != null)
                        line0 = String.format("%d hp %d lb/ft\n%s", styleData.powertrain.engine.horsepower,
                                styleData.powertrain.engine.torque, String.format("%d/%d MPG", styleData.powertrain.mpg.city, styleData.powertrain.mpg.highway));
                    else
                        line0 = styleData.powertrain.engine.desc;

                } catch (NullPointerException ne) {
                    Log.w(this.getClass().getCanonicalName(), ne.getMessage());
                }

                CarInfoDetailsCard specsCard = new CarInfoDetailsCard(
                        ListingDetails.this,
                        "Specs",
                        line0,
                        R.drawable.card_bgd0);

                specsCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                        viewer.putExtra(Constants.EXTRA_MODEL_NAME, listing.getModel().getName());
                        if (styleData.powertrain != null)
                            viewer.putExtra(Constants.EXTRA_POWERTRAIN, Parcels.wrap(styleData.powertrain));
                        if (styleData.dimensions != null)
                            viewer.putExtra(Constants.EXTRA_DIMENSIONS, Parcels.wrap(styleData.dimensions));
                        startActivity(viewer);
                    }
                });
                specs_card.setCard(specsCard);
            } else {
                specs_card.setVisibility(View.GONE);
            }

            if (styleData.tags != null) {
                List<String> costs = new ArrayList<>(Collections2.filter(styleData.tags, new CostsPredicate()));
                List<String> reliability = new ArrayList<>(Collections2.filter(styleData.tags, new ReliabilityPredicate()));
                List<String> review = new ArrayList<>(Collections2.filter(styleData.tags, new RatingsPredicate()));
                if (reliability.size() > 0) {
                    CarInfoDetailsCard issuesCard = new CarInfoDetailsCard(
                            ListingDetails.this,
                            "Safety",
                            "Recalls, Ratings, Issues",
                            R.drawable.card_bgd0);
                    issuesCard.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                            viewer.putExtra(Constants.EXTRA_MODEL_NAME, listing.getModel().getName());
                            if (styleData.safety != null)
                                viewer.putExtra(Constants.EXTRA_SAFETY, Parcels.wrap(styleData.safety));
                            if (styleData.recalls != null)
                                viewer.putExtra(Constants.EXTRA_RECALLS, Parcels.wrap(styleData.recalls));
                            if (styleData.complaints != null)
                                viewer.putExtra(Constants.EXTRA_CMPL, Parcels.wrap(styleData.complaints));
                            startActivity(viewer);
                        }
                    });
                    issues_card.setCard(issuesCard);
                } else {
                    issues_card.setVisibility(View.GONE);
                }

                if (review.size() > 0) {
                    CarInfoDetailsCard reviewsCard = new CarInfoDetailsCard(
                            ListingDetails.this,
                            "Reviews",
                            "Edmund's User Reviews",
                            R.drawable.card_bgd0);
                    reviewsCard.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                            viewer.putExtra(Constants.EXTRA_MODEL_NAME, listing.getModel().getName());
                            if (styleData.reviews != null)
                                viewer.putExtra(Constants.EXTRA_REVIEW, Parcels.wrap(styleData.reviews));
                            if (styleData.ratings != null)
                                viewer.putExtra(Constants.EXTRA_RATINGS, Parcels.wrap(styleData.ratings));
                            if (styleData.improvements != null)
                                viewer.putExtra(Constants.EXTRA_IMPR, Parcels.wrap(styleData.improvements));
                            if (styleData.favorites != null)
                                viewer.putExtra(Constants.EXTRA_FAV, Parcels.wrap(styleData.favorites));
                            startActivity(viewer);
                        }
                    });
                    reviews_card.setCard(reviewsCard);
                } else {
                    reviews_card.setVisibility(View.GONE);
                }

                if (costs.size() > 0) {
                    CarInfoDetailsCard costsCard = new CarInfoDetailsCard(
                            ListingDetails.this,
                            "Running Costs",
                            String.format("Gas Costs $%.0f / year", styleData.estimated_annual_fuel_cost),
                            R.drawable.card_bgd0);
                    costsCard.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                            viewer.putExtra(Constants.EXTRA_MODEL_NAME, listing.getModel().getName());
                            if (styleData.costs != null)
                                viewer.putExtra(Constants.EXTRA_COSTS, Parcels.wrap(styleData.costs));
                            startActivity(viewer);
                        }
                    });
                    CarInfoDetailsCard pricesCard = new CarInfoDetailsCard(
                            ListingDetails.this,
                            "Prices",
                            "Edmund's True Market Value",
                            R.drawable.card_bgd0);

                    pricesCard.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                            viewer.putExtra(Constants.EXTRA_MODEL_NAME, listing.getModel().getName());
                            if (styleData.prices != null)
                                viewer.putExtra(Constants.EXTRA_PRICES, Parcels.wrap(styleData.prices));
                            startActivity(viewer);
                        }
                    });
                    costs_card.setCard(costsCard);
                    prices_card.setCard(pricesCard);
                } else {
                    costs_card.setVisibility(View.GONE);
                    prices_card.setVisibility(View.GONE);
                }
            }

            if (listing.equipment != null || listing.features != null || listing.options != null) {
                CarInfoDetailsCard equipmentCard = new CarInfoDetailsCard(
                        ListingDetails.this,
                        "Equipments",
                        "Options, Features",
                        R.drawable.card_bgd0
                );

                equipmentCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
                        viewer.putExtra(Constants.EXTRA_MODEL_NAME, listing.getModel().getName());
                        if (listing.options != null && listing.options.size() > 0)
                            viewer.putExtra(Constants.EXTRA_OPTIONS, Parcels.wrap(listing.options));
                        if (listing.features != null && listing.features.size() > 0)
                            viewer.putExtra(Constants.EXTRA_FEATURES, Parcels.wrap(listing.features));
                        if (listing.equipment != null && listing.equipment.size() > 0)
                            viewer.putExtra(Constants.EXTRA_EQUIPMENTS, Parcels.wrap(listing.equipment));
                        startActivity(viewer);
                    }
                });
                equipments_card.setCard(equipmentCard);
            } else {
                equipments_card.setVisibility(View.GONE);
            }
//
//            if (styleData.incentives != null && styleData.incentives.getCount() > 0) {
//                String incentives_desc = String.format("%d Incentives available now", styleData.incentives.getCount());
//                CarInfoDetailsCard incentives_info_card = new CarInfoDetailsCard(
//                        ListingDetails.this,
//                        "Incentives",
//                        incentives_desc,
//                        null,
//                        R.drawable.card_bgd2
//                );
//                incentives_info_card.setOnClickListener(new Card.OnCardClickListener() {
//                    @Override
//                    public void onClick(Card card, View view) {
//                        Intent viewer = new Intent(ListingDetails.this, DataViewer.class);
//                        viewer.putExtra(Constants.EXTRA_INCENTIVES, Parcels.wrap(styleData.incentives));
//                        startActivity(viewer);
//                    }
//                });
//            }

            loading_view.setVisibility(View.GONE);
            content_container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_details);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().show();
        }

        listing = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_DATA));
        listing_details_title.setText(String.format("%d %s", listing.getYear().getYear(), listing.getModel().getName()));


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        server_address = sharedPreferences.getString("pref_key_server_addr", Constants.ServerAddr).trim();
        StyleDataRequest styleDataRequest = new StyleDataRequest(listing.style.id, server_address);

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
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                Intent i = new Intent(ListingDetails.this, FavoritesActivity.class);
                startActivity(i);
                break;
            case R.id.action_settings:
                Intent pref = new Intent(ListingDetails.this, AppPreference.class);
                startActivity(pref);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @OnClick (R.id.dealer_info_btn)
    public void onDealer(View v) {
            Intent i = new Intent(ListingDetails.this, DealerViewer.class);
            i.putExtra(Constants.EXTRA_DEALERID, listing.dealer.dealerId);
            i.putExtra(Constants.EXTRA_FRANCHISEID, listing.dealer.franchiseId);
            i.putExtra(Constants.EXTRA_DEALER_NAME, listing.dealer.name);
            String car_desc = String.format("%d %s %s\n%s\nStock Id %s\nVIN %s",
                    listing.year.year, listing.make.name, listing.model.name, listing.style.name,
                    listing.stockNumber,
                    listing.vin);
            i.putExtra(Constants.EXTRA_LISTINGS_CHAT_INFO, car_desc);
            startActivity(i);
    }

    @OnClick(R.id.save_listing_btn)
    void onSave() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ListingDetails.this)
                .title(R.string.save_and_drive)
                .content(String.format("Do you want to save and schedule a test drive for %d %s %s",
                        listing.getYear().year, listing.getMake().name, listing.getModel().name))
                .positiveText(R.string.schedule_test_drive)
                .negativeText(R.string.dismiss)
                .neutralText(R.string.save_for_later)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        MaterialDialog dialog = new MaterialDialog.Builder(ListingDetails.this)
                                .content("Saved!")
                                .positiveText("Got It!")
                                .show();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        String contact_name = sharedPreferences.getString("pref_key_name", ""),
                                contact_number = sharedPreferences.getString("pref_key_phone", "").trim(),
                                contact_email = sharedPreferences.getString("pref_key_email", "").trim();

                        if (contact_name.length() < 1 && (contact_email.length() < 1 || contact_number.length() < 1)) {

                            MaterialDialog dialog = new MaterialDialog.Builder(ListingDetails.this)
                                    .content("Please set your name and email/phone to allow dealership contacts!")
                                    .positiveText("Agree")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                            Intent pref = new Intent (ListingDetails.this, AppPreference.class);
                                            ListingDetails.this.startActivity(pref)
                                        ;}
                                    })
                                    .show();
                        } else {
                            SimpleDateFormat iso_8601_fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
                            String currentDateandTime = iso_8601_fmt.format(new Date());
                            LeadQuery leadQuery = new LeadQuery();
                            leadQuery.contact_name = contact_name;
                            leadQuery.phone = contact_number;
                            leadQuery.email = contact_email;
                            leadQuery.make = listing.getMake().name;
                            leadQuery.model = listing.getModel().name;
                            leadQuery.year = listing.getYear().year.toString();
                            leadQuery.vin = listing.vin;
                            leadQuery.stock = listing.stockNumber;
                            leadQuery.date = currentDateandTime;
                            leadQuery.dealerName = listing.dealer.name;
                            leadQuery.dealerId = listing.dealer.dealerId;
                            leadQuery.franchiseId = listing.dealer.franchiseId;
                            spiceManager.execute(new LeadRequest(leadQuery, server_address), new LeadRequestListener());
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                /* save to listings */
                    }
                });
        MaterialDialog dialog = builder.build();
        dialog.show();
    }

}
