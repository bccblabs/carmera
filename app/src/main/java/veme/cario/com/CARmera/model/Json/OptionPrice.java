package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionPrice {
    public String getBaseMSRP() {
        return baseMSRP;
    }

    public void setBaseMSRP(String baseMSRP) {
        this.baseMSRP = baseMSRP;
    }

    @JsonProperty
    private String baseMSRP;

}
