package com.bluetree.web.admin.stock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Stock;
import com.bluetree.manager.StockManager;
import com.bluetree.util.MessageUtil;

public class StockEditController extends SimpleFormController {

    private StockManager stockManager;

    public StockManager getStockManager() {
        return stockManager;
    }

    public void setStockManager(StockManager stockManager) {
        this.stockManager = stockManager;
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        Long stockId = Long.valueOf(request.getParameter("stock"));
        Stock stock = stockManager.findStock(stockId);
        StockForm stockForm = new StockForm();
        stockForm.setSymbol(stock.getSymbol());
        stockForm.setName(stock.getName());
        return stockForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        Long stockId = Long.valueOf(request.getParameter("stock"));
        Stock stock = stockManager.findStock(stockId);
        StockForm stockForm = (StockForm) form;
        String name = stockForm.getName().trim();
        stock.setName(name);
        logger.info(MessageUtil.msg("update stock <{0}>", stock));
        stockManager.updateStock(stock);
        ModelAndView mav = new ModelAndView(getSuccessView() + stock.getId());
        return mav;
    }

}
