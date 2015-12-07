package carmera.io.carmera.models.queries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/19/15.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageQuery {

    @JsonProperty
    public String userId;

    @JsonProperty
    public String imageData;

    public ImageQuery() {
    }

    public ImageQuery(String userId, String imageData) {
        this.userId = userId;
        this.imageData = imageData;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
