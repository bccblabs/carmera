package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bski on 11/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Make {

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getNiceName() {
        return niceName;
    }

    @JsonProperty
    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    private String id;
    private String name;
    private String niceName;
}
