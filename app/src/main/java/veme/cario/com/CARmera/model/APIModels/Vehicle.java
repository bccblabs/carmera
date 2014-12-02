package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
