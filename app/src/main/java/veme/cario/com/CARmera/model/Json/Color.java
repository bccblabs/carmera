package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 1/17/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class Color {
    @JsonProperty
    private String category;

    @JsonProperty
    private List<ColorOption> options;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ColorOption> getOptions() {
        return options;
    }

    public void setOptions(List<ColorOption> options) {
        this.options = options;
    }
}
