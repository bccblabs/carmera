package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 7/24/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class ListingV2 {

    @JsonProperty
    public Integer year;

    public Integer dealerZip;

    @JsonProperty
    public String dateinstock;

    @JsonProperty
    public String ext_color_generic;

    @JsonProperty
    public List<String> imagelist;

    @JsonProperty
    public String dealerCity;

    @JsonProperty
    public String type;

    @JsonProperty
    public String stock;

    @JsonProperty
    public String dealerState;

    @JsonProperty
    public String dealername;

    @JsonProperty
    public String dealerAddress;

    @JsonProperty
    public String dealerFax;

    @JsonProperty
    public String vin;

    @JsonProperty
    public String exteriorcolor;

    @JsonProperty
    public String dealeremail;

    @JsonProperty
    public String certified;

    @JsonProperty
    public Double sellingprice;

    @JsonProperty
    public String interiorcolor;

    @JsonProperty
    public List<String> options;

    @JsonProperty
    public Double msrp;

    @JsonProperty
    public Snapshot snapshot;

    @JsonProperty
    public Integer miles;

    public ListingV2() {
    }

    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @JsonProperty("dealer zip")
    public Integer getDealerZip() {
        return dealerZip;
    }

    @JsonProperty("dealer zip")
    public void setDealerZip(Integer dealerZip) {
        this.dealerZip = dealerZip;
    }

    public String getDateinstock() {
        return dateinstock;
    }

    public void setDateinstock(String dateinstock) {
        this.dateinstock = dateinstock;
    }

    public String getExt_color_generic() {
        return ext_color_generic;
    }

    public void setExt_color_generic(String ext_color_generic) {
        this.ext_color_generic = ext_color_generic;
    }

    public List<String> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<String> imagelist) {
        this.imagelist = imagelist;
    }

    @JsonProperty("dealer city")
    public String getDealerCity() {
        return dealerCity;
    }

    @JsonProperty("dealer city")
    public void setDealerCity(String dealerCity) {
        this.dealerCity = dealerCity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @JsonProperty("dealer state")
    public String getDealerState() {
        return dealerState;
    }

    @JsonProperty("dealer state")
    public void setDealerState(String dealerState) {
        this.dealerState = dealerState;
    }

    public String getDealername() {
        return dealername;
    }

    public void setDealername(String dealername) {
        this.dealername = dealername;
    }

    @JsonProperty("dealer address")
    public String getDealerAddress() {
        return dealerAddress;
    }

    @JsonProperty("dealer address")
    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    @JsonProperty("dealer fax")
    public String getDealerFax() {
        return dealerFax;
    }

    @JsonProperty("dealer fax")
    public void setDealerFax(String dealerFax) {
        this.dealerFax = dealerFax;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getExteriorcolor() {
        return exteriorcolor;
    }

    public void setExteriorcolor(String exteriorcolor) {
        this.exteriorcolor = exteriorcolor;
    }

    public String getDealeremail() {
        return dealeremail;
    }

    public void setDealeremail(String dealeremail) {
        this.dealeremail = dealeremail;
    }

    public String getCertified() {
        return certified;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public Double getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(Double sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getInteriorcolor() {
        return interiorcolor;
    }

    public void setInteriorcolor(String interiorcolor) {
        this.interiorcolor = interiorcolor;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Double getMsrp() {
        return msrp;
    }

    public void setMsrp(Double msrp) {
        this.msrp = msrp;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }
}
