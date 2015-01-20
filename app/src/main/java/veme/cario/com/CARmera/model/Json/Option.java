package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 1/17/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {
    @JsonProperty
    private String category;

    public List<EquipmentOption> getOptions() {
        return options;
    }

    public void setOptions(List<EquipmentOption> options) {
        this.options = options;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty

    private List<EquipmentOption> options;
}
