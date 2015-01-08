package veme.cario.com.CARmera.model.UserModels;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by bski on 1/8/15.
 */
@ParseClassName("SavedSearch")
public class SavedSearch extends ParseObject {
    public SavedSearch() {}

    public String getMinPrice() {
        return getString ("min_price");
    }

    public String getMaxPrice() {
        return getString ("max_price");
    }

    public String getBodyStyle() {
        return getString ("body_style");
    }

    public String getMinYear() {
        return getString("min_year");
    }

    public String getMaxYear() {
        return getString("max_year");
    }

    public List<String> getMakes() {
        return getList("make");
    }

    public List<String> getModels() {
        return getList("model");
    }

    public String getTransmission() {
        return getString ("transmission");
    }

    public List<String> getCylinderCnts() {
        return getList ("cylinderCnts");
    }
    public void setMinPrice(String val) {
        put ("min_price", val);
    }

    public void setMaxPrice(String val) {
        put ("max_price", val);
    }

    public void setBodyStyle(String val) {
        put ("body_style", val);
    }

    public void setMinYear(String val) {
        put("min_year", val);
    }

    public void setMaxYear(String val) {
        put("max_year", val);
    }

    public void addMake(String val) {
        add("make", val);
    }

    public void addModel(String val) {
        add("model", val);
    }

    public void setTransmission(String val) {
        put ("transmission", val);
    }

    public void addCylinderCnt(String val) {
        add ("cylinderCnts", val);
    }

    public String getSearchName () {
        return getString ("search_name");
    }

    public void setSearchName (String val) {
        put ("search_name", val);
    }

    public String getSearchRadius () {
        return getString ("search_radius");
    }

    public void setSearchRadius (String val) {
        put ("search_radius", val);
    }

}