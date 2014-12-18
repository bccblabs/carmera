package veme.cario.com.CARmera.model.UserModels;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by bski on 12/18/14.
 */
@ParseClassName("Contact")
public class Contact extends ParseObject {
    public Contact () {}

    public void setParseUser (ParseUser user) {
        put ("user", user);
    }

    public ParseUser getParseUser () {
        return getParseUser("user");
    }

    public ParseFile getUserThumbnail() {
        return getParseFile("user_thumbnail");
    }

}
