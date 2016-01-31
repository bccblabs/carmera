package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import carmera.io.carmera.models.Dealers;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 1/30/16.
 */
public class DealershipsRequest extends OkHttpSpiceRequest<Dealers> {
    public String make, zipcode, radius;
    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    public DealershipsRequest (String make, String zipcode, String radius) {
        super (Dealers.class);
        this.make = make;
        this.zipcode = zipcode;
        this.radius = radius;
    }

    @Override
    public Dealers loadDataFromNetwork () throws Exception{
        String url = Constants.DealerEndPoint +
                "?zipcode=" + zipcode +
                "&radius=" + Constants.RADIUS_DEFAULT +
                "&make=" + make +
                "&pageNum=" + Constants.PAGENUM_DEFAULT +
                "&pageSize=" + Constants.PAGESIZE_DEFAULT +
                "&sortby=distance%3AASC&view=basic" +
                "&api_key=" + Constants.EDMUNDS_API_KEY;

        Log.i (getClass().getCanonicalName(), url);
        Request req = new Request.Builder().url(url)
                .get()
                .build();

        Response res = client.newCall(req).execute();
        if (!res.isSuccessful())
            throw new IOException(res.message());
        return gson.fromJson (res.body().charStream(), Dealers.class);
    }
}
