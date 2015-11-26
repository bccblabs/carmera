package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/24/15.
 */
@Parcel
@JsonIgnoreProperties (ignoreUnknown = true)
public class Incentive {

    @JsonProperty
    public String startDate;

    @JsonProperty
    public String endDate;

    @JsonProperty
    public String restrictions;

    @JsonProperty
    public String name;



    @JsonProperty
    public Integer termMonths;
    @JsonProperty
    public Float apr;

    @JsonProperty
    public Integer rebateAmount;


    @JsonProperty
    public Integer monthlyPayment;
    @JsonProperty
    public Integer requiredDownPayment;
    @JsonProperty
    public Integer annualMileage;



    public Incentive() {
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getApr() {
        return apr;
    }

    public void setApr(Float apr) {
        this.apr = apr;
    }

    public Integer getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(Integer rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public Integer getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Integer monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Integer getRequiredDownPayment() {
        return requiredDownPayment;
    }

    public void setRequiredDownPayment(Integer requiredDownPayment) {
        this.requiredDownPayment = requiredDownPayment;
    }

    public Integer getAnnualMileage() {
        return annualMileage;
    }

    public void setAnnualMileage(Integer annualMileage) {
        this.annualMileage = annualMileage;
    }
}
