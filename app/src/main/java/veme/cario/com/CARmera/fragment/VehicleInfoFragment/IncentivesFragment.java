package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.IncentiveRebate;
import veme.cario.com.CARmera.model.Json.Incentive;
import veme.cario.com.CARmera.requests.IncentivesRequest;
import veme.cario.com.CARmera.util.IncentiveListAdapter;

/**
 * Created by bski on 12/18/14.
 */
public class IncentivesFragment extends Fragment {
    private static String JSON_HASH_KEY;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private IncentiveListAdapter incentiveListAdapter;
    private ListView incentives_listview;
    private TextView incentives_cnt;
    private TextView no_incentives_view;
    private View incentives_loading_view;

    private final class CustomerReviewRequestListener implements RequestListener<IncentiveRebate> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
        }

        @Override
        public void onRequestSuccess (IncentiveRebate incentiveRebate) {
            if (IncentivesFragment.this.isAdded()) {

                incentives_listview.setAlpha(0f);

                String reviewCount = incentiveRebate.getIncentiveHolder().size() + " incentives found";
                incentives_cnt.setText(reviewCount);

                for (Incentive incentive : incentiveRebate.getIncentiveHolder()) {
                    incentiveListAdapter.add (incentive);
                }
                incentiveListAdapter.notifyDataSetChanged();

                incentives_listview.setVisibility(View.VISIBLE);
                incentives_loading_view.setVisibility(View.GONE);
                incentives_listview.animate().alpha(1f);

                IncentivesFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_incentives, container, false);
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
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "incentives";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(IncentiveRebate.class, JSON_HASH_KEY,
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
        IncentivesFragment.this.getActivity().setProgressBarIndeterminate(true);
        IncentivesRequest incentivesRequest =
                new IncentivesRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(incentivesRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new CustomerReviewRequestListener());
    }

    private void initUIComponents () {
        incentives_listview = (ListView) getView().findViewById(R.id.incentives_listview);
        incentives_cnt = (TextView) getView().findViewById(R.id.incentives_cnt);
        incentives_loading_view = getView().findViewById(R.id.incentives_loading_view);

        incentives_listview.setVisibility(View.GONE);
        incentives_loading_view.setAlpha(0f);
        incentives_loading_view.setVisibility(View.VISIBLE);
        incentives_loading_view.animate().alpha(1f);

        incentiveListAdapter = new IncentiveListAdapter(getActivity());
        incentives_listview.setAdapter(incentiveListAdapter);
        no_incentives_view = (TextView) getView().findViewById(R.id.no_incentives_view);
        incentives_listview.setEmptyView(no_incentives_view);
        performRequest();
    }

}
