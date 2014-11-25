package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getBaseInvoice() {
        return baseInvoice;
    }

    public void setBaseInvoice(String baseInvoice) {
        this.baseInvoice = baseInvoice;
    }

    // public String getDeliveryCharges() {
    //     return deliveryCharges;
    // }

    // public void setDeliveryCharges(String deliveryCharges) {
    //     this.deliveryCharges = deliveryCharges;
    // }

    public String getUsedTmvRetail() {
        return usedTmvRetail;
    }

    public void setUsedTmvRetail(String usedTmvRetail) {
        this.usedTmvRetail = usedTmvRetail;
    }

    public String getUsedPrivateParty() {
        return usedPrivateParty;
    }

    public void setUsedPrivateParty(String usedPrivateParty) {
        this.usedPrivateParty = usedPrivateParty;
    }

    // public String getUsedTradeIn() {
    //     return usedTradeIn;
    // }

    // public void setUsedTradeIn(String usedTradeIn) {
    //     this.usedTradeIn = usedTradeIn;
    // }

    // public String getEstimateTmv() {
    //     return estimateTmv;
    // }

    // public void setEstimateTmv(String estimateTmv) {
    //     this.estimateTmv = estimateTmv;
    // }

    @JsonProperty
    private String baseMSRP;

    @JsonProperty
    private String baseInvoice;

    // @JsonProperty
    // private String deliveryCharges;

    @JsonProperty
    private String usedTmvRetail;

    @JsonProperty
    private String usedPrivateParty;

    // @JsonProperty
    // private String usedTradeIn;

    // @JsonProperty
    // private String estimateTmv;

}
