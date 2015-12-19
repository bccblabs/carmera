package carmera.io.carmera.models.listings_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class ContactInfo {

    public ContactInfo() {
    }

    @JsonProperty
    public String phone;
    @JsonProperty
    public String website;
    @JsonProperty
    public String gpContactEmail;

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

    public String getGpContactEmail() {
        return gpContactEmail;
    }

    public void setGpContactEmail(String gpContactEmail) {
        this.gpContactEmail = gpContactEmail;
    }
}
