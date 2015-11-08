package carmera.io.carmera.models.listings_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import carmera.io.carmera.models.listings_subdocuments.Photos;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Media {
    public Media() {
    }

    @JsonProperty
    public Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
