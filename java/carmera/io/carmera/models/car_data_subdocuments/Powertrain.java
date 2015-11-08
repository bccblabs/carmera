package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Powertrain {
    @JsonProperty
    public Transmission transmission;

    @JsonProperty
    public Engine engine;

    @JsonProperty
    public String drivenWheels;

    @JsonProperty
    public Mpg mpg;

    @JsonProperty
    public Float zero_sixty;

    @JsonProperty
    public Float fuel_capacity;

    @JsonProperty
    public Float turning_diameter;

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getDrivenWheels() {
        return drivenWheels;
    }

    public void setDrivenWheels(String drivenWheels) {
        this.drivenWheels = drivenWheels;
    }

    public Mpg getMpg() {
        return mpg;
    }

    public void setMpg(Mpg mpg) {
        this.mpg = mpg;
    }

    public Float getZero_sixty() {
        return zero_sixty;
    }

    public void setZero_sixty(Float zero_sixty) {
        this.zero_sixty = zero_sixty;
    }

    public Float getFuel_capacity() {
        return fuel_capacity;
    }

    public void setFuel_capacity(Float fuel_capacity) {
        this.fuel_capacity = fuel_capacity;
    }

    public Float getTurning_diameter() {
        return turning_diameter;
    }

    public void setTurning_diameter(Float turning_diameter) {
        this.turning_diameter = turning_diameter;
    }
}
