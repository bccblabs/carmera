package veme.cario.com.CARmera.model.Json;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 1/17/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMVDetails {
    @JsonProperty
    private PricingDetail nationalBasePrice;

    @JsonProperty
    private PricingDetail totalWithOptions;

    @JsonProperty
    private String offerPrice;

    @JsonProperty
    private String certifiedUsedPrice;

    public PricingDetail getNationalBasePrice() {
        return nationalBasePrice;
    }

    public void setNationalBasePrice(PricingDetail pricingDetail) {
        this.nationalBasePrice = pricingDetail;
    }

    public PricingDetail getTotalWithOptions() {
        return totalWithOptions;
    }

    public void setTotalWithOptions(PricingDetail totalWithOptions) {
        this.totalWithOptions = totalWithOptions;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getCertifiedUsedPrice() {
        return certifiedUsedPrice;
    }

    public void setCertifiedUsedPrice(String certifiedUsedPrice) {
        this.certifiedUsedPrice = certifiedUsedPrice;
    }
}
