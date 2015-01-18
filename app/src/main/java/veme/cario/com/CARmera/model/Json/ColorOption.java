package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 1/17/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class ColorOption {
    @JsonProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
