package veme.cario.com.CARmera.model.Json;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties (ignoreUnknown = true)
public class PricingDetail {
    @JsonProperty
    private String baseMSRP;

    @JsonProperty
    private String baseInvoice;

    @JsonProperty
    private String tmv;

    @JsonProperty
    private String usedTmvRetail;

    @JsonProperty
    private String usedPrivateParty;

    @JsonProperty
    private String usedTradeIn;

    @JsonProperty
    private String estimateTmv;

    public String getEstimateTmv() {
        return estimateTmv;
    }

    public void setEstimateTmv(String estimateTmv) {
        this.estimateTmv = estimateTmv;
    }

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

    public String getTmv() {
        return tmv;
    }

    public void setTmv(String tmv) {
        this.tmv = tmv;
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
}
