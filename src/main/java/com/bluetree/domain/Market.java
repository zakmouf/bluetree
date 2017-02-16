package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class Market extends Persistable implements Comparable<Market> {

    private static final long serialVersionUID = 1L;

    private String name;

    private Double riskless;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Double getRiskless() {
	return riskless;
    }

    public void setRiskless(Double riskless) {
	this.riskless = riskless;
    }

    @Override
    public int compareTo(Market other) {
	return name.compareTo(other.getName());
    }

    @Override
    public String toString() {
	return MessageUtil.msg("[{0},{1},{2}]", getId(), name, riskless);
    }

}
