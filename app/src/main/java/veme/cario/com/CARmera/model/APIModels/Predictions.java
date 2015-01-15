package veme.cario.com.CARmera.model.APIModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Prediction;

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
