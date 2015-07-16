package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Photos {
    @JsonProperty
    public Photo thumbnails;
    @JsonProperty
    public Photo small;
    @JsonProperty
    public Photo large;

    public Photo getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Photo thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Photo getSmall() {
        return small;
    }

    public void setSmall(Photo small) {
        this.small = small;
    }

    public Photos() {
    }

    public Photo getLarge() {
        return large;
    }

    public void setLarge(Photo large) {
        this.large = large;
    }
}
