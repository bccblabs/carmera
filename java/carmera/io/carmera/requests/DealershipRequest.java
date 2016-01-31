package carmera.io.carmera.requests;

import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/30/15.
 */
public class DealershipRequest extends OkHttpSpiceRequest<String> {
    public String dealerId;
    private final OkHttpClient client = new OkHttpClient();

    public DealershipRequest(String dealerId) {
        super(String.class);
        this.dealerId = dealerId;
    }

    @Override
    public String loadDataFromNetwork () throws Exception {
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
        return response.body().string();
    }
}
