package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Engine;
import veme.cario.com.CARmera.model.Json.MPG;
import veme.cario.com.CARmera.model.Json.Make;
import veme.cario.com.CARmera.model.Json.Model;
import veme.cario.com.CARmera.model.Json.Price;
import veme.cario.com.CARmera.model.Json.Transmission;

/**
 * Created by bski on 11/14/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleBaseInfo {

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String niceName;

    @JsonProperty
    private Make make;

    @JsonProperty
    private Model model;

    @JsonProperty
    private Engine engine;

    @JsonProperty
    private Transmission transmission;

    @JsonProperty
    private String drivenWheels;

    @JsonProperty
    private Price price;

    @JsonProperty
    private MPG mpg;

    @JsonProperty
    private List<String> states;



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

    public MPG getMpg() {
        return mpg;
    }

    public void setMpg(MPG mpg) {
        this.mpg = mpg;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

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

    public void setDrivenWheels(String dw) {
        this.drivenWheels = dw;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
