package carmera.io.carmera.models.listings_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Photo {
    @JsonProperty
    public long count;
    @JsonProperty
    public List<Link> links;

    public Photo() {
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
