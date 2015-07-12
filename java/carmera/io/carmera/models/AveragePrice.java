package carmera.io.carmera.models;

/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AveragePrice {
    @JsonProperty
    private float avgBaseMSRP;
    @JsonProperty
    private float avgBaseInvoice;
    @JsonProperty
    private float avgUsedTmvRetail;
    @JsonProperty
    private float avgUsedPrivateParty;
    @JsonProperty
    private float avgTradeIn;

    public float getAvgBaseMSRP() {
        return avgBaseMSRP;
    }

    public void setAvgBaseMSRP(float avgBaseMSRP) {
        this.avgBaseMSRP = avgBaseMSRP;
    }

    public float getAvgBaseInvoice() {
        return avgBaseInvoice;
    }

    public void setAvgBaseInvoice(float avgBaseInvoice) {
        this.avgBaseInvoice = avgBaseInvoice;
    }

    public float getAvgUsedTmvRetail() {
        return avgUsedTmvRetail;
    }

    public void setAvgUsedTmvRetail(float avgUsedTmvRetail) {
        this.avgUsedTmvRetail = avgUsedTmvRetail;
    }

    public float getAvgUsedPrivateParty() {
        return avgUsedPrivateParty;
    }

    public void setAvgUsedPrivateParty(float avgUsedPrivateParty) {
        this.avgUsedPrivateParty = avgUsedPrivateParty;
    }

    public float getAvgTradeIn() {
        return avgTradeIn;
    }

    public void setAvgTradeIn(float avgTradeIn) {
        this.avgTradeIn = avgTradeIn;
    }
}
