package com.bluetree.project;

import java.io.Serializable;
import java.util.Date;

import com.bluetree.domain.Portfolio;

public class ProjectConfig implements Serializable {

    private Portfolio portfolio;

    public ProjectConfig(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    private Date[] dates;

    private double[] indiceReturns;

    private double[] portfolioReturns;

    private double[] indiceNavs;

    private double[] portfolioNavs;

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public double[] getIndiceNavs() {
        return indiceNavs;
    }

    public void setIndiceNavs(double[] indiceNavs) {
        this.indiceNavs = indiceNavs;
    }

    public double[] getIndiceReturns() {
        return indiceReturns;
    }

    public void setIndiceReturns(double[] indiceReturns) {
        this.indiceReturns = indiceReturns;
    }

    public double[] getPortfolioNavs() {
        return portfolioNavs;
    }

    public void setPortfolioNavs(double[] portfolioNavs) {
        this.portfolioNavs = portfolioNavs;
    }

    public double[] getPortfolioReturns() {
        return portfolioReturns;
    }

    public void setPortfolioReturns(double[] portfolioReturns) {
        this.portfolioReturns = portfolioReturns;
    }

}
