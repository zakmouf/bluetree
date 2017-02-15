package com.bluetree.web.admin.stock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.manager.StockManager;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.PriceUtil;

public class StockViewController extends SimpleFormController {

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
        List prices = stockManager.getPrices(stock);
        StockViewForm stockViewForm = new StockViewForm();
        stockViewForm.setViewStock(stock);
        stockViewForm.setCount(new Integer(prices.size()));
        if (!prices.isEmpty()) {
            stockViewForm.setFirstDate(PriceUtil.firstDate(prices));
            stockViewForm.setLastDate(PriceUtil.lastDate(prices));
            stockViewForm.setFromDate(FormatUtil.formatDate(stockViewForm
                    .getFirstDate()));
            stockViewForm.setToDate(FormatUtil.formatDate(stockViewForm
                    .getLastDate()));
        }
        JFreeChart chart = generateChart(stock, prices);
        request.getSession().setAttribute("stock.view", chart);
        return stockViewForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        StockViewForm stockViewForm = (StockViewForm) form;
        Date fromDate = FormatUtil.parseDate(stockViewForm.getFromDate());
        Date toDate = FormatUtil.parseDate(stockViewForm.getToDate());
        Stock stock = stockViewForm.getViewStock();
        List prices = stockManager.getPrices(stock, fromDate, toDate);
        JFreeChart chart = generateChart(stock, prices);
        request.getSession().setAttribute("stock.view", chart);
        return showForm(request, errors, getFormView());
    }

    protected JFreeChart generateChart(Stock stock, List prices) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries(stock.getSymbol(), Day.class);
        for (Iterator it = prices.iterator(); it.hasNext();) {
            Price price = (Price) it.next();
            series.add(new Day(price.getDate()), price.getValue());
        }
        dataset.addSeries(series);

        DateAxis domainAxis = new DateAxis();
        domainAxis.setTickMarksVisible(false);
        domainAxis.setAxisLineVisible(false);
        domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 11));

        NumberAxis rangeAxis = new NumberAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 11));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true,
                false);
        renderer.setSeriesPaint(0, new Color(51, 0, 204));

        XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineStroke(null);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlineStroke(new BasicStroke(0.5f));
        plot.setRangeGridlinePaint(Color.DARK_GRAY);

        JFreeChart chart = new JFreeChart(null, null, plot, false);
        chart.setBackgroundPaint(Color.white);
        chart.setAntiAlias(true);
        chart.setBorderVisible(false);

        return chart;
    }

}
