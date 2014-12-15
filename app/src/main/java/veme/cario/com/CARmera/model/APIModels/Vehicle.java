package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 12/1/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    public PredictionResult getResult() {
        return result;
    }

    public void setResult(PredictionResult result) {
        this.result = result;
    }

    @JsonProperty
    private PredictionResult result;
}
