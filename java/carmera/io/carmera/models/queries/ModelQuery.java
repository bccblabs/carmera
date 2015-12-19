package carmera.io.carmera.models.queries;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 12/18/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class ModelQuery {
    @JsonProperty
    public String model;
    @JsonProperty
    public String imageUrl;
    @JsonProperty
    public String powerDesc;
    @JsonProperty
    public String mpgDesc;
    @JsonProperty
    public String yearDesc;
    @JsonProperty
    public List<Integer> styleIds;

    public ModelQuery() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPowerDesc() {
        return powerDesc;
    }

    public void setPowerDesc(String powerDesc) {
        this.powerDesc = powerDesc;
    }

    public String getMpgDesc() {
        return mpgDesc;
    }

    public void setMpgDesc(String mpgDesc) {
        this.mpgDesc = mpgDesc;
    }

    public String getYearDesc() {
        return yearDesc;
    }

    public void setYearDesc(String yearDesc) {
        this.yearDesc = yearDesc;
    }

    public List<Integer> getStyleIds() {
        return styleIds;
    }

    public void setStyleIds(List<Integer> styleIds) {
        this.styleIds = styleIds;
    }
}
