package com.bluetree.update.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.manager.StockManager;
import com.bluetree.update.UpdateException;
import com.bluetree.update.Updater;
import com.bluetree.util.DateUtil;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;

public class YahooUpdater implements Updater {

    private Logger logger = Logger.getLogger(YahooUpdater.class);

    private final String URL_PATTERN = "http://ichart.finance.yahoo.com/table.csv?s={0}&a={1}&b={2}&c={3}&d={4}&e={5}&f={6}&g=d&ignore=.csv";

    private final String FIELD_SEPARATOR = ",";

    private final String DATE_PATTERN = "yyyy-MM-dd";

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
            DATE_PATTERN, Locale.US);

    private StockManager stockManager;

    public StockManager getStockManager() {
        return stockManager;
    }

    public void setStockManager(StockManager stockManager) {
        this.stockManager = stockManager;
    }

    private Date parseDate(String date) throws UpdateException {
        try {
            return dateFormatter.parse(date);
        } catch (ParseException e) {
            throw new UpdateException(e);
        }
    }

    private Double parseValue(String value) throws UpdateException {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new UpdateException(e);
        }
    }

    private String getLink(Stock stock, Date fromDate, Date toDate) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fromDate);
        int fromYear = calendar.get(Calendar.YEAR);
        int fromMonth = calendar.get(Calendar.MONTH);
        int fromDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(toDate);
        int toYear = calendar.get(Calendar.YEAR);
        int toMonth = calendar.get(Calendar.MONTH);
        int toDay = calendar.get(Calendar.DAY_OF_MONTH);

        Object[] arguments = new Object[7];
        arguments[0] = stock.getSymbol();
        arguments[1] = new Integer(fromMonth).toString();
        arguments[2] = new Integer(fromDay).toString();
        arguments[3] = new Integer(fromYear).toString();
        arguments[4] = new Integer(toMonth).toString();
        arguments[5] = new Integer(toDay).toString();
        arguments[6] = new Integer(toYear).toString();

        return MessageFormat.format(URL_PATTERN, arguments);
    }

    private String pumpLink(String link) throws UpdateException {
        try {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int read = inputStream.read(bytes);
            while (read != -1) {
                baos.write(bytes, 0, read);
                read = inputStream.read(bytes);
            }
            inputStream.close();
            bytes = baos.toByteArray();
            String page = new String(bytes);
            return page;
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            throw new UpdateException(e);
        }
    }

    private List parsePage(String page) throws UpdateException {
        List prices = new ArrayList();
        StringTokenizer lines = new StringTokenizer(page, "\r\n");
        if (lines.hasMoreTokens())
            lines.nextToken();
        while (lines.hasMoreTokens()) {
            StringTokenizer tokens = new StringTokenizer(lines.nextToken(),
                    FIELD_SEPARATOR);
            String dateStr = tokens.nextToken(); // date
            tokens.nextToken(); // open
            tokens.nextToken(); // high
            tokens.nextToken(); // low
            tokens.nextToken(); // close
            tokens.nextToken(); // volume
            String valueStr = tokens.nextToken(); // adj.close
            Date date = parseDate(dateStr);
            Double value = parseValue(valueStr);
            Price price = new Price();
            price.setDate(date);
            price.setValue(value);
            prices.add(price);
        }
        return prices;
    }

    private void updateStock(Stock stock, Date fromDate, Date toDate)
            throws UpdateException {
        String link = getLink(stock, fromDate, toDate);
        String page = pumpLink(link);
        List prices = parsePage(page);
        if (logger.isDebugEnabled()) {
            logger.debug(MessageUtil.msg("{0} : {1} -> {2} : {3}", stock,
                    FormatUtil.formatDate(fromDate), FormatUtil
                            .formatDate(toDate), new Integer(prices.size())));
        }
        stockManager.addPrices(stock, prices);
    }

    private void updateStock(Stock stock, Date startDate, Date endDate,
            int increment) throws UpdateException {
        Calendar calendar = Calendar.getInstance();
        Date fromDate = startDate;
        calendar.setTime(fromDate);
        while (fromDate.compareTo(endDate) <= 0) {
            calendar.add(Calendar.DAY_OF_MONTH, increment);
            Date toDate = calendar.getTime();
            if (toDate.compareTo(endDate) > 0) {
                toDate = endDate;
            }
            updateStock(stock, fromDate, toDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            fromDate = calendar.getTime();
        }
    }

    public void updateStock(Stock stock, Date startDate, Integer increment)
            throws UpdateException {
        Date lastDate = stockManager.getLastDate(stock);
        lastDate = lastDate == null ? startDate : DateUtil.addDays(lastDate, 1);
        Date endDate = DateUtil.today();
        updateStock(stock, lastDate, endDate, increment.intValue());
    }

}
