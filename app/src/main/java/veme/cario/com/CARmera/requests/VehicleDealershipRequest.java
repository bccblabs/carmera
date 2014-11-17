package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.VehicleDealershipList;

/**
 * Created by bski on 11/16/14.
 */
public class VehicleDealershipRequest extends SpringAndroidSpiceRequest<VehicleDealershipList> {
    private String make;
    private String zip;

    public VehicleDealershipRequest (String make, String zip) {
        super (VehicleDealershipList.class);
        this.make = make;
        this.zip = zip;
    }

    @Override
    public VehicleDealershipList loadDataFromNetwork () throws Exception {
        String url = String.format("http://api.edmunds.com/api/dealer/v2/dealers/?zipcode=%s&radius=100&make=%s&state=new&pageNum=1&pageSize=10&view=basic&api_key=%s",
                this.zip, this.make, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, VehicleDealershipList.class);
    }
}
