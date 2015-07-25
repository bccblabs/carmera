package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.models.Listings;

public class ListingsRequest extends OkHttpSpiceRequest<Listings> {
    private static final String TAG = "LISTINGS_DATA_REQUEST";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public static final com.squareup.okhttp.MediaType JSON =
            com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
    private GenQuery vehicleQuery;

    public ListingsRequest(GenQuery query) {
        super(Listings.class);
        this.vehicleQuery = query;
    }

    @Override
    public Listings loadDataFromNetwork () throws Exception {

        try {
            String req_json = gson.toJson (vehicleQuery);
            Log.i(TAG, "Request Json" +  req_json);
            RequestBody body = RequestBody.create(JSON, req_json);
            Request request = new Request.Builder()
                    .url("http://52.24.11.145:3000/v2/listings")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), Listings.class);


        } catch (IOException ie) {
            Log.i(TAG, ie.getMessage());
            return null;
        }
    }
}
