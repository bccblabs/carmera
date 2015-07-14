package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel

public class AverageRating {
    @JsonProperty
    public float performance;
    @JsonProperty
    public float comfort;
    @JsonProperty
    public float fun_to_drive;
    @JsonProperty
    public float interior;
    @JsonProperty
    public float exterior;
    @JsonProperty
    public float build_quality;
    @JsonProperty
    public float reliability;

    public AverageRating() {
    }

    public float getPerformance() {
        return performance;
    }

    public void setPerformance(float performance) {
        this.performance = performance;
    }

    public float getComfort() {
        return comfort;
    }

    public void setComfort(float comfort) {
        this.comfort = comfort;
    }

    public float getFun_to_drive() {
        return fun_to_drive;
    }

    public void setFun_to_drive(float fun_to_drive) {
        this.fun_to_drive = fun_to_drive;
    }

    public float getInterior() {
        return interior;
    }

    public void setInterior(float interior) {
        this.interior = interior;
    }

    public float getExterior() {
        return exterior;
    }

    public void setExterior(float exterior) {
        this.exterior = exterior;
    }

    public float getBuild_quality() {
        return build_quality;
    }

    public void setBuild_quality(float build_quality) {
        this.build_quality = build_quality;
    }

    public float getReliability() {
        return reliability;
    }

    public void setReliability(float reliability) {
        this.reliability = reliability;
    }
}
