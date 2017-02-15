package com.bluetree.web.admin.update;

import java.util.List;

public class UpdateForm {

    private List jobDetails;

    public List getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(List jobDetails) {
        this.jobDetails = jobDetails;
    }

    private List markets;

    public List getMarkets() {
        return markets;
    }

    public void setMarkets(List markets) {
        this.markets = markets;
    }

    private String marketId;

    private String startDate;

    private String increment;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

}
