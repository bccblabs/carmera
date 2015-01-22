package veme.cario.com.CARmera.model.UserModels;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by bski on 11/5/14.
 */
@ParseClassName("TaggedVehicle")
public class TaggedVehicle extends ParseObject {
    /* TODO: how to implement tagging? */

    public TaggedVehicle() {}


    public String getStyleId () {
        return getString ("style_id");
    }

    public void setStyleId (String val) {
        put ("style_id", val);
    }

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

    public ParseFile getTagPhoto() {
        return getParseFile("photo");
    }

    public void setUser (ParseUser parseUser) {
        ParseRelation<ParseUser> parentUser = getRelation("user");
        parentUser.add(parseUser);
        this.saveInBackground();
    }

    public void setTagPhoto (ParseFile photo) {
        put ("photo", photo);
    }

    public void setFavorite(boolean fav) {
        put ("favorite", fav);
    }

    public boolean isFavorites() { return getBoolean("favorites");}

    public void setLocation (ParseGeoPoint location) {
        put ("location", location);
    }

    public ParseGeoPoint getLocation () {
       return getParseGeoPoint("location");
    }

    public String getUserName() {
        return ParseUser.getCurrentUser().getUsername();
    }

    @Override
    public String toString () {
        return getYear() + " " + getMake() + " " + getModel();
    }

    public String getPrice () {
        return getString("price");
    }

    public void setPrice (String price) { put("price", price);}

    public String getSellerInfo () {
        return getString ("seller_info");
    }

    public void setSellerInfo (String sellerInfo) {
        put ("seller_info", sellerInfo);
    }

    public String getSellerPhone() {
        return getString("seller_phone");
    }

    public void setSellerPhone (String phone) { put ("seller_phone", phone); }

    public String getSellerEmail() {
        return getString ("seller_email");
    }

    public void setSellerEmail (String email) { put ("seller_email", email); }

    public ParseFile getSellerThumbnail() { return getParseFile("seller_thumbnail"); }

    public void setSellerThumbnail (ParseFile thumbnail) { put ("seller_thumbnail", thumbnail); }

    public String getLikesCnt() {
        if (containsKey("likers"))
            return Integer.toString(getList("likers").size());
        else
            return Integer.toString(0);
    }

    public boolean isLikedByMe() {
        if (containsKey("likers")) {
            List<String> likers_id = getList("likers");
            return likers_id.contains(ParseUser.getCurrentUser().getObjectId());
        } else {
            return false;
        }
    }

    public void addLiker(String id) {
        addUnique("likers", id);
    }

    public void removeLiker (String id) {
        if (containsKey("likers")) {
            List<String> likers_id = getList("likers");
            likers_id.remove(likers_id);
            remove("likers");
            addAllUnique("likers", likers_id);
        }
    }

    public ParseUser getReferer() { return getParseUser("referer");}

    public boolean isListing() { return getBoolean("listing");}

    public void setListing(boolean listing) { put("listing", listing); }

    public void setThumbnail (ParseFile thumbnail) { put ("thumbnail", thumbnail); }

    public ParseFile getThumbnail () { return getParseFile ("thumbnail"); }

    public String getMileage () { return getString ("mileage"); }

    public void setMileage (String val) { put ("mileage", val); }

    public String getVin () { return getString ("vin"); }

    public void setVin (String val) { put ("vin", val); }
}
