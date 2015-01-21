package veme.cario.com.CARmera.model.UserModels;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by bski on 1/20/15.
 */
@ParseClassName("UserActivity")
public class UserActivity extends ParseObject {
    public UserActivity () {}

    public String getType () { return getString("type"); }

    public void setType (String type) { put ("type", type); }

    public ParseUser getDestUser () {
        try {
            ParseQuery<ParseObject> src_query = ParseQuery.getQuery("ParseUser");
            ParseUser user  = (ParseUser) src_query.get(getString ("dest_ptr"));
            return user;
        } catch (ParseException e) {
            Log.i (UserActivity.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    public void setDestPtr (String dest_ptr) {
        put ("dest_ptr", dest_ptr);
    }

    public ParseUser getSrcUser () {
        try {
            ParseQuery<ParseObject> src_query = ParseQuery.getQuery("ParseUser");
            ParseUser user  = (ParseUser) src_query.get(getString("src_ptr"));
            return user;
        } catch (ParseException e) {
            Log.i (UserActivity.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    public void putSrcPtr (String src_ptr) {
        put ("src_ptr", src_ptr);
    }

    public void setDataId (String data_id) {
        put ("data_ptr", data_id);
    }


    /* For sake of simplicity */

    public String getSrcName () { return getString ("src_name"); }
    public String getDestName () { return getString ("dest_name"); }
    public void setSrcName (String val_) { put ("src_name", val_); }
    public void setDestName (String val_) { put ("dest_name", val_); }

    public ParseObject getData () {
        try {
            ParseQuery<ParseObject> src_query = ParseQuery.getQuery("ParseUser");
            return (ParseUser) src_query.get(getString("data_ptr"));
        } catch (ParseException e) {
            Log.i (UserActivity.class.getSimpleName(), e.getMessage());
            return null;
        }
    }


    public String toString () {
        String msg = " shared " + getData().toString() + " with ";
        ParseUser user = getSrcUser();
        if (user == ParseUser.getCurrentUser()) {
            msg = "I have " + msg + " with " + getDestUser().getUsername() + ".";
        } else {
            if ((user = getDestUser()) == ParseUser.getCurrentUser())
                msg = user.getUsername() + " has " + msg + " with you.";
            else
                msg = user.getUsername() + " has " + msg + " with " + user.getUsername() + "." ;
        }
        return getSrcUser() + " has shared " + getData().toString() + " with " + getDestUser() + ".";
//        return msg;
    }
}
