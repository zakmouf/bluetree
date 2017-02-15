package com.bluetree.web.admin.stock;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.manager.StockManager;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;

public class StockPriceController extends SimpleFormController {

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
        StockPriceForm stockPriceForm = (StockPriceForm) form;
        String[] lines = StringUtils.split(stockPriceForm.getText(), "\r\n");
        List prices = new ArrayList();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            String[] tokens = StringUtils.split(line, ",");
            String date = tokens[0].trim();
            String value = tokens[1].trim();
            Price price = new Price();
            price.setDate(FormatUtil.parseDate(date));
            price.setValue(Double.valueOf(value));
            prices.add(price);
        }
        Long stockId = Long.valueOf(request.getParameter("stock"));
        Stock stock = stockManager.findStock(stockId);
        logger.info(MessageUtil.msg("stock clear <{0}>", stock));
        stockManager.clearPrices(stock);
        logger.info(MessageUtil.msg("stock prices <{0}>", stock));
        stockManager.addPrices(stock, prices);
        ModelAndView mav = new ModelAndView(getSuccessView() + stock.getId());
        return mav;
    }

}
