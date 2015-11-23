package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.octo.android.robospice.retry.RetryPolicy;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;

public class ListingsRequest extends OkHttpSpiceRequest<Listings> {
    private static final String TAG = "LISTINGS_REQUEST";
    private String server_addr;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public static final com.squareup.okhttp.MediaType JSON =
            com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
    private ListingsQuery vehicleQuery;

    public ListingsRequest(ListingsQuery query, String server_addr_) {
        super(Listings.class);
        this.vehicleQuery = query;
        this.server_addr = server_addr_;
        RetryPolicy retryPolicy = getRetryPolicy();
        Log.i (TAG, "" + retryPolicy.getDelayBeforeRetry() + " " + retryPolicy.getRetryCount());
    }

    @Override
    public Listings loadDataFromNetwork () throws Exception {
//        try {
            String req_json = gson.toJson(vehicleQuery);
            Log.i(TAG, "Request Json" +  req_json);
            RequestBody body = RequestBody.create(JSON, req_json);
            Request request = new Request.Builder()
                    .url(server_addr + Constants.ListingsEndPoint)
                    .post(body)
                    .build();
            client.setConnectTimeout(60, TimeUnit.SECONDS);
            client.setReadTimeout(60, TimeUnit.SECONDS);
            Response response = client.newCall(request).execute();
            Log.i (TAG,response.toString());
            return gson.fromJson (response.body().charStream(), Listings.class);

//        } catch (Exception ie) {
//            return null;
//        }
    }
}
