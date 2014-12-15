package veme.cario.com.CARmera.requests;


import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.VehicleSpecs;

/**
 * Created by bski on 11/13/14.
 */
public class VehicleSpecsRequest extends SpringAndroidSpiceRequest<VehicleSpecs> {
    private static final String TAG = "VEHICLE_SPECS_REQUEST";
    private String trim_id;
    public VehicleSpecsRequest(String id) {
        super(VehicleSpecs.class);
        this.trim_id = id;
    }
    @Override
    public VehicleSpecs loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/styles/%s?view=full&fmt=json&api_key=%s",
                this.trim_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleSpecs.class);
    }
}
