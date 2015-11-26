package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/24/15.
 */

@Parcel
@JsonIgnoreProperties (ignoreUnknown = true)
public class Incentives {
    @JsonProperty
    public List<Incentive> incentiveHolder;

    @JsonProperty
    public Integer count;


    public Incentives() {
    }

    public List<Incentive> getIncentiveHolder() {
        return incentiveHolder;
    }

    public void setIncentiveHolder(List<Incentive> incentiveHolder) {
        this.incentiveHolder = incentiveHolder;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
