package carmera.io.carmera.requests;

import android.util.Log;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import carmera.io.carmera.models.StyleData;
import carmera.io.carmera.utils.Constants;

public class StyleDataRequest extends SpringAndroidSpiceRequest<StyleData> {
    private static final String TAG = "STYLE_DATA_REQUEST";
    public String styleId;

    public StyleDataRequest(String styleId) {
        super(StyleData.class);
        this.styleId = styleId;
    }
    @Override
    public StyleData loadDataFromNetwork () throws Exception {
        Log.i(StyleData.class.getSimpleName(), this.styleId);
        String url = String.format (Constants.VehicleInfoEndPoint + "?styleId=%s",
                this.styleId);
        return getRestTemplate().getForObject(url, StyleData.class);
    }
}
