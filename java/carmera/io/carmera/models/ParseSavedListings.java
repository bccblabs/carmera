package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by bski on 1/22/16.
 */

@ParseClassName("ParseSavedListings")
public class ParseSavedListings extends ParseObject {


    public static ParseQuery<ParseSavedListings> getQuery() {
        return ParseQuery.getQuery(ParseSavedListings.class);
    }

    public void setUserId (String userId) {
        put ("userId", userId);
    }

    public String getUserId () {
        return getString ("userId");
    }

    public void franchiseId (String franchiseId) {
        put ("franchiseId", franchiseId);
    }

    public String getFranchiseId () {
        return getString ("franchiseId");
    }

    public void setStockNumber (String stockNumber) {
        put ("stockNumber", stockNumber);
    }

    public String getStockNumber () {
        return getString ("stockNumber");
    }
}