package carmera.io.carmera.models.listings_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Style {
    @JsonProperty
    public String name;
    @JsonProperty
    public Submodel submodel;
    @JsonProperty
    public String trim;

    public Style() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Submodel getSubmodel() {
        return submodel;
    }

    public void setSubmodel(Submodel submodel) {
        this.submodel = submodel;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }
}
