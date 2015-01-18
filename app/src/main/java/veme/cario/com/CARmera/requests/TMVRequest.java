package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.TMV;
import veme.cario.com.CARmera.model.APIModels.VehicleCustomerReview;

/**
 * Created by bski on 1/17/15.
 */
public class TMVRequest extends SpringAndroidSpiceRequest<TMV> {

    private String state;
    private String style_id;
    public TMVRequest(String state_, String style_id_) {
        super(TMV.class);
        this.state = state_;
        this.style_id = style_id_;
    }

    @Override
    public TMV loadDataFromNetwork () throws Exception {
        String url;
        if (this.state.equals("new")) {
            url = String.format("https://api.edmunds.com/v1/api/tmv/tmvservice/calculatenewtmv?styleid=%s&zip=90019&fmt=json&api_key=%s",
                    this.style_id, CARmeraApp.edmunds_app_key);
        } else {
            url = String.format("https://api.edmunds.com/v1/api/tmv/tmvservice/calculateusedtmv?styleid=%s&condition=Outstanding&mileage=10000&zip=90404&fmt=json&api_key=%s",
                    this.style_id, CARmeraApp.edmunds_app_key);
        }
        return getRestTemplate().getForObject(url, TMV.class);
    }

}
