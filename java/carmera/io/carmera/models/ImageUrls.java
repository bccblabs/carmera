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
public class ImageUrls {
    @JsonProperty
    public List<String> exterior;
    @JsonProperty
    public List<String> interior;

    public List<String> getEngine() {
        return engine;
    }

    public void setEngine(List<String> engine) {
        this.engine = engine;
    }

    public List<String> getExterior() {
        return exterior;
    }

    public void setExterior(List<String> exterior) {
        this.exterior = exterior;
    }

    public List<String> getInterior() {
        return interior;
    }

    public void setInterior(List<String> interior) {
        this.interior = interior;
    }

    @JsonProperty

    public List<String> engine;

    public ImageUrls() {
    }
}
