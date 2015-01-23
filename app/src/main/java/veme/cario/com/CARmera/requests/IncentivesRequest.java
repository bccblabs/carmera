package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.IncentiveRebate;

/**
 * Created by bski on 1/14/15.
 */
public class IncentivesRequest extends SpringAndroidSpiceRequest<IncentiveRebate> {
    private String vehicle_id;

    public IncentivesRequest(String vehicle_id_) {
        super(IncentiveRebate.class);
        this.vehicle_id = vehicle_id_;
    }

    @Override
    public IncentiveRebate loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/v1/api/incentive/incentiverepository/findincentivesbystyleid?styleid=%s&fmt=json&api_key=%s",
                this.vehicle_id,
                CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, IncentiveRebate.class);
    }

}
