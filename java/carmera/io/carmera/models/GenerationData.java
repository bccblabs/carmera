package carmera.io.carmera.models;


/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerationData {
    @JsonProperty
    private List<TrimData> trims;

    @JsonProperty
    private Snapshot snapshot;

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    public List<TrimData> getTrims() {
        return trims;
    }

    public void setTrims(List<TrimData> trims) {
        this.trims = trims;
    }


}
