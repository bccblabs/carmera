package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Nhtsa {
    @JsonProperty
    public List<CategoryValue> categories;

    @JsonProperty
    public String overall;

    public Nhtsa() {
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public List<CategoryValue> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryValue> categories) {
        this.categories = categories;
    }
}
