package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
/**
 * Created by bski on 1/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Predictions {
    @JsonProperty
    private List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }
}
