package veme.cario.com.foshop.model;

import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by bski on 11/5/14.
 */
public class Vehicle extends ParseObject {

    // Requires empty constructor
    public Vehicle() {}

    public String getYear() {
        return getString("year");
    }

    public void setYear(String year) {
        put ("year", year);
    }

    public String getMake() {
        return getString("make");
    }

    public void setMake(String make) {
        put ("make", make);
    }

    public String getModel() {
        return getString ("model");
    }

    public void setModel (String model) {
        put ("model", model);
    }

    public String getTagTimestamp () {
        return getString ("ts");
    }

    public void setTagTimestamp(String timestamp) {
        put ("ts", timestamp);
    }

    public ParseFile getTagPhoto() {
        return getParseFile("photo");
    }

    public void setTagPhoto (ParseFile photo) {
        put ("photo", photo);
    }

    public boolean isFavorite() {
        return getBoolean("favorite");
    }

    public void setFavorite(boolean fav) {
        put ("favorite", fav);
    }
}
