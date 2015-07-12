package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUrls {
    @JsonProperty
    private List<String> exterior;
    @JsonProperty
    private List<String> interior;
    @JsonProperty
    private List<String> engine;

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

    public List<String> getEngine() {
        return engine;
    }

    public void setEngine(List<String> engine) {
        this.engine = engine;
    }
}
