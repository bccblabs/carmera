package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleOwnershipCost;
import veme.cario.com.CARmera.requests.VehicleNewOwnershipCostRequest;

public class OwnershipCostFragment extends Fragment {

    private TextView total_cost;
    private TextView total_fuel_cost;
    private TextView total_insurance_cost;
    private TextView total_repair_cost;
    private TextView total_maintenance_cost;
    private TextView total_depr_cost;

    private static final String JSON_HASH_KEY = "ownership_cost_json";

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class OwnershipCostRequestListener implements RequestListener<VehicleOwnershipCost> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            OwnershipCostFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleOwnershipCost vehicleOwnershipCost) {
            if (OwnershipCostFragment.this.isAdded()) {
                total_cost.setText(vehicleOwnershipCost.getDeprCost().getTotal()
                                    + vehicleOwnershipCost.getFuelCost().getTotal()
                                    + vehicleOwnershipCost.getInsuranceCost().getTotal()
                                    + vehicleOwnershipCost.getRepairCost().getTotal()
                                    + vehicleOwnershipCost.getMaintenanceCost().getTotal());

                total_depr_cost.setText(vehicleOwnershipCost.getDeprCost().getTotal());
                total_fuel_cost.setText(vehicleOwnershipCost.getFuelCost().getTotal());
                total_insurance_cost.setText(vehicleOwnershipCost.getInsuranceCost().getTotal());
                total_maintenance_cost.setText(vehicleOwnershipCost.getMaintenanceCost().getTotal());
                total_repair_cost.setText(vehicleOwnershipCost.getRepairCost().getTotal());


                OwnershipCostFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
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
        return inflater.inflate(R.layout.fragment_ownership, container, false);
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
        spiceManager.addListenerIfPending(VehicleOwnershipCost.class, JSON_HASH_KEY,
                new OwnershipCostRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        OwnershipCostFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleNewOwnershipCostRequest vehicleNewOwnershipCostRequest =
                new VehicleNewOwnershipCostRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(vehicleNewOwnershipCostRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new OwnershipCostRequestListener());
    }

    private void initUIComponents () {
        total_cost = (TextView) getView().findViewById(R.id.total_cost_ownership);
        total_fuel_cost = (TextView) getView().findViewById(R.id.total_fuel_cost);
        total_insurance_cost = (TextView) getView().findViewById(R.id.total_insurance_cost);
        total_repair_cost = (TextView) getView().findViewById(R.id.total_repair_cost);
        total_maintenance_cost = (TextView) getView().findViewById(R.id.total_maintenance_cost);
        total_depr_cost = (TextView) getView().findViewById(R.id.total_depreciation_cost);


//        performRequest();
    }
}