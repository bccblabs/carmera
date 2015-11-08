package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Prices {
    @JsonProperty
    public Integer usedTmvRetail;

    @JsonProperty
    public Integer usedPrivateParty;

    @JsonProperty
    public Integer usedTradeIn;

    @JsonProperty
    public Integer baseMSRP;

    @JsonProperty
    public Integer baseInvoice;

    public Integer getUsedTmvRetail() {
        return usedTmvRetail;
    }

    public void setUsedTmvRetail(Integer usedTmvRetail) {
        this.usedTmvRetail = usedTmvRetail;
    }

    public Integer getUsedPrivateParty() {
        return usedPrivateParty;
    }

    public void setUsedPrivateParty(Integer usedPrivateParty) {
        this.usedPrivateParty = usedPrivateParty;
    }

    public Integer getUsedTradeIn() {
        return usedTradeIn;
    }

    public void setUsedTradeIn(Integer usedTradeIn) {
        this.usedTradeIn = usedTradeIn;
    }

    public Integer getBaseMSRP() {
        return baseMSRP;
    }

    public void setBaseMSRP(Integer baseMSRP) {
        this.baseMSRP = baseMSRP;
    }

    public Integer getBaseInvoice() {
        return baseInvoice;
    }

    public void setBaseInvoice(Integer baseInvoice) {
        this.baseInvoice = baseInvoice;
    }
}
