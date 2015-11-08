package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class ComplaintDetails {
    @JsonProperty
    public Integer count;

    @JsonProperty
    public String component;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
