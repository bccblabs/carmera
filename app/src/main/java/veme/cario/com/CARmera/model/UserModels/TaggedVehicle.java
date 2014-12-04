package veme.cario.com.CARmera.model.UserModels;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by bski on 11/5/14.
 */
@ParseClassName("TaggedVehicle")
public class TaggedVehicle extends ParseObject {

    // Requires empty constructor
    public TaggedVehicle() {}

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

    public void setUser (UserInfo user_info) {
        put("user_info", user_info);
    }

    public ParseFile getUserPhoto() {
        UserInfo userinfo = (UserInfo) getParseObject("user_info");
        return userinfo.getThumbnail();
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

    public String getSellerInfo () {
        return getString ("seller_info");
    }

    public String getSellerPhone() {
        return getString("seller_phone");
    }

    public String getSellerEmail() {
        return getString ("seller_email");
    }

    public ParseFile getSellerThumbnail() { return getParseFile("seller_thumbnail"); }

    public String getLikesCnt() { return Integer.toString(getList("likes").size()); }

    public boolean isLikedByMe() {
        List<String> likers_id = getList("likes");
        return likers_id.contains(ParseUser.getCurrentUser().getObjectId());
    }

    public void addLiker(String id) {
        addUnique("likers", id);
    }

    public void removeLiker (String id) {
        List<String> likers_id = getList("likers");
        likers_id.remove(likers_id);
        remove("likers");
        addAllUnique("likers", likers_id);
    }

    public ParseUser getReferer() { return getParseUser("referer");}

}
