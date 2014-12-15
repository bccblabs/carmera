package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 11/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transmission {

    @JsonProperty
    public String getTransmissionType() {
        return transmissionType;
    }

    @JsonProperty
    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    @JsonProperty
    public String getNumberOfSpeeds() {
        return numberOfSpeeds;
    }

    @JsonProperty
    public void setNumberOfSpeeds(String numberofSpeeds) {
        this.numberOfSpeeds = numberofSpeeds;
    }

    @JsonProperty
    private String transmissionType;

    @JsonProperty
    private String numberOfSpeeds;
}
