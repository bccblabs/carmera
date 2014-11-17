package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.Utility.Utility;
import veme.cario.com.CARmera.model.APIModels.VehicleOwnershipCost;
import veme.cario.com.CARmera.model.APIModels.VehicleReview;

/**
 * Created by bski on 11/16/14.
 */
public class VehicleReviewRequest extends SpringAndroidSpiceRequest<VehicleReview> {
    private String vehicle_id;


    public VehicleReviewRequest(String yr, String mk, String ml) {
        super(VehicleReview.class);
        this.vehicle_id = Utility.mmy_to_id(yr, mk, ml);
    }

    @Override
    public VehicleReview loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/grade/%s?fmt=json&api_key=%s",
                this.vehicle_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleReview.class);
    }
}
