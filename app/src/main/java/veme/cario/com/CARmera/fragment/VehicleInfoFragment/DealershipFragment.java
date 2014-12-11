package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Dealership;
import veme.cario.com.CARmera.model.APIModels.VehicleDealership;
import veme.cario.com.CARmera.requests.VehicleDealershipRequest;
import veme.cario.com.CARmera.util.DealershipListAdapter;

public class DealershipFragment extends Fragment {

    private static final String JSON_HASH_KEY = "base_info_json";
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private ListView dealerships_list_view;
    private DealershipListAdapter dealership_list_adapter;

    private final class DealershipRequestListener implements RequestListener<Dealership> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            DealershipFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (Dealership dealership) {
            if (DealershipFragment.this.isAdded()) {

                if (dealership_list_adapter == null) {
                    return;
                }
                dealership_list_adapter.clear();
                for (VehicleDealership vehicleDealership : dealership.getDealerships()) {
                    dealership_list_adapter.add(vehicleDealership);
                }
                dealership_list_adapter.notifyDataSetChanged();
                DealershipFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dealership, container, false);
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
        spiceManager.addListenerIfPending(Dealership.class, JSON_HASH_KEY,
                new DealershipRequestListener());
    }


    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        DealershipFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleDealershipRequest vehicleDealershipRequest = new VehicleDealershipRequest(
                                                                getArguments().getString("vehicle_make"),
                                                                getArguments().getString("user_zip"));
        spiceManager.execute(vehicleDealershipRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new DealershipRequestListener());
    }

    private void initUIComponents () {
        dealerships_list_view = (ListView) getView().findViewById(R.id.dealerships_listview);
        dealership_list_adapter = new DealershipListAdapter(getActivity());
        dealerships_list_view.setAdapter(dealership_list_adapter);
//        performRequest();
    }

}
