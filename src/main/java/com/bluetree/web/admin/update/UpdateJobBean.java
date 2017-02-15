package com.bluetree.web.admin.update;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bluetree.domain.Market;
import com.bluetree.domain.Stock;
import com.bluetree.manager.MarketManager;
import com.bluetree.update.UpdateException;
import com.bluetree.update.Updater;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;

public class UpdateJobBean extends QuartzJobBean {

    private static final Logger logger = Logger.getLogger(UpdateJobBean.class);

    private MarketManager marketManager;

    private Updater updater;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }

    public Updater getUpdater() {
        return updater;
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

        try {

            JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            Long marketId = (Long) dataMap.get("marketId");
            Date startDate = (Date) dataMap.get("startDate");
            Integer increment = (Integer) dataMap.get("increment");

            Market market = marketManager.findMarket(marketId);
            Stock indice = marketManager.getIndice(market);
            List stocks = new ArrayList();
            stocks.add(indice);
            stocks.addAll(marketManager.getStocks(market));

            Iterator iterator = stocks.iterator();
            while (iterator.hasNext()) {
                Stock stock = (Stock) iterator.next();
                logger.info(MessageUtil.msg(
                        "Update <{0}> from <{1}> with <{2}>", stock, FormatUtil
                                .formatDate(startDate), increment));
                updater.updateStock(stock, startDate, increment);
            }

        } catch (UpdateException e) {
            throw new JobExecutionException(e);
        }
    }

}
