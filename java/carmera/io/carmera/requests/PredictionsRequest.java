package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;

import carmera.io.carmera.models.Predictions;

public class PredictionsRequest extends OkHttpSpiceRequest<Predictions> {

    private static final String TAG = "PREDICTION_REQUEST";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public static final com.squareup.okhttp.MediaType JSON =
            com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");

    private String image_url;

    public PredictionsRequest(String image_url_) {
        super(Predictions.class);
        this.image_url = image_url_;
    }

    @Override
    public Predictions loadDataFromNetwork () throws Exception {

        try {
            String req_json = String.format ( "{\"classifier_id\": 31249, \"image_url\": \"%s\"}", image_url) ;
            Log.i (TAG, req_json);
            RequestBody body = RequestBody.create(JSON, req_json);
            Request request = new Request.Builder()
                    .url("https://www.metamind.io/vision/classify")
                    .header("Authentication", "Basic XWeYJ4qJEzLVMAPcnPoS0JDh4jJemKSJMtYalryzngfrl5Lpeb")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), Predictions.class);


        } catch (IOException ie) {
            Log.i(TAG, ie.getMessage());
            return null;
        }
    }
}
