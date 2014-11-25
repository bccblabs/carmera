package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.util.Utility;
import veme.cario.com.CARmera.model.APIModels.VehicleOwnershipCost;

/**
 * Created by bski on 11/16/14.
 */
public class VehicleNewOwnershipCostRequest extends SpringAndroidSpiceRequest<VehicleOwnershipCost> {
    private String vehicle_id;


    public VehicleNewOwnershipCostRequest(String vehicle_id) {
        super(VehicleOwnershipCost.class);
        // TODO: use a guard to handle the receiving of vehicle_id
        this.vehicle_id = vehicle_id;
    }

    @Override
    public VehicleOwnershipCost loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/tco/v1/details/allnewtcobystyleidzipandstate/%s/%s/%s?fmt=json&api_key=%s",
                this.vehicle_id, Utility.get_zip(), Utility.get_state(), CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleOwnershipCost.class);
    }
}
