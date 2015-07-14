package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Transmission {
    @JsonProperty
    public String transmissionType;

    @JsonProperty
    public String numberOfSpeeds;

    @JsonProperty
    public String automaticType;

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

    public Transmission() {
    }
}
