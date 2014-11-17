package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

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

    public String getUsedTradeIn() {
        return usedTradeIn;
    }

    public void setUsedTradeIn(String usedTradeIn) {
        this.usedTradeIn = usedTradeIn;
    }

    public String getEstimateTmv() {
        return estimateTmv;
    }

    public void setEstimateTmv(String estimateTmv) {
        this.estimateTmv = estimateTmv;
    }

    private String baseMSRP;
    private String baseInvoice;
    private String deliveryCharges;
    private String usedTmvRetail;
    private String usedPrivateParty;
    private String usedTradeIn;
    private String estimateTmv;
}
