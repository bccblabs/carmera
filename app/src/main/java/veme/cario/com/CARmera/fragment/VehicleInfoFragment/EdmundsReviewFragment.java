package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.EdmundsRating;
import veme.cario.com.CARmera.model.APIModels.EdmundsReview;
import veme.cario.com.CARmera.model.Json.EdmundsSubRating;
import veme.cario.com.CARmera.requests.EdmundsReviewRequest;
import veme.cario.com.CARmera.util.AnimatedExpandableListView;
import veme.cario.com.CARmera.util.EdmundsRatingAdapter;
import veme.cario.com.CARmera.util.EquipmentListAdapter;

/**
 * Created by bski on 12/18/14.
 */
public class EdmundsReviewFragment extends Fragment {

    private TextView date_textview;
    private TextView grade_textview;
    private TextView summary_textview;
    private TextView no_edmunds_review;
    private AnimatedExpandableListView edmundsRatingListview;

    private List<EdmundsRatingAdapter.RatingItem> ratingitems = new ArrayList<EdmundsRatingAdapter.RatingItem>();
    private EdmundsRatingAdapter edmundsRatingAdapter;

    private static String JSON_HASH_KEY;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class EdmundsReviewRequestListener implements RequestListener<EdmundsReview> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            EdmundsReviewFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (EdmundsReview edmundsReview) {
            if (EdmundsReviewFragment.this.isAdded()) {
                date_textview.setText(edmundsReview.getDate());
                grade_textview.setText(edmundsReview.getGrade());
                summary_textview.setText(edmundsReview.getSummary());

                for (EdmundsRating edmundsRating : edmundsReview.getRatings()) {
                    EdmundsRatingAdapter.RatingItem ratingItem = new EdmundsRatingAdapter.RatingItem();
                    ratingItem.grade = edmundsRating.getGrade();
                    ratingItem.score = edmundsRating.getScore();
                    ratingItem.summary = edmundsRating.getSummary();
                    ratingItem.title = edmundsRating.getTitle();
                    for (EdmundsSubRating subRating : edmundsRating.getSubRatings()) {
                        EdmundsRatingAdapter.SubRatingItem subRatingItem = new EdmundsRatingAdapter.SubRatingItem();
                        subRatingItem.grade = subRating.getGrade();
                        subRatingItem.score = subRating.getScore();
                        subRatingItem.summary = subRating.getSummary();
                        subRatingItem.title = subRating.getTitle();
                        ratingItem.subRatings.add (subRatingItem);
                    }
                    ratingitems.add (ratingItem);
                }
                edmundsRatingAdapter.setData(ratingitems);
                edmundsRatingAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* set fragment to be retained across Activity recreation */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edmunds_review, container, false);
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
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "edmunds_reviews";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(EdmundsReview.class, JSON_HASH_KEY,
                new EdmundsReviewRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void initUIComponents () {

        date_textview = (TextView) getView().findViewById(R.id.edmunds_review_date_textview);
        grade_textview = (TextView) getView().findViewById(R.id.edmunds_review_grade_textview);
        summary_textview = (TextView) getView().findViewById(R.id.edmunds_review_summary_textview);

        no_edmunds_review = (TextView) getView().findViewById(R.id.transmission_type_textview);
        edmundsRatingListview = (AnimatedExpandableListView) getView().findViewById(R.id.edmunds_review_listview);
        edmundsRatingAdapter = new EdmundsRatingAdapter(getActivity());
        edmundsRatingAdapter.setData(ratingitems);
        edmundsRatingListview.setAdapter(edmundsRatingAdapter);
        edmundsRatingListview.setEmptyView(no_edmunds_review);
        performRequest();
    }

    private void performRequest() {
        EdmundsReviewFragment.this.getActivity().setProgressBarIndeterminate(true);
        EdmundsReviewRequest edmundsReviewRequest =
                new EdmundsReviewRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(edmundsReviewRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new EdmundsReviewRequestListener());
    }

}
