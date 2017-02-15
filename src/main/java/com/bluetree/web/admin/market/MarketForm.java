package com.bluetree.web.admin.market;

import java.util.List;

public class MarketForm {

    private List stocks;

    public List getStocks() {
        return stocks;
    }

    public void setStocks(List stocks) {
        this.stocks = stocks;
    }

    private String name;

    private String riskless;

    private String indiceId;

    public String getIndiceId() {
        return indiceId;
    }

    public void setIndiceId(String indiceId) {
        this.indiceId = indiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRiskless() {
        return riskless;
    }

    public void setRiskless(String riskless) {
        this.riskless = riskless;
    }

}
