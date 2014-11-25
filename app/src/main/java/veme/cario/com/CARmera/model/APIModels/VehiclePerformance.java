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

    public String getZeroToSixty() {
        return zero_to_sixty;
    }

    public void setZeroToSixty(String zero_to_sixty) {
        this.zero_to_sixty = zero_to_sixty;
    }

    public String getZeroToHundred() {
        return zero_to_hundred;
    }

    public void setZeroToHundred(String zero_to_hundred) {
        this.zero_to_hundred = zero_to_hundred;
    }

    public String getQuarterMile() {
        return quarter_mile;
    }

    public void setQuarterMile(String quarter_mile) {
        this.quarter_mile = quarter_mile;
    }

}
