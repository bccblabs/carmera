package carmera.io.carmera.models.car_data_subdocuments;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Engine {
    @JsonProperty
    public String compressionRatio;
    @JsonProperty
    public String compressorType;
    @JsonProperty
    public Integer cylinder;
    @JsonProperty
    public String fuelType;
    @JsonProperty
    public Integer horsepower;
    @JsonProperty
    public Float displacement;
    @JsonProperty
    public Integer torque;
    @JsonProperty
    public Integer totalValves;
    @JsonProperty
    public String desc;
    @JsonProperty
    public RPM rpm;

    public Engine() {
    }

    public RPM getRpm() {
        return rpm;
    }

    public void setRpm(RPM rpm) {
        this.rpm = rpm;
    }

    public String getCompressionRatio() {
        return compressionRatio;
    }

    public void setCompressionRatio(String compressionRatio) {
        this.compressionRatio = compressionRatio;
    }

    public String getCompressorType() {
        return compressorType;
    }

    public void setCompressorType(String compressorType) {
        this.compressorType = compressorType;
    }

    public Integer getCylinder() {
        return cylinder;
    }

    public void setCylinder(Integer cylinder) {
        this.cylinder = cylinder;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    public Float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Float displacement) {
        this.displacement = displacement;
    }

    public Integer getTorque() {
        return torque;
    }

    public void setTorque(Integer torque) {
        this.torque = torque;
    }

    public Integer getTotalValves() {
        return totalValves;
    }

    public void setTotalValves(Integer totalValves) {
        this.totalValves = totalValves;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
