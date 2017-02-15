package com.bluetree.web.admin.market;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Stock;
import com.bluetree.manager.MarketManager;
import com.bluetree.manager.StockManager;
import com.bluetree.util.MessageUtil;

public class MarketStockController extends SimpleFormController {

    private MarketManager marketManager;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }

    private StockManager stockManager;

    public StockManager getStockManager() {
        return stockManager;
    }

    public void setStockManager(StockManager stockManager) {
        this.stockManager = stockManager;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        Long marketId = Long.valueOf(request.getParameter("market"));
        Market market = marketManager.findMarket(marketId);

        MarketStockForm marketStockForm = (MarketStockForm) form;

        Map symbols = new HashMap();
        String[] lines = StringUtils.split(marketStockForm.getText(), "\r\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            String[] tokens = StringUtils.split(line, ",");
            String symbol = tokens[0].trim().toUpperCase();
            String name = tokens[1].trim();
            symbols.put(symbol, name);
        }

        List stocks = stockManager.getStocks();

        Map existingStocks = new HashMap();
        Iterator iterator = stocks.iterator();
        while (iterator.hasNext()) {
            Stock stock = (Stock) iterator.next();
            existingStocks.put(stock.getSymbol(), stock);
        }

        stocks.clear();

        iterator = symbols.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            String symbol = (String) entry.getKey();
            String name = (String) entry.getValue();
            Stock stock = (Stock) existingStocks.get(symbol);
            if (stock != null) {
                if (!name.equals(stock.getName())) {
                    stock.setName(name);
                    logger.info(MessageUtil.msg("update stock <{0}>", stock));
                    stockManager.updateStock(stock);
                }
            } else {
                stock = new Stock();
                stock.setSymbol(symbol);
                stock.setName(name);
                logger.info(MessageUtil.msg("new stock <{0}>", stock));
                stockManager.insertStock(stock);
            }
            stocks.add(stock);
        }

        logger.info(MessageUtil.msg("market stocks <{0}>", market));
        marketManager.updateStocks(market, stocks);

        ModelAndView mav = new ModelAndView(getSuccessView() + market.getId());
        return mav;
    }

}
