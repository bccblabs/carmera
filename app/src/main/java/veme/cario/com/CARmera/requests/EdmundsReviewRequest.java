package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.EdmundsReview;

public class EdmundsReviewRequest extends SpringAndroidSpiceRequest<EdmundsReview> {
    private String vehicle_id;


    public EdmundsReviewRequest(String id) {
        super(EdmundsReview.class);
        this.vehicle_id = id;
    }

    @Override
    public EdmundsReview loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/grade/%s?fmt=json&api_key=%s\n",
                this.vehicle_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, EdmundsReview.class);
    }
}
