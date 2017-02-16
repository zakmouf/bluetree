package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class Stock extends Persistable implements Comparable<Stock> {

    private static final long serialVersionUID = 1L;

    private String symbol;

    private String name;

    public String getSymbol() {
	return symbol;
    }

    public void setSymbol(String symbol) {
	this.symbol = symbol;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int compareTo(Stock other) {
	return symbol.compareTo(other.getSymbol());
    }

    @Override
    public String toString() {
	return MessageUtil.msg("[{0},{1},{2}]", getId(), symbol, name);
    }

}