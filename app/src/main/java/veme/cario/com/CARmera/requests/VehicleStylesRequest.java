package veme.cario.com.CARmera.requests;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.VehicleStyles;

public class VehicleStylesRequest extends SpringAndroidSpiceRequest<VehicleStyles> {
    private String year;
    private String make;
    private String model;

    private static final String TAG = "VEHICLE_STYLES_REQUEST";
    public VehicleStylesRequest(String yr, String mk, String ml) {
        super(VehicleStyles.class);
        this.year = yr;
        this.make = mk;
        this.model = ml;
    }

    @Override
    public VehicleStyles loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/%s/%s/%s/styles?state=new&view=full&fmt=json&api_key=%s",
                this.make, this.model, this.year, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleStyles.class);
    }
}