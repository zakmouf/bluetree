package com.bluetree.util;

import org.apache.commons.collections.map.LinkedMap;

public class BasketUtil {

    public static double[] calculateReturns(LinkedMap returns, int[] keys) {
        double[] stockReturns = (double[]) returns.getValue(keys[0]);
        double[] basketReturns = new double[stockReturns.length];
        for (int j = 0; j < basketReturns.length; j++) {
            basketReturns[j] = stockReturns[j];
        }
        for (int i = 1; i < keys.length; i++) {
            stockReturns = (double[]) returns.getValue(keys[0]);
            for (int j = 0; j < basketReturns.length; j++) {
                basketReturns[j] = basketReturns[j] + stockReturns[j];
            }
        }
        for (int j = 0; j < basketReturns.length; j++) {
            basketReturns[j] = basketReturns[j] / keys.length;
        }
        return basketReturns;
    }

}