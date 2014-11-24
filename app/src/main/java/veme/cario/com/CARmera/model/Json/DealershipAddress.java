package veme.cario.com.CARmera.model.Json;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bski on 11/16/14.
 */
// // @JsonIgnoreProperties(ignoreUnknown = true)
public class DealershipAddress {
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    private String street;
    private String city;
    private String stateCode;
    private String latitude;
    private String longitude;
    private String zipcode;
}
