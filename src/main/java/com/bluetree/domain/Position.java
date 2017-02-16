package com.bluetree.domain;

import java.io.Serializable;

import com.bluetree.util.MessageUtil;

public class Position implements Serializable, Comparable<Position> {

    private static final long serialVersionUID = 1L;

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

    @Override
    public int compareTo(Position other) {
	int cmp = -weight.compareTo(other.getWeight());
	if (cmp == 0) {
	    cmp = stock.compareTo(other.getStock());
	}
	return cmp;
    }

    @Override
    public String toString() {
	return MessageUtil.msg("[{0},{1}]", stock, weight);
    }

}
