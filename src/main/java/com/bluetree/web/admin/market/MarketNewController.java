package com.bluetree.web.admin.market;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Stock;
import com.bluetree.manager.MarketManager;
import com.bluetree.manager.StockManager;
import com.bluetree.util.MessageUtil;

public class MarketNewController extends SimpleFormController {

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

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        MarketForm marketForm = new MarketForm();
        List stocks = stockManager.getStocks();
        marketForm.setStocks(stocks);
        return marketForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        MarketForm marketForm = (MarketForm) form;
        String name = marketForm.getName().trim();
        String riskless = marketForm.getRiskless().trim();
        Long indiceId = Long.valueOf(marketForm.getIndiceId().trim());
        Market market = marketManager.findMarket(name);
        if (market == null) {
            market = new Market();
            market.setName(name);
            market.setRiskless(Double.valueOf(riskless));
            logger.info(MessageUtil.msg("new market <{0}>", market));
            marketManager.insertMarket(market);
            Stock indice = stockManager.findStock(indiceId);
            logger.info(MessageUtil.msg("market <{0}> indice <{1}>", market, indice));
            marketManager.updateIndice(market, indice);
        }
        ModelAndView mav = new ModelAndView(getSuccessView() + market.getId());
        return mav;
    }

}
