package carmera.io.carmera.models.queries;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

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

    @JsonProperty
    public String date;

    public ImageQuery() {
    }

    public ImageQuery(String userId, String imageData, String date) {
        this.userId = userId;
        this.imageData = imageData;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
