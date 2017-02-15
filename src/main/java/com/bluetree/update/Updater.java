package com.bluetree.update;

import java.util.Date;

import com.bluetree.domain.Stock;

public interface Updater {

    void updateStock(Stock stock, Date startDate, Integer increment)
            throws UpdateException;

}
