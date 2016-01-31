package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

import carmera.io.carmera.models.car_data_subdocuments.DealerRatings;
import carmera.io.carmera.models.car_data_subdocuments.DealerRatingsReviews;
import carmera.io.carmera.models.listings_subdocuments.Address;
import carmera.io.carmera.models.listings_subdocuments.ContactInfo;
import carmera.io.carmera.models.listings_subdocuments.Operations;

/**
 * Created by bski on 1/30/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class DealerInfo {
    @JsonProperty
    public String dealerId;

    @JsonProperty
    public String name;

    @JsonProperty
    public Operations operations;

    @JsonProperty
    public Address address;

    @JsonProperty
    public ContactInfo contactInfo;

    @JsonProperty
    public List<String> states;

    @JsonProperty
    public DealerRatingsReviews reviews;

    public DealerInfo() {
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Operations getOperations() {
        return operations;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public DealerRatingsReviews getReviews() {
        return reviews;
    }

    public void setReviews(DealerRatingsReviews reviews) {
        this.reviews = reviews;
    }
}
