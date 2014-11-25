package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bski on 11/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transmission {

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getEquipmentType() {
        return equipmentType;
    }

    @JsonProperty
    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    @JsonProperty
    public String getTransmissionType() {
        return transmissionType;
    }

    @JsonProperty
    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    @JsonProperty
    public String getNumberofSpeeds() {
        return numberofSpeeds;
    }

    @JsonProperty
    public void setNumberofSpeeds(String numberofSpeeds) {
        this.numberofSpeeds = numberofSpeeds;
    }

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String equipmentType;

    @JsonProperty
    private String transmissionType;

    @JsonProperty
    private String numberofSpeeds;
}
