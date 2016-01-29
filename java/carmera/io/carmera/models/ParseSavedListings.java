package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by bski on 1/22/16.
 */

@ParseClassName("ParseSavedListings")
public class ParseSavedListings extends ParseObject {


    public static ParseQuery<ParseSavedListings> getQuery() {
        return ParseQuery.getQuery(ParseSavedListings.class);
    }

    public void setUser (ParseUser userId) {
        put ("user", userId);
    }

    public ParseUser getUser () {
        return getParseUser("user");
    }

    public void franchiseId (String franchiseId) {
        put ("franchiseId", franchiseId);
    }

    public String getFranchiseId () {
        return getString("franchiseId");
    }

    public void setStockNumber (String stockNumber) {
        put ("stockNumber", stockNumber);
    }

    public String getStockNumber () {
        return getString ("stockNumber");
    }

    public void setMake (String val) {
        put ("make", val);
    }

    public String getMake () {
        return getString("make");
    }

    public void setModel (String val) {
        put ("model", val);
    }

    public String getModel () {
        return getString("make");
    }



    public void setTrim (String val) {
        put ("trim", val);
    }

    public String getTrim () {
        return getString("trim");
    }


    public void setYear (Integer val) {
        put ("year", val);
    }

    public Integer getYear () {
        return getInt("year");
    }


}
