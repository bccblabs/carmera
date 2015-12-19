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
    private String server_addr;
    private OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public static final com.squareup.okhttp.MediaType JSON =
            com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
    private ListingsQuery vehicleQuery;

    public ListingsRequest(ListingsQuery query, String server_addr_) {
        super(Listings.class);
        this.vehicleQuery = query;
        this.server_addr = server_addr_;
        client.setRetryOnConnectionFailure(false);
    }

    @Override
    public Listings loadDataFromNetwork () throws Exception {
        RequestBody body = RequestBody.create(JSON, gson.toJson(vehicleQuery));
        Request request = new Request.Builder()
                .url(server_addr + Constants.ListingsEndPoint)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson (response.body().charStream(), Listings.class);
    }
}
