package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Listings {

    @JsonProperty
    public Integer count;

    @JsonProperty
    public List<ListingV2> listings;

    @JsonProperty
    public List<DataEntryFloat> stats;

    public Listings() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ListingV2> getListings() {
        return listings;
    }

    public void setListings(List<ListingV2> listings) {
        this.listings = listings;
    }

    public List<DataEntryFloat> getStats() {
        return stats;
    }

    public void setStats(List<DataEntryFloat> stats) {
        this.stats = stats;
    }
}
