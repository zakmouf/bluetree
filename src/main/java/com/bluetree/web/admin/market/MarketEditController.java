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

public class MarketEditController extends SimpleFormController {

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
        Long marketId = Long.valueOf(request.getParameter("market"));
        Market market = marketManager.findMarket(marketId);
        MarketForm marketForm = new MarketForm();
        marketForm.setName(market.getName());
        marketForm.setRiskless(market.getRiskless().toString());
        Stock indice = marketManager.getIndice(market);
        marketForm.setIndiceId(indice.getId().toString());
        List stocks = stockManager.getStocks();
        marketForm.setStocks(stocks);
        return marketForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        Long marketId = Long.valueOf(request.getParameter("market"));
        MarketForm marketForm = (MarketForm) form;
        String name = marketForm.getName().trim();
        Market market = marketManager.findMarket(name);
        if (market != null) {
            if (!market.getId().equals(marketId)) {
                errors.reject("Name already used");
                return showForm(request, errors, getFormView());
            }
        }
        String riskless = marketForm.getRiskless().trim();
        Long indiceId = Long.valueOf(marketForm.getIndiceId().trim());
        market = marketManager.findMarket(marketId);
        market.setName(name);
        market.setRiskless(Double.valueOf(riskless));
        logger.info(MessageUtil.msg("update market <{0}>", market));
        marketManager.updateMarket(market);
        Stock indice = stockManager.findStock(indiceId);
        logger.info(MessageUtil.msg("market <{0}> indice <{1}>", market, indice));
        marketManager.updateIndice(market, indice);
        ModelAndView mav = new ModelAndView(getSuccessView() + market.getId());
        return mav;
    }

}
