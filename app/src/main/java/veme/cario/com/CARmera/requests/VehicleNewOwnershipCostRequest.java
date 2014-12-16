package veme.cario.com.CARmera.requests;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.util.Utility;
import veme.cario.com.CARmera.model.APIModels.VehicleOwnershipCost;

/**
 * Created by bski on 11/16/14.
 */
public class VehicleNewOwnershipCostRequest extends SpringAndroidSpiceRequest<VehicleOwnershipCost> {
    private final static String TAG = "VEHICLE_OWNERSHIP_REQUEST";
    private String vehicle_id;


    public VehicleNewOwnershipCostRequest(String vehicle_id) {
        super(VehicleOwnershipCost.class);
        this.vehicle_id = vehicle_id;
    }

    @Override
    public VehicleOwnershipCost loadDataFromNetwork () throws Exception {
        Log.i(TAG,"vehicle_id:" + vehicle_id);
        String url = String.format ("https://api.edmunds.com/api/tco/v1/details/allusedtcobystyleidzipandstate/%s/%s/%s?fmt=json&api_key=%s",
                this.vehicle_id, "90019", "CA", CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleOwnershipCost.class);
    }
}
