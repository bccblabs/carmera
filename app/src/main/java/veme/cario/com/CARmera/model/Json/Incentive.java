package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bski on 1/14/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Incentive {
    @JsonProperty
    private Double dealerCashAmount;

    @JsonProperty
    private String contentType;

    @JsonProperty
    private String restrictions;

    @JsonProperty
    private String name;

    @JsonProperty
    private String startDate;

    @JsonProperty
    private String endDate;

    @JsonProperty
    private String termMonths;

    @JsonProperty
    private Double apr;

    @JsonProperty
    private String creditRatingTier;

    @JsonProperty
    private Double rebateAmount;

    public Double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(Double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }


    public String getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(String termMonths) {
        this.termMonths = termMonths;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public String getCreditRatingTier() {
        return creditRatingTier;
    }

    public void setCreditRatingTier(String creditRatingTier) {
        this.creditRatingTier = creditRatingTier;
    }

    public Double getDealerCashAmount() {
        return dealerCashAmount;
    }

    public void setDealerCashAmount(Double dealerCashAmount) {
        this.dealerCashAmount = dealerCashAmount;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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
}
