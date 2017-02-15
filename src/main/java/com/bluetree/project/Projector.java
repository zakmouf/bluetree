package com.bluetree.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Position;
import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.manager.MarketManager;
import com.bluetree.manager.PortfolioManager;
import com.bluetree.manager.StockManager;
import com.bluetree.util.MessageUtil;
import com.bluetree.util.PriceUtil;

public class Projector {

    private PortfolioManager portfolioManager;

    private MarketManager marketManager;

    private StockManager stockManager;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }

    public PortfolioManager getPortfolioManager() {
        return portfolioManager;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    public StockManager getStockManager() {
        return stockManager;
    }

    public void setStockManager(StockManager stockManager) {
        this.stockManager = stockManager;
    }

    public void project(ProjectConfig config) throws ProjectException {

        Portfolio portfolio = config.getPortfolio();
        Market market = portfolioManager.getMarket(portfolio);
        Stock indice = marketManager.getIndice(market);
        List positions = portfolioManager.getPositions(portfolio);

        Date fromDate = portfolio.getToDate();

        List indicePrices = stockManager.getPricesInclusive(indice, fromDate);
        if (indicePrices.size() < 5) {
            throw new ProjectException(MessageUtil.msg("Indice <{0}> is empty",
                    indice.getSymbol()));
        }

        Map prices = new HashMap();
        Iterator iterator = positions.iterator();
        while (iterator.hasNext()) {
            Position position = (Position) iterator.next();
            Stock stock = position.getStock();
            List stockPrices = stockManager.getPricesInclusive(stock, fromDate);
            if (stockPrices.size() < 5) {
                throw new ProjectException(MessageUtil.msg(
                        "Stock <{0}> is empty", stock.getSymbol()));
            }
            prices.put(position, stockPrices);
        }

        Date lastDate = PriceUtil.lastDate(indicePrices);
        iterator = prices.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            List stockPrices = (List) entry.getValue();
            Date stockLastDate = PriceUtil.lastDate(stockPrices);
            if (stockLastDate.getTime() < lastDate.getTime()) {
                lastDate = stockLastDate;
            }
        }

        List indicePrices2 = new ArrayList();
        iterator = indicePrices.iterator();
        while (iterator.hasNext()) {
            Price price = (Price) iterator.next();
            if (price.getDate().getTime() <= lastDate.getTime()) {
                indicePrices2.add(price);
            }
        }
        indicePrices = indicePrices2;
        if (indicePrices.size() < 5) {
            throw new ProjectException("Not enough datas for all stocks");
        }

        Date[] dates = new Date[indicePrices.size()];
        iterator = indicePrices.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            Price price = (Price) iterator.next();
            dates[i] = price.getDate();
        }

        double[] indiceReturns = PriceUtil.calculateReturns(indicePrices);
        double[] portfolioReturns = new double[indiceReturns.length];

        iterator = prices.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            Position position = (Position) entry.getKey();
            double weight = position.getWeight().doubleValue();
            List stockPrices = (List) entry.getValue();
            PriceUtil.matchPrices(indicePrices, stockPrices);
            double[] stockReturns = PriceUtil.calculateReturns(stockPrices);
            for (int i = 0; i < indiceReturns.length; i++) {
                portfolioReturns[i] = portfolioReturns[i]
                        + (stockReturns[i] * weight);
            }
        }

        double[] indiceNavs = new double[indiceReturns.length + 1];
        double[] portfolioNavs = new double[indiceReturns.length + 1];
        indiceNavs[0] = 100.0;
        portfolioNavs[0] = 100.0;
        for (int i = 0; i < indiceReturns.length; i++) {
            indiceNavs[i + 1] = indiceNavs[i] * (1.0 + indiceReturns[i]);
            portfolioNavs[i + 1] = portfolioNavs[i]
                    * (1.0 + portfolioReturns[i]);
        }

        config.setDates(dates);
        config.setIndiceReturns(indiceReturns);
        config.setPortfolioReturns(portfolioReturns);
        config.setIndiceNavs(indiceNavs);
        config.setPortfolioNavs(portfolioNavs);

    }

}
