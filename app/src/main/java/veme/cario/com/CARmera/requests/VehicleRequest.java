package veme.cario.com.CARmera.requests;

import android.graphics.Bitmap;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import veme.cario.com.CARmera.model.APIModels.Vehicle;

/**
 * Created by bski on 12/1/14.
 */
public class VehicleRequest extends SpringAndroidSpiceRequest<Vehicle> {

    private Bitmap bitmap;
    private static final String TAG = "VEHICLE_REQUEST";
    private static final String link = "http://localhost:3000/classify";

    public VehicleRequest(Bitmap bitmap) {
        super(Vehicle.class);
        this.bitmap = bitmap;
    }

    @Override
    public Vehicle loadDataFromNetwork () throws Exception {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("imageFile", bitmap);
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate.postForObject(link, parts, Vehicle.class);
    }
}
