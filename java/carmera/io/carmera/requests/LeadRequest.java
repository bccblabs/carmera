package carmera.io.carmera.requests;
import android.util.Log;
import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.octo.android.robospice.retry.RetryPolicy;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.util.concurrent.TimeUnit;
import carmera.io.carmera.models.ResponseMessage;
import carmera.io.carmera.models.queries.LeadQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/17/15.
 */
public class LeadRequest extends OkHttpSpiceRequest<ResponseMessage> {

    private static final String TAG = "LISTINGS_REQUEST";
    private String server_addr;
    private LeadQuery leadQuery;
    private OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public static final com.squareup.okhttp.MediaType JSON =
            com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");

    public LeadRequest (LeadQuery leadQuery, String server_addr_) {
        super(ResponseMessage.class);
        this.leadQuery = leadQuery;
        this.server_addr = server_addr_;
        RetryPolicy retryPolicy = getRetryPolicy();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);
        client.setRetryOnConnectionFailure(false);
        client.setWriteTimeout(120, TimeUnit.SECONDS);
        Log.i(TAG, "" + retryPolicy.getDelayBeforeRetry() + " " + retryPolicy.getRetryCount());
    }


    @Override
    public ResponseMessage loadDataFromNetwork () throws Exception {
        String req_json = gson.toJson(leadQuery);
        Log.i(TAG, "Request Json" + req_json);
        RequestBody body = RequestBody.create(JSON, req_json);
        Request request = new Request.Builder()
                .url(server_addr + Constants.LeadEndPoint)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Log.i (TAG,response.toString());
        return gson.fromJson (response.body().charStream(), ResponseMessage.class);
    }

}
