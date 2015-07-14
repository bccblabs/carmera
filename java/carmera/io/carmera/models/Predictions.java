package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;
/**
 * Created by bski on 1/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Predictions {
    @JsonProperty
    public List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public Predictions() {
    }
}
