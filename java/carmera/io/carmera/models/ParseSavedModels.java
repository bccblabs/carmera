package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by bski on 1/22/16.
 */
@ParseClassName("ParseSavedModels")
public class ParseSavedModels extends ParseObject{

    public void setUserId (String userId) {
        put ("userId", userId);
    }

    public String getUserId () {
        return getString("userId");
    }

    public void setMake (String make) {
        put("make", make);
    }
    public String getMake () {
        return getString("make");
    }

    public void setModel (String model) {
        put("model", model);
    }
    public String getModel () {
        return getString("model");
    }


    public void setStartYr (Integer startYr) {
        put("startYr", startYr);
    }
    public Integer getStartYr () {
        return getInt("startYr");
    }

    public void setEndYr (Integer endYr) {
        put("endYr", endYr);
    }
    public Integer getEndYr () {
        return getInt("endYr");
    }

    public void setHp (Integer startYr) {
        put("hp", startYr);
    }
    public Integer getHp () {
        return getInt("hp");
    }

    public void setTq (Integer endYr) {
        put("tq", endYr);
    }
    public Integer getTq () {
        return getInt("tq");
    }

    public void getCity (Integer startYr) {
        put("city", startYr);
    }
    public Integer getCity () {
        return getInt("city");
    }

    public void setHwy (Integer endYr) {
        put("hwy", endYr);
    }
    public Integer getHwy () {
        return getInt("hwy");
    }

    public void setMatchingListings (Integer endYr) {
        put("matching_listings", endYr);
    }
    public Integer getMatchingListings () {
        return getInt("matching_listings");
    }


    public void setBodyType (String model) {
        put("bodytype", model);
    }
    public String getBodyType () {
        return getString("bodytype");
    }


}
