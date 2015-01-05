package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleCustomerReview;
import veme.cario.com.CARmera.model.Json.CustomerReview;
import veme.cario.com.CARmera.requests.VehicleCustomerReviewRequest;
import veme.cario.com.CARmera.util.AnimatedExpandableListView;
import veme.cario.com.CARmera.util.ReviewListAdapter;

public class CustomerReviewFragment extends Fragment {

    private static String JSON_HASH_KEY;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private TextView review_info;
    private TextView no_customer_review;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AnimatedExpandableListView customer_review_listview;
    private ReviewListAdapter review_list_adapter;
    private List<ReviewListAdapter.ReviewItem> customer_reviews_items = new ArrayList<ReviewListAdapter.ReviewItem>();
    private View customer_review_loading_view;
    private int page_num = 0;


    private final class CustomerReviewRequestListener implements RequestListener<VehicleCustomerReview> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
        }

        @Override
        public void onRequestSuccess (VehicleCustomerReview vehicleCustomerReview) {
            if (CustomerReviewFragment.this.isAdded()) {

                customer_review_listview.setAlpha(0f);

                String reviewCount = vehicleCustomerReview.getReviewCount();
                review_info.setText(vehicleCustomerReview.getAverageRating() + "/5 from "
                        + reviewCount + " reviewers." );

                for (CustomerReview customerReview : vehicleCustomerReview.getReviews()) {
                    ReviewListAdapter.ReviewChildItem childItem = new ReviewListAdapter.ReviewChildItem();
                    childItem.text = customerReview.getText();
                    childItem.favorite_features = customerReview.getFavoriteFeatures();
                    childItem.suggested_improvements = customerReview.getSuggestedImprovements();

                    ReviewListAdapter.ReviewItem reviewItem = new ReviewListAdapter.ReviewItem();
                    reviewItem.name = customerReview.getAuthor().getAuthorName();
                    reviewItem.rating = customerReview.getAverageRating();
                    reviewItem.title = customerReview.getTitle();

                    reviewItem.items.add(childItem);
                    customer_reviews_items.add(reviewItem);
                }
                review_list_adapter.setData(customer_reviews_items);
                review_list_adapter.notifyDataSetChanged();

                customer_review_listview.setVisibility(View.VISIBLE);
                customer_review_loading_view.setVisibility(View.GONE);
                customer_review_listview.animate().alpha(1f);

                CustomerReviewFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page_num = 0;
        /* set fragment to be retained across Activity recreation */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_reviews, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    @Override
    public void onStart() {
        super.onStart();
        Time now = new Time();
        now.setToNow();
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "customer_reviews";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleCustomerReview.class, JSON_HASH_KEY,
                new CustomerReviewRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        CustomerReviewFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleCustomerReviewRequest vehicleCustomerReviewRequest =
                new VehicleCustomerReviewRequest(getArguments().getString("vehicle_id"), Integer.toString(++page_num));
        spiceManager.execute(vehicleCustomerReviewRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new CustomerReviewRequestListener());
    }

    private void initUIComponents () {
        review_info = (TextView) getView().findViewById(R.id.review_info_text_view);

        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.customer_review_swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
                performRequest();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        customer_review_listview = (AnimatedExpandableListView) getView().findViewById(R.id.customer_review_listview);
        customer_review_listview.setVisibility(View.GONE);
        customer_review_loading_view = getView().findViewById(R.id.customer_review_progress);

        customer_review_loading_view.setAlpha(0f);
        customer_review_loading_view.setVisibility(View.VISIBLE);
        customer_review_loading_view.animate().alpha(1f);


        review_list_adapter = new ReviewListAdapter(getActivity());
        review_list_adapter.setData(customer_reviews_items);

        customer_review_listview.setAdapter(review_list_adapter);



        no_customer_review = (TextView) getView().findViewById(R.id.no_customer_review);
        customer_review_listview.setEmptyView(no_customer_review);
        customer_review_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (customer_review_listview.isGroupExpanded(groupPosition)) {
                    customer_review_listview.collapseGroupWithAnimation(groupPosition);
                } else {
                    customer_review_listview.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });

        customer_review_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (customer_review_listview.isGroupExpanded(groupPosition)) {
                    customer_review_listview.collapseGroupWithAnimation(groupPosition);
                } else {
                    customer_review_listview.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });
        performRequest();
    }
}
