package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import carmera.io.carmera.models.listings_subdocuments.Make;
import carmera.io.carmera.models.listings_subdocuments.Model;
import carmera.io.carmera.models.listings_subdocuments.Prices;
import carmera.io.carmera.models.listings_subdocuments.Dealer;
import carmera.io.carmera.models.listings_subdocuments.Media;
import carmera.io.carmera.models.listings_subdocuments.Style;
import carmera.io.carmera.models.listings_subdocuments.Year;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Listing {
    public Listing() {
    }

    @JsonProperty
    public Float min_price;
    @JsonProperty
    public String id;
    @JsonProperty
    public String type;
    @JsonProperty
    public String vin;
    @JsonProperty
    public String stockNumber;
    @JsonProperty
    public int mileage;
    @JsonProperty
    public String listedSince;
    @JsonProperty
    public Make make;
    @JsonProperty
    public Model model;
    @JsonProperty
    public Year year;
    @JsonProperty
    public Style style;
    @JsonProperty
    public Media media;
    @JsonProperty
    public Prices prices;
    @JsonProperty
    public Dealer dealer;


    public Float getMin_price() {
        return min_price;
    }

    public void setMin_price(Float min_price) {
        this.min_price = min_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getListedSince() {
        return listedSince;
    }

    public void setListedSince(String listedSince) {
        this.listedSince = listedSince;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
