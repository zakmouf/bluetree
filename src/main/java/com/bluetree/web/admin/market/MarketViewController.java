package com.bluetree.web.admin.market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Stock;
import com.bluetree.manager.MarketManager;

public class MarketViewController extends AbstractController {

    private MarketManager marketManager;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }

    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long marketId = Long.valueOf(request.getParameter("market"));
        Market market = marketManager.findMarket(marketId);
        Stock indice = marketManager.getIndice(market);
        List profiles = marketManager.getProfiles(market);
        List stocks = marketManager.getStocks(market);
        Map model = new HashMap();
        model.put("market", market);
        model.put("indice", indice);
        model.put("profiles", profiles);
        model.put("stocks", stocks);
        return new ModelAndView(getViewName(), model);
    }

}
