package veme.cario.com.CARmera.model;

import com.parse.ParseObject;

/* Implementation note:
    1. no need for UI, so only setters no getters
 */

public class BusinessRequest extends ParseObject {

    public enum BusRequestType {
        Dealership,
        Service,
        Upgrade;
    }

    /* Default empty constructor */
    public BusinessRequest () {}

    public void setDestination(String destination) {
        put ("destination", destination);
    }

    public void setRequestType(BusRequestType busRequestType) {
        put ("requestType", busRequestType);
    }

}