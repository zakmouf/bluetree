package com.bluetree.web.admin.stock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Stock;
import com.bluetree.manager.StockManager;
import com.bluetree.util.MessageUtil;

public class StockNewController extends SimpleFormController {

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
        StockForm stockForm = (StockForm) form;
        String symbol = stockForm.getSymbol().trim().toUpperCase();
        Stock stock = stockManager.findStock(symbol);
        if (stock == null) {
            stock = new Stock();
            stock.setSymbol(symbol);
            stock.setName(stockForm.getName().trim());
            logger.info(MessageUtil.msg("new stock <{0}>", stock));
            stockManager.insertStock(stock);
        }
        ModelAndView mav = new ModelAndView(getSuccessView() + stock.getId());
        return mav;
    }

}
