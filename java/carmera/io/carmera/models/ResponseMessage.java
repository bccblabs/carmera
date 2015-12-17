package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 12/17/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class ResponseMessage {
    @JsonProperty
    public String message;

    public String getMessage() {
        return message;
    }

    public ResponseMessage() {
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
