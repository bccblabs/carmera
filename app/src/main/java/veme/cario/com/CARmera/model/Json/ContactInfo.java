package veme.cario.com.CARmera.model.Json;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bski on 11/16/14.
 */
// // @JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfo {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    private String phone;
    private String website;

}
