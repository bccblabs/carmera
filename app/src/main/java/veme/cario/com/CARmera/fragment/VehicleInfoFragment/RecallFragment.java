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

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleRecall;
import veme.cario.com.CARmera.model.Json.Recall;
import veme.cario.com.CARmera.requests.VehicleRecallRequest;
import veme.cario.com.CARmera.util.VehicleRecallAdapter;

/**
 * Created by bski on 12/19/14.
 */
public class RecallFragment extends Fragment {
    private static final String TAG = "RECALL_FRAGMENT";
    private static String JSON_HASH_KEY;

    private ListView recall_list_view;
    private TextView no_recall_view;
    private VehicleRecallAdapter recallAdapter;
    private View recallsLoadingView;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class VehicleRecallRequestListener implements RequestListener<VehicleRecall> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            recall_list_view.setAlpha(0f);
            recall_list_view.setVisibility(View.VISIBLE);
            recall_list_view.animate().alpha(1f);
        }

        @Override
        public void onRequestSuccess (VehicleRecall vehicleRecall) {

            if (RecallFragment.this.isAdded()) {
                recall_list_view.setAlpha(0f);

                for (Recall recall : vehicleRecall.getRecallHolder())
                    recallAdapter.add(recall);
                recallAdapter.notifyDataSetChanged();
                recall_list_view.setVisibility(View.VISIBLE);
                recallsLoadingView.setVisibility(View.GONE);
                recall_list_view.animate().alpha(1f);
                RecallFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vehicle_recall, container, false);
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
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "vehicle_recall";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleRecall.class, JSON_HASH_KEY,
                new VehicleRecallRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void initUIComponents () {
        recall_list_view = (ListView) getView().findViewById(R.id.vehicle_recall_listview);


        recallsLoadingView = getView().findViewById(R.id.recall_progress);

        recall_list_view.setVisibility(View.GONE);
        recallsLoadingView.setAlpha(0f);
        recallsLoadingView.setVisibility(View.VISIBLE);
        recallsLoadingView.animate().alpha(1f);

        recallAdapter = new VehicleRecallAdapter(getActivity());
        recall_list_view.setAdapter(recallAdapter);

        no_recall_view = (TextView) getView().findViewById(R.id.no_recalls_textview);
        recall_list_view.setEmptyView(no_recall_view);
        performRequest();
    }

    private void performRequest() {
        RecallFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleRecallRequest vehicleRecallRequest = new VehicleRecallRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(vehicleRecallRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VehicleRecallRequestListener());
    }
}
