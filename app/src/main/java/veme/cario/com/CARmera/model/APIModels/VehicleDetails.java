package veme.cario.com.CARmera.model.APIModels;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Color;
import veme.cario.com.CARmera.model.Json.Engine;
import veme.cario.com.CARmera.model.Json.MPG;
import veme.cario.com.CARmera.model.Json.Make;
import veme.cario.com.CARmera.model.Json.Model;
import veme.cario.com.CARmera.model.Json.Option;
import veme.cario.com.CARmera.model.Json.Transmission;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDetails {
    @JsonProperty
    private Make make;

    @JsonProperty
    private Model model;

    @JsonProperty
    private Engine engine;

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @JsonProperty
    private Transmission transmission;

    @JsonProperty
    private String drivenWheels;

    @JsonProperty
    private List<Option> options;

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public MPG getMPG() {
        return MPG;
    }

    public void setMPG(MPG MPG) {
        this.MPG = MPG;
    }

    @JsonProperty
    private MPG MPG;


}
