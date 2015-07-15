package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Link {
    public Link() {
    }

    @JsonProperty
    public String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
