package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import veme.cario.com.CARmera.model.Json.Engine;
import veme.cario.com.CARmera.model.Json.Transmission;

/**
 * Created by bski on 12/14/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleSpecs {

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public String getDrivenWheels() {
        return drivenWheels;
    }

    public void setDrivenWheels(String drivenWheels) {
        this.drivenWheels = drivenWheels;
    }

    @JsonProperty
    private Engine engine;

    @JsonProperty
    private Transmission transmission;

    @JsonProperty
    private String drivenWheels;

}
