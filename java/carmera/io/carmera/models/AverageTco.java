package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageTco {
    @JsonProperty
    private float avgFuel;
    @JsonProperty
    private float avgInsurance;
    @JsonProperty
    private float avgMaintenance;
    @JsonProperty
    private float avgRepairs;
    @JsonProperty
    private float avgDepreciation;

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
}
