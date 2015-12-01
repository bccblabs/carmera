package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import carmera.io.carmera.models.listings_subdocuments.Dealer;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/30/15.
 */
public class DealershipRequest extends OkHttpSpiceRequest<Dealer> {
    private static final String TAG = "DEALERSHIP_REQUEST";
    public String dealerId;
    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    public DealershipRequest(String dealerId) {
        super(Dealer.class);
        this.dealerId = dealerId;
    }

    @Override
    public Dealer loadDataFromNetwork () throws Exception {
        try {

            Log.i(TAG, this.dealerId);
            String url = String.format (Constants.DealerEndPoint +
                            "%s?view=basic&api_key=%s",
                            this.dealerId,
                            Constants.EDMUNDS_API_KEY);

            Request req = new Request.Builder().url(url)
                                                .get()
                                                .build();

            Response response = client.newCall(req).execute();
            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), Dealer.class);

        } catch (IOException ie) {
            Log.e (TAG, ie.getMessage());
            return null;
        }
    }
}
