package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Make {

    public Make() {
    }

    @JsonProperty
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
