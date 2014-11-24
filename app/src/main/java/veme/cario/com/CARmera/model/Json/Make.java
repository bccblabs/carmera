package veme.cario.com.CARmera.model.Json;

//// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bski on 11/15/14.
 */
//// // @JsonIgnoreProperties(ignoreUnknown = true)
public class Make {
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

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    private String id;
    private String name;
    private String niceName;
}
