package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 1/30/16.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Dealers {
    @JsonProperty
    public List<DealerInfo> dealers;
    @JsonProperty
    public Integer dealersCount;

    public Dealers() {
    }

    public List<DealerInfo> getDealers() {
        return dealers;
    }

    public void setDealers(List<DealerInfo> dealers) {
        this.dealers = dealers;
    }

    public Integer getDealersCount() {
        return dealersCount;
    }

    public void setDealersCount(Integer dealersCount) {
        this.dealersCount = dealersCount;
    }
}
