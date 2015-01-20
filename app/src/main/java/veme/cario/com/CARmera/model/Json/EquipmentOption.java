package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentOption {
    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private OptionPrice price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OptionPrice getPrice() {
        return price;
    }

    public void setPrice(OptionPrice price) {
        this.price = price;
    }
}
