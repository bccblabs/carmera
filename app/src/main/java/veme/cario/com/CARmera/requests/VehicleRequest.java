package veme.cario.com.CARmera.requests;

import android.graphics.Bitmap;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import veme.cario.com.CARmera.model.APIModels.Vehicle;

/**
 * Created by bski on 12/1/14.
 */
public class VehicleRequest extends SpringAndroidSpiceRequest<Vehicle> {

    private Bitmap bitmap;

    public VehicleRequest(Bitmap bitmap) {
        super(Vehicle.class);
        /* do a async load? */
        this.bitmap = bitmap;
    }

    @Override
    public Vehicle loadDataFromNetwork () throws Exception {
        String url = String.format ("https://api.edmunds.com/api/vehicle/v2/%s/%s/%s/styles?fmt=json&api_key=%s");
        return getRestTemplate().getForObject(url, Vehicle.class);
    }
}
