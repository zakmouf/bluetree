package com.bluetree.web.admin.stock;

import java.util.Date;

import com.bluetree.domain.Stock;

public class StockViewForm {

    private Stock viewStock;

    public Stock getViewStock() {
        return viewStock;
    }

    public void setViewStock(Stock viewStock) {
        this.viewStock = viewStock;
    }

    private Date firstDate;

    private Date lastDate;

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    private String fromDate;

    private String toDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
