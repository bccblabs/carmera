package veme.cario.com.CARmera.requests;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.VehicleRecall;

public class VehicleRecallRequest extends SpringAndroidSpiceRequest<VehicleRecall> {
    private final static String TAG = "VEHICLE_RECALL_REQUEST";
    private String vehicle_id;


    public VehicleRecallRequest(String vehicle_id) {
        super(VehicleRecall.class);
        this.vehicle_id = vehicle_id;
    }

    @Override
    public VehicleRecall loadDataFromNetwork() throws Exception {
        Log.i(TAG, "vehicle_id:" + vehicle_id);
        String url = String.format("https://api.edmunds.com/v1/api/maintenance/recallrepository/findbymodelyearid?modelyearid=%s&fmt=json&api_key=%s",
                this.vehicle_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleRecall.class);
    }
}
