package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/30/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class DealerReviews {

    @JsonProperty
    public List<SalesReviews> salesReviews;

    public DealerReviews() {
    }

    public List<SalesReviews> getSalesReviews() {
        return salesReviews;
    }

    public void setSalesReviews(List<SalesReviews> salesReviews) {
        this.salesReviews = salesReviews;
    }
}
