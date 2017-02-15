package com.bluetree.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Position;
import com.bluetree.domain.Stock;
import com.bluetree.manager.MarketManager;
import com.bluetree.manager.PortfolioManager;
import com.bluetree.manager.StockManager;
import com.bluetree.util.BasketUtil;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;
import com.bluetree.util.PriceUtil;
import com.bluetree.util.Randomizer;
import com.bluetree.util.StatUtil;

public class OptimizeJobBean extends QuartzJobBean {

    private static final int PREVIEW_MAX_LOOPS = 5000;

    private static final double BETA_DELTA = 0.1;

    private static final long PREVIEW_MAX_TIME = 60L * 1000L;

    private static final int OPTIMIZE_UNSUCCESS = 40000;

    private static final long OPTIMIZE_MAX_TIME = 7L * 60L * 1000L;

    private static final int OPTIMIZE_SUCCESS = 300;

    private static final Logger logger = Logger
            .getLogger(OptimizeJobBean.class);

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

    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Long portfolioId = (Long) dataMap.get("portfolioId");
        Portfolio portfolio = portfolioManager.findPortfolio(portfolioId);
        List positions = null;
        try {
            positions = optimize(portfolio);
        } catch (OptimizeException e) {
            logger.info(e.getMessage());
            portfolio.setError(e.getMessage());
        } catch (Throwable e) {
            String message = "Unexpected error occured";
            logger.error(message, e);
            portfolio.setError(message);
        } finally {
            portfolioManager.updatePortfolio(portfolio);
            if (positions != null && !positions.isEmpty()) {
                portfolioManager.updatePositions(portfolio, positions);
            }
            System.gc();
        }
    }

    private List optimize(Portfolio portfolio) throws OptimizeException {

        logger.info(MessageUtil.msg("optimize portfolio <{0}>", portfolio));

        //
        // read data
        //
        Market market = portfolioManager.getMarket(portfolio);
        Stock indice = marketManager.getIndice(market);
        List initialStocks = marketManager.getStocks(market);
        Date fromDate = portfolio.getFromDate();
        Date toDate = portfolio.getToDate();

        // read indice
        List indicePrices = stockManager.getPrices(indice, fromDate, toDate);
        if (indicePrices.size() < 10) {
            throw new OptimizeException(MessageUtil.msg(
                    "Indice <{0}> is empty", indice.getSymbol()));
        }
        // log indice
        if (logger.isDebugEnabled()) {
            logger.debug(MessageUtil.msg(
                    "Indice <{0}> size <{1}> first <{2}> last <{3}>", indice
                            .getSymbol(), new Integer(indicePrices.size()),
                    PriceUtil.firstDate(indicePrices), PriceUtil
                            .lastDate(indicePrices)));
        }

        // correct dates
        fromDate = PriceUtil.firstDate(indicePrices);
        toDate = PriceUtil.lastDate(indicePrices);

        // read stocks
        Map prices = new HashMap();
        Iterator iterator = initialStocks.iterator();
        while (iterator.hasNext()) {
            Stock stock = (Stock) iterator.next();
            List stockPrices = stockManager.getPricesInclusive(stock, fromDate,
                    toDate);
            if (stockPrices.isEmpty()) {
                logger.debug(MessageUtil.msg("Stock <{0}> is empty", stock
                        .getSymbol()));
                continue;
            }
            int diff = PriceUtil.matchPrices(indicePrices, stockPrices);
            // log stock
            if (logger.isDebugEnabled()) {
                logger
                        .debug(MessageUtil
                                .msg(
                                        "Stock <{0}> size <{1}> first <{2}> last <{3}> diff <{4}>",
                                        stock.getSymbol(), new Integer(
                                                stockPrices.size()), PriceUtil
                                                .firstDate(stockPrices),
                                        PriceUtil.lastDate(stockPrices),
                                        new Integer(diff)));
            }
            prices.put(stock, stockPrices);
        }

        // less stocks to use
        logger.info(MessageUtil.msg("Use {0} / {1} stocks", new Integer(prices
                .size()), new Integer(initialStocks.size())));
        if (prices.size() < 10) {
            throw new OptimizeException(
                    "Less than 10 stocks match chosen dates");
        }

        // calculate returns
        double[] indiceReturns = PriceUtil.calculateReturns(indicePrices);
        LinkedMap returns = new LinkedMap();
        iterator = prices.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            Stock stock = (Stock) entry.getKey();
            List stockPrices = (List) entry.getValue();
            double[] stockReturns = PriceUtil.calculateReturns(stockPrices);
            returns.put(stock, stockReturns);
        }
        indicePrices = null;
        prices = null;

        double beta = portfolio.getBeta().doubleValue();
        double riskless = market.getRiskless().doubleValue() / 252.0;

        // indice properties
        double indiceMean = StatUtil.mean(indiceReturns);
        double indiceStdev = StatUtil.stdev(indiceReturns);
        double indiceSharp = StatUtil.sharp(indiceMean, riskless, indiceStdev);

        int size = returns.size();

        boolean ratioMaxFound = false;
        Randomizer randomizer = new Randomizer();
        double ratioMax = Double.MIN_VALUE;

        int cpt = 0;
        long start = System.currentTimeMillis();

        while (cpt < PREVIEW_MAX_LOOPS) {

            if (System.currentTimeMillis() - start > PREVIEW_MAX_TIME) {
                break;
            }

            int[] keys = randomizer.keys(10, size);
            double[] basketReturns = BasketUtil.calculateReturns(returns, keys);

            double basketMean = StatUtil.mean(basketReturns);
            double basketStdev = StatUtil.stdev(basketReturns);
            double basketSharp = StatUtil.sharp(basketMean, riskless,
                    basketStdev);
            double basketRatio = StatUtil.ratio(indiceSharp, basketSharp);
            double basketBeta = StatUtil.beta(indiceReturns, basketReturns);

            if (basketBeta >= beta - BETA_DELTA
                    && basketBeta <= beta + BETA_DELTA) {
                ratioMaxFound = true;
                if (ratioMax < basketRatio) {
                    cpt = 0;
                    ratioMax = basketRatio;
                    if (logger.isDebugEnabled()) {
                        logger.debug(MessageUtil.msg("Ratio max <{0}>",
                                FormatUtil.percent(ratioMax)));
                    }
                }
            }
            cpt++;
        }

        if (!ratioMaxFound) {
            throw new OptimizeException("No ratio found for chosen beta");
        }

        // correct ratio max
        ratioMax = (int) (ratioMax * 100.0 + 5.0);
        ratioMax = ratioMax - ((int) ratioMax % 5);
        ratioMax = ratioMax / 100.0;
        logger.info(MessageUtil.msg("Ratio max <{0}>", FormatUtil
                .percent(ratioMax)));

        // max weight
        double maxWeight = (1 - Math.exp(-0.08)) / (1 - Math.exp(-0.08 * size));
        logger.info(MessageUtil.msg("Max weight <{0}>", FormatUtil
                .percent(maxWeight)));

        int[] points = new int[size];
        int pointTotal = 0;

        randomizer = new Randomizer();

        int success = 0;
        int unsuccess = 0;

        if (logger.isDebugEnabled()) {
            logger.debug(MessageUtil.msg("Ratio <{0}> Success <{1}>",
                    FormatUtil.percent(ratioMax), new Integer(success)));
        }

        logger.info("Start optimization");
        start = System.currentTimeMillis();
        while (success < OPTIMIZE_SUCCESS) {

            if (System.currentTimeMillis() - start > OPTIMIZE_MAX_TIME) {
                throw new OptimizeException("No portfolio found (time exceed)");
            }

            int[] keys = randomizer.keys(5, size);
            double[] baskerReturns = BasketUtil.calculateReturns(returns, keys);

            double basketMean = StatUtil.mean(baskerReturns);
            double basketStdev = StatUtil.stdev(baskerReturns);
            double basketSharp = StatUtil.sharp(basketMean, riskless,
                    basketStdev);
            double basketRatio = StatUtil.ratio(indiceSharp, basketSharp);
            double basketBeta = StatUtil.beta(indiceReturns, baskerReturns);

            boolean weightOk = true;
            // check weights after 10% of success
            if (success > (OPTIMIZE_SUCCESS / 10)) {
                for (int i = 0; weightOk && i < keys.length; i++) {
                    double weight = points[keys[i]] + 1;
                    weight /= (pointTotal + keys.length);
                    weightOk = weight < maxWeight;
                }
            }

            if (weightOk && basketBeta >= beta - BETA_DELTA
                    && basketBeta <= beta + BETA_DELTA
                    && basketRatio >= ratioMax) {

                for (int i = 0; i < keys.length; i++) {
                    points[keys[i]] += 1;
                }
                pointTotal += keys.length;

                success++;
                unsuccess = 0;
                if (logger.isDebugEnabled()) {
                    logger
                            .debug(MessageUtil.msg("Ratio <{0}> Success <{1}>",
                                    FormatUtil.percent(ratioMax), new Integer(
                                            success)));
                }
            } else {
                unsuccess++;
                if (unsuccess == OPTIMIZE_UNSUCCESS) {
                    unsuccess = 0;
                    ratioMax -= 0.005;
                    if (logger.isDebugEnabled()) {
                        logger.debug(MessageUtil.msg(
                                "Ratio <{0}> Success <{1}>", FormatUtil
                                        .percent(ratioMax),
                                new Integer(success)));
                    }
                }
            }

        }
        logger.info("Finish optimization");

        List positions = new ArrayList();
        iterator = returns.keySet().iterator();
        for (int i = 0; i < points.length; i++) {
            Stock stock = (Stock) iterator.next();
            if (points[i] != 0) {
                double weight = (double) points[i] / (double) pointTotal;
                Position position = new Position();
                position.setStock(stock);
                position.setWeight(new Double(weight));
                positions.add(position);
            }
        }
        Collections.sort(positions);

        if (logger.isDebugEnabled()) {
            iterator = positions.iterator();
            while (iterator.hasNext()) {
                Position position = (Position) iterator.next();
                logger.debug(MessageUtil.msg("Stock <{0}> Weight <{1}>",
                        position.getStock().getSymbol(), FormatUtil
                                .percent(position.getWeight().doubleValue())));
            }
        }

        size = portfolio.getSize().intValue();
        if (positions.size() > size) {

            Position position = (Position) positions.get(size - 1);
            double weight = position.getWeight().doubleValue();

            iterator = positions.iterator();
            while (iterator.hasNext()) {
                position = (Position) iterator.next();
                if (position.getWeight().doubleValue() < weight) {
                    iterator.remove();
                }
            }

            double total = 0.0;
            iterator = positions.iterator();
            while (iterator.hasNext()) {
                position = (Position) iterator.next();
                total += position.getWeight().doubleValue();
            }

            iterator = positions.iterator();
            while (iterator.hasNext()) {
                position = (Position) iterator.next();
                weight = position.getWeight().doubleValue() / total;
                position.setWeight(new Double(weight));
            }

        }

        if (logger.isDebugEnabled()) {
            iterator = positions.iterator();
            while (iterator.hasNext()) {
                Position position = (Position) iterator.next();
                logger.debug(MessageUtil.msg("Stock <{0}> Weight <{1}>",
                        position.getStock().getSymbol(), FormatUtil
                                .percent(position.getWeight().doubleValue())));
            }
        }

        indiceReturns = null;
        returns = null;

        portfolio.setFromDate(fromDate);
        portfolio.setToDate(toDate);

        return positions;

    }

}
