package com.bluetree.web.admin.market;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Market;
import com.bluetree.manager.MarketManager;
import com.bluetree.util.MessageUtil;

public class MarketDeleteController extends AbstractController {

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
        logger.info(MessageUtil.msg("delete market <{0}>", market));
        marketManager.deleteMarket(market);
        return new ModelAndView(getViewName());
    }

}
