package veme.cario.com.CARmera.requests;


import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.VehicleBaseInfo;

/**
 * Created by bski on 11/13/14.
 */
public class VehicleBaseInfoRequest extends SpringAndroidSpiceRequest<VehicleBaseInfo> {
    private String year;
    private String make;
    private String model;


    public VehicleBaseInfoRequest(String yr, String mk, String ml) {
        super(VehicleBaseInfo.class);
        this.year = yr;
        this.make = mk;
        this.model = ml;
    }

    @Override
    public VehicleBaseInfo loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/%s/%s/%s/styles?fmt=json&api_key=%s",
                this.make, this.model, this.year, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleBaseInfo.class);
    }
}
