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
    public ListingsQuery query;

    @JsonProperty
    public List<Listing> listings;

    public Listings() {
    }

    public ListingsQuery getListingsQuery() {
        return query;
    }

    public void setListingsQuery(ListingsQuery listingsQuery) {
        this.query = listingsQuery;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

}