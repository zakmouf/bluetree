package com.bluetree.domain;

import java.util.Date;

import com.bluetree.util.MessageUtil;

public class Portfolio extends Persistable implements Comparable<Portfolio> {

    private static final long serialVersionUID = 1L;

    private String name;

    private Date fromDate;

    private Date toDate;

    private Double beta;

    private Integer size;

    private Boolean executed;

    private String error;

    public Double getBeta() {
	return beta;
    }

    public void setBeta(Double beta) {
	this.beta = beta;
    }

    public String getError() {
	return error;
    }

    public void setError(String error) {
	this.error = error;
    }

    public Boolean getExecuted() {
	return executed;
    }

    public void setExecuted(Boolean executed) {
	this.executed = executed;
    }

    public Date getFromDate() {
	return fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getSize() {
	return size;
    }

    public void setSize(Integer size) {
	this.size = size;
    }

    public Date getToDate() {
	return toDate;
    }

    public void setToDate(Date toDate) {
	this.toDate = toDate;
    }

    @Override
    public int compareTo(Portfolio other) {
	return -getId().compareTo(other.getId());
    }

    @Override
    public String toString() {
	return MessageUtil.msg("[{0},{1}]", getId(), name);
    }

}
