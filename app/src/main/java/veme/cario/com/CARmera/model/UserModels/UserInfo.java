package veme.cario.com.CARmera.model.UserModels;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by bski on 11/25/14.
 */
@ParseClassName("UserInfo")
public class UserInfo extends ParseObject {

    public void setThumbnail (ParseFile thumbnail) {
        put("user_thumbnail", thumbnail);
    }

    public ParseFile getThumbnail () {
        return getParseFile("user_thumbnail");
    }
}

