package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class AverageTco {
    public float getAvgFuel() {
        return avgFuel;
    }

    public void setAvgFuel(float avgFuel) {
        this.avgFuel = avgFuel;
    }

    public float getAvgInsurance() {
        return avgInsurance;
    }

    public void setAvgInsurance(float avgInsurance) {
        this.avgInsurance = avgInsurance;
    }

    public float getAvgMaintenance() {
        return avgMaintenance;
    }

    public void setAvgMaintenance(float avgMaintenance) {
        this.avgMaintenance = avgMaintenance;
    }

    public float getAvgRepairs() {
        return avgRepairs;
    }

    public void setAvgRepairs(float avgRepairs) {
        this.avgRepairs = avgRepairs;
    }

    public float getAvgDepreciation() {
        return avgDepreciation;
    }

    public void setAvgDepreciation(float avgDepreciation) {
        this.avgDepreciation = avgDepreciation;
    }

    public AverageTco() {}

    @JsonProperty

    public float avgFuel;
    @JsonProperty
    public float avgInsurance;
    @JsonProperty
    public float avgMaintenance;
    @JsonProperty
    public float avgRepairs;
    @JsonProperty
    public float avgDepreciation;
}
