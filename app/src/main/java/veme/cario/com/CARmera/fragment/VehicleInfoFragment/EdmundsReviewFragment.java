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

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.EdmundsRating;
import veme.cario.com.CARmera.model.APIModels.EdmundsReview;
import veme.cario.com.CARmera.requests.EdmundsReviewRequest;
import veme.cario.com.CARmera.util.EdmundsRatingAdapter;

/**
 * Created by bski on 12/18/14.
 */
public class EdmundsReviewFragment extends Fragment {

    private TextView date_textview;
    private TextView grade_textview;
    private TextView summary_textview;
    private TextView no_edmunds_review;

    private ListView ratings_listview;
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
                edmundsRatingAdapter.clear();
                for (EdmundsRating edmundsRating : edmundsReview.getRatings()) {
                    edmundsRatingAdapter.add(edmundsRating);
                }
                edmundsRatingAdapter.notifyDataSetChanged();
                date_textview.setText(edmundsReview.getDate());
                grade_textview.setText(edmundsReview.getGrade());
                summary_textview.setText(edmundsReview.getSummary());
                EdmundsReviewFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
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
        View view = inflater.inflate(R.layout.fragment_edmunds_review, container, false);
        date_textview = (TextView) view.findViewById(R.id.edmunds_review_date_textview);
        grade_textview = (TextView) view.findViewById(R.id.edmunds_review_grade_textview);
        summary_textview = (TextView) view.findViewById(R.id.edmunds_review_summary_textview);
        ratings_listview = (ListView) view.findViewById(R.id.edmunds_ratings_list_view);
        no_edmunds_review = (TextView) view.findViewById(R.id.no_edmunds_review);

        ratings_listview.setEmptyView(no_edmunds_review);
        edmundsRatingAdapter = new EdmundsRatingAdapter(getActivity());
        ratings_listview.setAdapter(edmundsRatingAdapter);

        performRequest();
        return view;
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

    private void performRequest() {
        EdmundsReviewFragment.this.getActivity().setProgressBarIndeterminate(true);
        EdmundsReviewRequest edmundsReviewRequest =
                new EdmundsReviewRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(edmundsReviewRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new EdmundsReviewRequestListener());
    }

}
