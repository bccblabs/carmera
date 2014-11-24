package veme.cario.com.CARmera.model.APIModels;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import veme.cario.com.CARmera.model.Json.ContactInfo;
import veme.cario.com.CARmera.model.Json.DealershipAddress;

/**
 * Created by bski on 11/16/14.
 */
// // @JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDealership {
    public String getDealershipId() {
        return dealershipId;
    }

    public void setDealershipId(String dealershipId) {
        this.dealershipId = dealershipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public DealershipAddress getAddress() {
        return address;
    }

    public void setAddress(DealershipAddress address) {
        this.address = address;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    private String dealershipId;
    private String name;
    private int distance;
    private DealershipAddress address;
    private ContactInfo contactInfo;


}
