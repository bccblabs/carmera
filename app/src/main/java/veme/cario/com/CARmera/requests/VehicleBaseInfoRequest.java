package veme.cario.com.CARmera.requests;


import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.VehicleBaseInfo;

/**
 * Created by bski on 11/13/14.
 */
public class VehicleBaseInfoRequest extends SpringAndroidSpiceRequest<VehicleBaseInfo> {
    private static final String TAG = "VEHICLE_INFO_REQUEST";
    private String trim_id;
    public VehicleBaseInfoRequest(String id) {
        super(VehicleBaseInfo.class);
        this.trim_id = id;
    }
    @Override
    public VehicleBaseInfo loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/styles/%s?view=basic&fmt=json&api_key=%s",
                this.trim_id, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleBaseInfo.class);
    }
}
