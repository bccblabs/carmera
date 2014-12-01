package veme.cario.com.CARmera.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleCustomerReview;
import veme.cario.com.CARmera.model.Json.CustomerReview;
import veme.cario.com.CARmera.requests.VehicleCustomerReviewRequest;
import veme.cario.com.CARmera.util.ReviewListAdapter;

public class ReviewFragment extends Fragment {

    private static final String JSON_HASH_KEY = "review_json";
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private TextView average_customer_rating_textview;
    private TextView customer_review_count_textview;
    private ListView customer_review_listview;
    private ReviewListAdapter customer_reviews_adapter;


    private final class ReviewRequestListener implements RequestListener<VehicleCustomerReview> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            ReviewFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleCustomerReview vehicleCustomerReview) {
            if (ReviewFragment.this.isAdded()) {
                average_customer_rating_textview.setText(vehicleCustomerReview.getAverageRating());
                customer_review_count_textview.setText(vehicleCustomerReview.getReviewCount());

                /* adapter code */
                if (customer_reviews_adapter == null) {
                    return;
                }
                customer_reviews_adapter.clear();
                for (CustomerReview review : vehicleCustomerReview.getReviews()) {
                    customer_reviews_adapter.add(review);
                }
                customer_reviews_adapter.notifyDataSetChanged();
                ReviewFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleCustomerReview.class, JSON_HASH_KEY,
                new ReviewRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        ReviewFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleCustomerReviewRequest vehicleBaseInfoRequest =
                new VehicleCustomerReviewRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(vehicleBaseInfoRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new ReviewRequestListener());
    }

    private void initUIComponents () {
        average_customer_rating_textview = (TextView) getView().findViewById(R.id.average_customer_rating);
        customer_review_count_textview = (TextView) getView().findViewById(R.id.customer_review_cnt);

        customer_review_listview = (ListView) getView().findViewById(R.id.customer_reviews_listview);
        customer_reviews_adapter = new ReviewListAdapter(getActivity());
        customer_review_listview.setAdapter(customer_reviews_adapter);

//        performRequest();
    }
}
