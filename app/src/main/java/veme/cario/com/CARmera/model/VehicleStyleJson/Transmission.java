package veme.cario.com.CARmera.model.VehicleStyleJson;

/**
 * Created by bski on 11/15/14.
 */
public class Transmission {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getNumberofSpeeds() {
        return numberofSpeeds;
    }

    public void setNumberofSpeeds(String numberofSpeeds) {
        this.numberofSpeeds = numberofSpeeds;
    }

    private String id;
    private String name;
    private String equipmentType;
    private String transmissionType;
    private String numberofSpeeds;
}
