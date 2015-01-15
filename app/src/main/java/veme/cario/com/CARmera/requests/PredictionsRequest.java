package veme.cario.com.CARmera.requests;

import android.graphics.Bitmap;
import android.util.Base64;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.sinch.android.rtc.internal.service.pubnub.http.UriEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

import veme.cario.com.CARmera.model.APIModels.Predictions;
import veme.cario.com.CARmera.model.APIModels.Vehicle;

/**
 * Created by bski on 12/1/14.
 */
public class PredictionsRequest extends SpringAndroidSpiceRequest<Predictions> {

    private static final String TAG = "VEHICLE_REQUEST";
    private String image_url;

    public PredictionsRequest(String image_url_) {
        super(Predictions.class);
        this.image_url = image_url_;
    }

    @Override
    public Predictions loadDataFromNetwork () throws Exception {
        HttpHeaders hdrs = new HttpHeaders();
        String api_string = URLEncoder.encode("I4MGGdRNHSV17xvWXOveGBRCVsqSZV0vEeeq9UMpTw91KK1hkj", "UTF-8");
        String encoded_string = Base64.encodeToString(api_string.getBytes("UTF-8"), Base64.DEFAULT);
        hdrs.add("Authorization", "Basic " + encoded_string);
        String url = String.format ("https://www.metamind.io/vision/classify?classifier_id=869&image_url=%s",
                image_url);
        ResponseEntity<Predictions> entity= getRestTemplate().exchange(
                                                            url,
                                                            HttpMethod.POST,
                                                            new HttpEntity<Object>(hdrs),
                                                            Predictions.class);
        return entity.getBody();
    }
}
