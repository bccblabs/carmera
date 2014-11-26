package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import veme.cario.com.CARmera.model.Json.ContactInfo;
import veme.cario.com.CARmera.model.Json.DealershipAddress;
import veme.cario.com.CARmera.model.Json.Operations;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class VehicleDealership {

    public String getDealershipIdgetDealershipId() {
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

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Operations getOperations() {
        return operations;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    @JsonProperty
    private String dealerId;

    @JsonProperty
    private String name;

    @JsonProperty
    private int distance;

    @JsonProperty
    private DealershipAddress address;

    @JsonProperty
    private Operations operations;

    @JsonProperty
    private ContactInfo contactInfo;


}
