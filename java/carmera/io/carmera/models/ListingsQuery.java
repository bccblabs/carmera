package carmera.io.carmera.models;

import org.parceler.Parcel;

import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.CarQuery;

/**
 * Created by bski on 11/7/15.
 */
@Parcel
public class ListingsQuery {
    public ApiQuery api;
    public CarQuery car;

    public ApiQuery getApi() {
        return api;
    }

    public void setApi(ApiQuery api) {
        this.api = api;
    }

    public CarQuery getCar() {
        return car;
    }

    public void setCar(CarQuery car) {
        this.car = car;
    }
}
