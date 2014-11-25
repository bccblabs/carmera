package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.util.Utility;
import veme.cario.com.CARmera.model.APIModels.VehicleOwnershipCost;

/**
 * Created by bski on 11/16/14.
 */
public class VehicleUsedOwnershipCostRequest extends SpringAndroidSpiceRequest<VehicleOwnershipCost> {
    private String vehicle_id;


    public VehicleUsedOwnershipCostRequest(String yr, String mk, String ml) {
        super(VehicleOwnershipCost.class);
        this.vehicle_id = Utility.mmy_to_id(yr, mk, ml);
    }

    @Override
    public VehicleOwnershipCost loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/tco/v1/details/allusedtcobystyleidzipandstate/%s/%s/%s?fmt=json&api_key=%s",
                this.vehicle_id, Utility.get_zip(), Utility.get_state(), CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleOwnershipCost.class);
    }
}
