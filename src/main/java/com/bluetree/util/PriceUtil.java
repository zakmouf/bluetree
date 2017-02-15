package com.bluetree.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bluetree.domain.Price;

public abstract class PriceUtil {

    public static int matchPrices(List indicePrices, List stockPrices) {
        List matchPrices = new ArrayList();
        matchPrices.clear();
        int size = indicePrices.size();
        int i = 0;
        int j = 0;
        int diff = 0;
        while (i < size) {
            Price indicePrice = (Price) indicePrices.get(i);
            Price stockPrice = (Price) stockPrices.get(j);
            int cmp = indicePrice.getDate().compareTo(stockPrice.getDate());
            if (cmp == 0) {
                matchPrices.add(stockPrice);
                i++;
                j++;
            } else if (cmp < 0) {
                Price before = (Price) stockPrices.get(j - 1);
                double value = before.getValue().doubleValue()
                        + ((stockPrice.getValue().doubleValue() - before
                                .getValue().doubleValue()) / DateUtil
                                .daysBetween(stockPrice.getDate(), before
                                        .getDate()))
                        * DateUtil.daysBetween(indicePrice.getDate(), before
                                .getDate());
                Price price = new Price();
                price.setDate(indicePrice.getDate());
                price.setValue(new Double(value));
                matchPrices.add(price);
                i++;
                diff++;
            } else {
                j++;
            }
        }
        stockPrices.clear();
        stockPrices.addAll(matchPrices);
        return diff;
    }

    public static Date firstDate(List prices) {
        return ((Price) prices.get(0)).getDate();
    }

    public static Date lastDate(List prices) {
        return ((Price) prices.get(prices.size() - 1)).getDate();
    }

    public static double[] calculateReturns(List prices) {
        double[] returns = new double[prices.size() - 1];
        Iterator iterator = prices.iterator();
        Price oldPrice = (Price) iterator.next();
        for (int i = 0; i < returns.length; i++) {
            Price price = (Price) iterator.next();
            returns[i] = (price.getValue().doubleValue() / oldPrice.getValue()
                    .doubleValue()) - 1.0;
            oldPrice = price;
        }
        return returns;
    }

}
