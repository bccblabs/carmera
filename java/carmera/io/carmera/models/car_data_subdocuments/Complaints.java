package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/7/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Complaints {
    @JsonProperty
    public Integer count;

    @JsonProperty
    public List<ComplaintDetails> details;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ComplaintDetails> getDetails() {
        return details;
    }

    public void setDetails(List<ComplaintDetails> details) {
        this.details = details;
    }
}
