package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Listing {
    @JsonProperty
    private String dealerName;

    @JsonProperty
    private String dealerAddress;

    @JsonProperty
    private String dealerPhone;

    @JsonProperty
    private String dealerLatitude;

    @JsonProperty
    private String dealerLongitude;

    @JsonProperty
    private String make;

    @JsonProperty
    private String model;

    @JsonProperty
    private String trim;

    @JsonProperty
    private String cityMpg;

    @JsonProperty
    private String hwyMpg;

    @JsonProperty
    private String combinedMpg;

    @JsonProperty
    private String exteriorColor;

    @JsonProperty
    private String exteriorGenericColor;

    @JsonProperty
    private String transmission;

    @JsonProperty
    private String driveTrain;

    @JsonProperty
    private List<String> features;

    @JsonProperty
    private String mileage;

    @JsonProperty
    private String engineSize;

    @JsonProperty
    private List<String> photoUrlsT;

    @JsonProperty
    private String styleId;

    @JsonProperty
    private String stockNumber;

    @JsonProperty
    private String dealerOfferPrice;

    @JsonProperty
    private String tmvinventoryPrice;

    @JsonProperty
    private String msrpPrice;

}

