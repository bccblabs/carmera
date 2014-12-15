package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    public String getBaseMSRP() {
        return baseMSRP;
    }

    public void setBaseMSRP(String baseMSRP) {
        this.baseMSRP = baseMSRP;
    }

    public String getUsedTmvRetail() {
        return usedTmvRetail;
    }

    public void setUsedTmvRetail(String usedTmvRetail) {
        this.usedTmvRetail = usedTmvRetail;
    }

    @JsonProperty
    private String baseMSRP;

    @JsonProperty
    private String usedTmvRetail;

}
