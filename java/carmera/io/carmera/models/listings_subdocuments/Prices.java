package carmera.io.carmera.models.listings_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Prices {

    @JsonProperty
    public double msrp;
    @JsonProperty
    public double tmv;
    @JsonProperty
    public double guaranteedPrice;
    @JsonProperty
    public double invoice;
    @JsonProperty
    public double monthlyPayment;
    @JsonProperty
    public double listPrice;
    @JsonProperty
    public double dealerOfferPrice;

    public Prices() {
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getDealerOfferPrice() {
        return dealerOfferPrice;
    }

    public void setDealerOfferPrice(double dealerOfferPrice) {
        this.dealerOfferPrice = dealerOfferPrice;
    }

    public double getMsrp() {
        return msrp;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }

    public double getTmv() {
        return tmv;
    }

    public void setTmv(double tmv) {
        this.tmv = tmv;
    }

    public double getGuaranteedPrice() {
        return guaranteedPrice;
    }

    public void setGuaranteedPrice(double guaranteedPrice) {
        this.guaranteedPrice = guaranteedPrice;
    }

    public double getInvoice() {
        return invoice;
    }

    public void setInvoice(double invoice) {
        this.invoice = invoice;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
}
