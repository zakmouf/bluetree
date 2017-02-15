package com.bluetree.web.admin.stock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Stock;
import com.bluetree.manager.StockManager;
import com.bluetree.util.MessageUtil;

public class StockClearController extends AbstractController {

    private StockManager stockManager;

    public StockManager getStockManager() {
        return stockManager;
    }

    public void setStockManager(StockManager stockManager) {
        this.stockManager = stockManager;
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
        Long stockId = Long.valueOf(request.getParameter("stock"));
        Stock stock = stockManager.findStock(stockId);
        logger.info(MessageUtil.msg("stock clear <{0}>", stock));
        stockManager.clearPrices(stock);
        return new ModelAndView(getViewName());
    }

}
