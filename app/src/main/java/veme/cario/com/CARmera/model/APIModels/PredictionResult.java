package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bski on 12/2/14.
 */
public class PredictionResult {
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty
    private String year;

    @JsonProperty
    private String make;

    @JsonProperty
    private String model;
}
