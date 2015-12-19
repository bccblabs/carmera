package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import carmera.io.carmera.models.car_data_subdocuments.DealerReviews;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/30/15.
 */
public class DealerReviewsRequest extends OkHttpSpiceRequest<DealerReviews> {

    public String dealerId;
    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();



    public DealerReviewsRequest(String dealerId) {
        super(DealerReviews.class);
        this.dealerId = dealerId;
    }


    @Override
    public DealerReviews loadDataFromNetwork () throws Exception {
        String url = Constants.DealerReviewsEndPoint +
                        "?dealerid="+ this.dealerId +
                        "&limit=0%2C100&fmt=json" +
                        "&api_key=" + Constants.EDMUNDS_API_KEY;

        Request req = new Request.Builder().url(url)
                .get()
                .build();

        Response response = client.newCall(req).execute();
        if (!response.isSuccessful())
            throw new IOException(response.message());
        return gson.fromJson (response.body().charStream(), DealerReviews.class);
    }
}
