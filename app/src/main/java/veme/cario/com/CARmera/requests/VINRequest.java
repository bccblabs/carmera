package veme.cario.com.CARmera.requests;

import android.util.Log;

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
        Log.i(VINRequest.class.getSimpleName(), "origin :" +  vin_ );
//        this.vin = vin_.substring(0,10);
        this.vin = vin_;
    }

    @Override
    public VehicleDetails loadDataFromNetwork () throws Exception {
        Log.i(VINRequest.class.getSimpleName(), this.vin);
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/vins/%s?fmt=json&api_key=%s",
                this.vin, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleDetails.class);
    }
}
