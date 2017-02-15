package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class Stock extends Persistable {

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

    public String toString() {
        return MessageUtil.msg("[{0},{1},{2}]", getId(), symbol, name);
    }

    public int compareTo(Object other) {
        return symbol.compareTo(((Stock) other).getSymbol());
    }

    public int hashCode() {
        return symbol.hashCode();
    }

}