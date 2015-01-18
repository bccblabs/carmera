package veme.cario.com.CARmera.model.Json;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 1/17/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VINStyle {
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
