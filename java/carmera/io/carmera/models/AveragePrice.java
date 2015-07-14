package carmera.io.carmera.models;

/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class AveragePrice {
    @JsonProperty
    public float avgBaseMSRP;
    @JsonProperty
    public float avgBaseInvoice;
    @JsonProperty
    public float avgUsedTmvRetail;
    @JsonProperty
    public float avgUsedpublicParty;
    @JsonProperty
    public float avgTradeIn;

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

    public float getAvgUsedpublicParty() {
        return avgUsedpublicParty;
    }

    public void setAvgUsedpublicParty(float avgUsedpublicParty) {
        this.avgUsedpublicParty = avgUsedpublicParty;
    }

    public float getAvgTradeIn() {
        return avgTradeIn;
    }

    public void setAvgTradeIn(float avgTradeIn) {
        this.avgTradeIn = avgTradeIn;
    }

    public AveragePrice() {
    }
}
