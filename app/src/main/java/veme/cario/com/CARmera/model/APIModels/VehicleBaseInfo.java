package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import veme.cario.com.CARmera.model.Json.MPG;
import veme.cario.com.CARmera.model.Json.Make;
import veme.cario.com.CARmera.model.Json.Model;
import veme.cario.com.CARmera.model.Json.Price;
import veme.cario.com.CARmera.model.Json.Year;

/**
 * Created by bski on 11/14/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleBaseInfo {

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @JsonProperty
    private Price price;

    @JsonProperty
    private MPG MPG;

    @JsonProperty
    private String trim;

    @JsonProperty
    private Make make;

    @JsonProperty
    private Model model;

    @JsonProperty
    private Year year;

    public MPG getMPG() {
        return MPG;
    }

    public void setMPG(MPG mpg) {
        this.MPG = mpg;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
