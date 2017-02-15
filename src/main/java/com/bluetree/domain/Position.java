package com.bluetree.domain;

import java.io.Serializable;

import com.bluetree.util.MessageUtil;

public class Position implements Serializable, Comparable {

    private Stock stock;

    private Double weight;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }   
    
    public String toString() {
        return MessageUtil.msg("[{0},{1}]", stock, weight);
    }

    public int compareTo(Object other) {
        int cmp = -weight.compareTo(((Position) other).getWeight());
        if (cmp == 0) {
            cmp = stock.compareTo(((Position) other).getStock());
        }
        return cmp;
    }

}
