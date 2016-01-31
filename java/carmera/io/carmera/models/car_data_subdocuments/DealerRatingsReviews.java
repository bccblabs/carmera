package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 1/30/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class DealerRatingsReviews {
    @JsonProperty
    public DealerRatings sales;
    @JsonProperty
    public DealerRatings service;

    public DealerRatingsReviews() {
    }

    public DealerRatings getSales() {
        return sales;
    }

    public void setSales(DealerRatings sales) {
        this.sales = sales;
    }

    public DealerRatings getService() {
        return service;
    }

    public void setService(DealerRatings service) {
        this.service = service;
    }
}
