package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Reviews {
    @JsonProperty
    public List<String> fav_features;
    @JsonProperty
    public List<String> improvements;

    public List<String> getFav_features() {
        return fav_features;
    }

    public void setFav_features(List<String> fav_features) {
        this.fav_features = fav_features;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }

    public Reviews() {
    }
}
