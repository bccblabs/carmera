package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiclePerformance {

    @JsonProperty
    private String zero_to_sixty;

    @JsonProperty
    private String zero_to_hundred;

    @JsonProperty
    private String quarter_mile;

    public String getZero_to_sixty() {
        return zero_to_sixty;
    }

    public void setZero_to_sixty(String zero_to_sixty) {
        this.zero_to_sixty = zero_to_sixty;
    }

    public String getZero_to_hundred() {
        return zero_to_hundred;
    }

    public void setZero_to_hundred(String zero_to_hundred) {
        this.zero_to_hundred = zero_to_hundred;
    }

    public String getQuarter_mile() {
        return quarter_mile;
    }

    public void setQuarter_mile(String quarter_mile) {
        this.quarter_mile = quarter_mile;
    }

}
