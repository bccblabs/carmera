package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transmission {
    @JsonProperty
    private String transmissionType;

    @JsonProperty
    private String numberOfSpeeds;

    @JsonProperty
    private String automaticType;

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getNumberOfSpeeds() {
        return numberOfSpeeds;
    }

    public void setNumberOfSpeeds(String numberOfSpeeds) {
        this.numberOfSpeeds = numberOfSpeeds;
    }

    public String getAutomaticType() {
        return automaticType;
    }

    public void setAutomaticType(String automaticType) {
        this.automaticType = automaticType;
    }
}
