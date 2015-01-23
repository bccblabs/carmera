package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.util.Utility;
import veme.cario.com.CARmera.model.APIModels.VehicleCustomerReview;

/**
 * Created by bski on 11/16/14.
 */

public class VehicleCustomerReviewRequest extends SpringAndroidSpiceRequest<VehicleCustomerReview> {
    private String vehicle_id;
    private String page_num;

    public VehicleCustomerReviewRequest(String vehicle_id_, String page_num_) {
        super(VehicleCustomerReview.class);
        this.vehicle_id = vehicle_id_;
        this.page_num = page_num_;
    }

    @Override
    public VehicleCustomerReview loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehiclereviews/v2/styles/%s?pagenum=1&pagesize=10&fmt=json&api_key=%s",
                this.vehicle_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleCustomerReview.class);
    }
}
