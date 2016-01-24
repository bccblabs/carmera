package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by bski on 1/22/16.
 */

@ParseClassName("ParseLead")
public class ParseLead extends ParseObject {
    public String getContactName () {
        return getString("contactName");
    }

    public void setContactName (String val) {
        put ("contactName", val);
    }

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId (String val) {
        put ("userId", val);
    }


    public String getPhone() {
        return getString("phone");
    }

    public void setPhone (String val) {
        put ("phone", val);
    }


    public String getEmail() {
        return getString("email");
    }

    public void setEmail (String val) {
        put ("email", val);
    }

    public String getVin() {
        return getString("vin");
    }

    public void setVin (String val) {
        put ("vin", val);
    }

    public String getStock() {
        return getString("stock");
    }

    public void setStock (String val) {
        put ("stock", val);
    }

    public String getYear() {
        return getString("year");
    }

    public void setYear (String val) {
        put ("year", val);
    }


    public String getMake() {
        return getString("make");
    }

    public void setMake (String val) {
        put ("make", val);
    }


    public String getModel() {
        return getString("model");
    }

    public void setModel (String val) {
        put ("model", val);
    }


    public String getTrim() {
        return getString("trim");
    }

    public void setTrim (String val) {
        put ("trim", val);
    }


    public String getDealerName() {
        return getString("dealerName");
    }

    public void setDealerName (String val) {
        put ("dealerName", val);
    }

    public String getDealerId() {
        return getString("dealerId");
    }

    public void setDealerId (String val) {
        put ("dealerId", val);
    }

    public String getFranchiseId() {
        return getString("franchiseId");
    }

    public void setFranchiseId (String val) {
        put ("franchiseId", val);
    }

}
