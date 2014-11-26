package veme.cario.com.CARmera.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.model.APIModels.Dealership;

public class VehicleDealershipRequest extends SpringAndroidSpiceRequest<Dealership> {
    private String make;
    private String zip;

    public VehicleDealershipRequest (String make, String zip) {
        super (Dealership.class);
        this.make = make;
        this.zip = zip;
    }

    @Override
    public Dealership loadDataFromNetwork () throws Exception {
        String url = String.format("http://api.edmunds.com/api/dealer/v2/dealers/?zipcode=%s&radius=100&make=%s&state=new&pageNum=1&pageSize=10&view=basic&api_key=%s",
                this.zip, this.make, CARmeraApp.edmunds_app_key);
        return getRestTemplate().getForObject(url, Dealership.class);
    }
}
