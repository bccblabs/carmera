package carmera.io.carmera.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import carmera.io.carmera.models.GenerationData;

/**
 * Created by bski on 7/12/15.
 */
public class GenerationDataRequest extends OkHttpSpiceRequest<GenerationData> {
    private static final String TAG = "GENERATION_DATA_REQUEST";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private String label;

    public GenerationDataRequest(String label_) {
        super(GenerationData.class);
        this.label = label_;
    }

    @Override
    public GenerationData loadDataFromNetwork () throws Exception {

        try {
            String url_string = String.format("http://52.25.18.15:3000/generations/trims/details?label=%s", this.label);
            Request request = new Request.Builder()
                    .url(url_string)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful())
                throw new IOException(response.message());
            return gson.fromJson (response.body().charStream(), GenerationData.class);

        } catch (IOException ie) {
            Log.i(TAG, ie.getMessage());
            return null;
        }
    }
}
