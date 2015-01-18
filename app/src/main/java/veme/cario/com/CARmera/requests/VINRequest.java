package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.VehicleDetails;

/**
 * Created by bski on 1/17/15.
 */
public class VINRequest extends SpringAndroidSpiceRequest<VehicleDetails> {

    private String vin;
    public VINRequest(String vin_) {
        super(VehicleDetails.class);
        this.vin = vin_;
    }

    @Override
    public VehicleDetails loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/vins/%s?fmt=json&api_key=%s",
                this.vin, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleDetails.class);
    }
}
