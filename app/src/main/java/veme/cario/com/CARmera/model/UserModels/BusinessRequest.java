package veme.cario.com.CARmera.model.UserModels;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("BusinessRequest")
public class BusinessRequest extends ParseObject {

    public enum BusRequestType {
        Dealership,
        Service,
        Upgrade
    }

    public BusinessRequest () {}

    public void setDestination(String destination) {
        put ("destination", destination);
    }

    public void setRequestType(BusRequestType busRequestType) {
        put ("requestType", busRequestType);
    }

}
