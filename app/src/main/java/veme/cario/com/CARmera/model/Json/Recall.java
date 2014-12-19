package veme.cario.com.CARmera.model.Json;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Recall {
    @JsonProperty
    String recallNumber;

    public String getRecallNumber() {
        return recallNumber;
    }

    public void setRecallNumber(String recallNumber) {
        this.recallNumber = recallNumber;
    }

    public String getComponentDescription() {
        return componentDescription;
    }

    public void setComponentDescription(String componentDescription) {
        this.componentDescription = componentDescription;
    }

    public String getManufacturedFrom() {
        return manufacturedFrom;
    }

    public void setManufacturedFrom(String manufacturedFrom) {
        this.manufacturedFrom = manufacturedFrom;
    }

    public String getManufacturedTo() {
        return manufacturedTo;
    }

    public void setManufacturedTo(String manufacturedTo) {
        this.manufacturedTo = manufacturedTo;
    }

    public String getNumberOfVehiclesAffected() {
        return numberOfVehiclesAffected;
    }

    public void setNumberOfVehiclesAffected(String numberOfVehiclesAffected) {
        this.numberOfVehiclesAffected = numberOfVehiclesAffected;
    }

    public String getDefectConsequence() {
        return defectConsequence;
    }

    public void setDefectConsequence(String defectConsequence) {
        this.defectConsequence = defectConsequence;
    }

    public String getDefectCorrectiveAction() {
        return defectCorrectiveAction;
    }

    public void setDefectCorrectiveAction(String defectCorrectiveAction) {
        this.defectCorrectiveAction = defectCorrectiveAction;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }

    public String getManufacturerRecallNumber() {
        return manufacturerRecallNumber;
    }

    public void setManufacturerRecallNumber(String manufacturerRecallNumber) {
        this.manufacturerRecallNumber = manufacturerRecallNumber;
    }

    @JsonProperty
    String componentDescription;

    @JsonProperty
    String manufacturedFrom;

    @JsonProperty
    String manufacturedTo;

    @JsonProperty
    String numberOfVehiclesAffected;

    @JsonProperty
    String defectConsequence;

    @JsonProperty
    String defectCorrectiveAction;

    @JsonProperty
    String defectDescription;

    @JsonProperty
    String manufacturerRecallNumber;

}
