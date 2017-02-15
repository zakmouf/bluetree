package com.bluetree.web.user.forms;

import java.util.List;

public class PortfolioForm {

    private String name;

    private String marketId;

    private String fromDate;

    private String toDate;

    private String beta;

    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private List markets;

    public List getMarkets() {
        return markets;
    }

    public void setMarkets(List markets) {
        this.markets = markets;
    }

}
