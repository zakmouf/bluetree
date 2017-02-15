package com.bluetree.domain;

import java.io.Serializable;
import java.util.Date;

import com.bluetree.util.MessageUtil;

public class Price implements Serializable, Comparable {

    private Date date;

    private Double value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String toString() {
        return MessageUtil.msg("[{0},{1}]", date, value);
    }

    public int compareTo(Object other) {
        return date.compareTo(((Price) other).getDate());
    }

    public int hashCode() {
        return date.hashCode();
    }

}