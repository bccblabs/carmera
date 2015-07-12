package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by bski on 7/11/15.
 */
@ParseClassName("CapturedVehicle")
public class CapturedVehicle extends ParseObject {

    public String getGenName() {
        return getString("gen_name");
    }

    public void setGenName(String gen_name) {
        put ("year", gen_name);
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

    public void setTagPhoto (ParseFile photo) {
        put ("photo", photo);
    }

    public void setThumbnail (ParseFile thumbnail) { put ("thumbnail", thumbnail); }

    public ParseFile getThumbnail () { return getParseFile ("thumbnail"); }

}
