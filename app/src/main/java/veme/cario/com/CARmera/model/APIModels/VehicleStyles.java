package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;

import veme.cario.com.CARmera.model.Json.Style;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleStyles {
    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> vehicleStyles) {
        this.styles = vehicleStyles;
    }

    @JsonProperty
    List<Style> styles;
}
