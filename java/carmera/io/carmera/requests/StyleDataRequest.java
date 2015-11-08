package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import carmera.io.carmera.models.StyleData;
import carmera.io.carmera.utils.Constants;

public class StyleDataRequest extends OkHttpSpiceRequest<StyleData>{
    private static final String TAG = "STYLE_DATA_REQUEST";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public static final com.squareup.okhttp.MediaType JSON =
            com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
    private GenQuery vehicleQuery;

    public StyleDataRequest(GenQuery query) {
        super(StyleData.class);
        this.vehicleQuery = query;
    }
    @Override
    public StyleData loadDataFromNetwork () throws Exception {

        try {
            String req_json = gson.toJson (vehicleQuery);
            Log.i(TAG, req_json);
            RequestBody body = RequestBody.create(JSON, req_json);
            Request request = new Request.Builder()
                    .url(Constants.VehicleInfoEndPoint)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), StyleData.class);
        } catch (IOException ie) {
            Log.i(TAG, ie.getMessage());
            return null;
        }
    }
}
