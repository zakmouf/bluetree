package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class Market extends Persistable {

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

    public String toString() {
        return MessageUtil.msg("[{0},{1},{2}]", getId(), name, riskless);
    }

    public int compareTo(Object other) {
        return name.compareTo(((Market) other).getName());
    }

    public int hashCode() {
        return name.hashCode();
    }

}
