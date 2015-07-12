package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reviews {
    @JsonProperty
    private List<String> fav_features;
    @JsonProperty
    private List<String> improvements;

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
}
