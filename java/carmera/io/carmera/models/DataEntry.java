package carmera.io.carmera.models;

/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class DataEntry {
    @JsonProperty
    public String name;

    public DataEntry() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty

    public String value;
}
