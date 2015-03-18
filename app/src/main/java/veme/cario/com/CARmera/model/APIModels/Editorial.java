package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Editorial {
    @JsonProperty
    private String con;

    @JsonProperty
    private String body;

    @JsonProperty
    private String interior;

    @JsonProperty
    private String driving;

    @JsonProperty
    private String whatsNew;

    @JsonProperty
    private String powertrain;

    @JsonProperty
    private String safety;

    @JsonProperty
    private String introduction;

}
