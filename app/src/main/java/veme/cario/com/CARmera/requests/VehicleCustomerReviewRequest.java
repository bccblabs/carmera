package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.Utility.Utility;
import veme.cario.com.CARmera.model.APIModels.VehicleCustomerReview;

/**
 * Created by bski on 11/16/14.
 */

public class VehicleCustomerReviewRequest extends SpringAndroidSpiceRequest<VehicleCustomerReview> {
    private String vehicle_id;


    public VehicleCustomerReviewRequest(String yr, String mk, String ml) {
        super(VehicleCustomerReview.class);
        this.vehicle_id = Utility.mmy_to_id(yr, mk, ml);
    }

    @Override
    public VehicleCustomerReview loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehiclereviews/v2/styles/%s?pagenum=1&pagesize=5&fmt=json&api_key=%s",
                this.vehicle_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleCustomerReview.class);
    }
}
