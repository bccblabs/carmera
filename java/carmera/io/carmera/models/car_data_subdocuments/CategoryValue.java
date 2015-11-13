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
public class CategoryValue {
    @JsonProperty
    public String category;

    @JsonProperty
    public List<DataEntry> options;

    @JsonProperty
    public String overall;

    public CategoryValue() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<DataEntry> getOptions() {
        return options;
    }

    public void setOptions(List<DataEntry> options) {
        this.options = options;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }
}
