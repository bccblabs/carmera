package carmera.io.carmera.models;


/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class GenerationData {
    public GenerationData() {}

    public List<TrimData> getTrims() {
        return trims;
    }

    public void setTrims(List<TrimData> trims) {
        this.trims = trims;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    @JsonProperty
    public List<TrimData> trims;

    @JsonProperty
    public Snapshot snapshot;
}
