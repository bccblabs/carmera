package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import carmera.io.carmera.models.StyleData;
import carmera.io.carmera.utils.Constants;

public class StyleDataRequest extends OkHttpSpiceRequest<StyleData> {
    private static final String TAG = "STYLE_DATA_REQUEST";
    public String styleId;
    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    public StyleDataRequest(String styleId) {
        super(StyleData.class);
        this.styleId = styleId;
    }
    @Override
    public StyleData loadDataFromNetwork () throws Exception {
        try {

            Log.i(StyleData.class.getSimpleName(), this.styleId);
            String url = String.format (Constants.VehicleInfoEndPoint + "?styleId=%s",
                    this.styleId);
            Request req = new Request.Builder().url(url).get().build();
            Response response = client.newCall(req).execute();
            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), StyleData.class);
        } catch (IOException ie) {
            Log.e (TAG, ie.getMessage());
            return null;
        }
    }
}
