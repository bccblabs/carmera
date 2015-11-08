package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
public class Recalls {
    @JsonProperty
    public Integer numberOfRecalls;

    @JsonProperty
    public List<Recall> recalls;

    public Integer getNumberOfRecalls() {
        return numberOfRecalls;
    }

    public void setNumberOfRecalls(Integer numberOfRecalls) {
        this.numberOfRecalls = numberOfRecalls;
    }

    public List<Recall> getRecalls() {
        return recalls;
    }

    public void setRecalls(List<Recall> recalls) {
        this.recalls = recalls;
    }
}
