package carmera.io.carmera.requests;

import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.VehicleQuery;

/**
 * Created by bski on 7/15/15.
 */
public class ListingsDataRequest extends OkHttpSpiceRequest<Listings>{

    private static final String TAG = "LISTING_DATA_REQUEST";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private VehicleQuery vehicleQuery;

    public ListingsDataRequest(VehicleQuery query) {
        super(Listings.class);
        this.vehicleQuery = query;
    }

    @Override
    public Listings loadDataFromNetwork () throws Exception {
        try {
            String url_string = String.format("http://52.27.126.141:3000/listings%s", this.vehicleQuery.get_query_params());
            Log.i (TAG, "Request string: " + url_string);
            Request request = new Request.Builder()
                    .url(url_string)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), Listings.class);

        } catch (IOException ie) {
            Log.i (TAG, ie.getMessage());
            return null;
        }
    }
}
