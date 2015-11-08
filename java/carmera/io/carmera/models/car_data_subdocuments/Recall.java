package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Recall {

    @JsonProperty
    public String recallNumber;

    @JsonProperty
    public Integer numberOfVehiclesAffected;

    @JsonProperty
    public String ownerNotificationDate;

    @JsonProperty
    public String component;

    @JsonProperty
    public String subcomponent;

    @JsonProperty
    public String manufacturedFrom;

    @JsonProperty
    public String manufacturedTo;

    @JsonProperty
    public List<String>  defectConsequence;

    @JsonProperty
    public List<String> defectDescription;


    public String getRecallNumber() {
        return recallNumber;
    }

    public void setRecallNumber(String recallNumber) {
        this.recallNumber = recallNumber;
    }

    public Integer getNumberOfVehiclesAffected() {
        return numberOfVehiclesAffected;
    }

    public void setNumberOfVehiclesAffected(Integer numberOfVehiclesAffected) {
        this.numberOfVehiclesAffected = numberOfVehiclesAffected;
    }

    public String getOwnerNotificationDate() {
        return ownerNotificationDate;
    }

    public void setOwnerNotificationDate(String ownerNotificationDate) {
        this.ownerNotificationDate = ownerNotificationDate;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSubcomponent() {
        return subcomponent;
    }

    public void setSubcomponent(String subcomponent) {
        this.subcomponent = subcomponent;
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

    public List<String> getDefectConsequence() {
        return defectConsequence;
    }

    public void setDefectConsequence(List<String> defectConsequence) {
        this.defectConsequence = defectConsequence;
    }

    public List<String> getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(List<String> defectDescription) {
        this.defectDescription = defectDescription;
    }
}
