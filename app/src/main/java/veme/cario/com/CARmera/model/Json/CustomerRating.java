package veme.cario.com.CARmera.model.Json;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bski on 11/16/14.
 */
// // @JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRating {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String type;
    private String value;
}
