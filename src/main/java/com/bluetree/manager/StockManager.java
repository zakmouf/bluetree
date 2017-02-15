package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;

public class StockManager extends AbstractManager {

    public Stock findStock(Long id) {
        //
        String sql = "select * from stocks where stock_id=?";
        Object[] params = { id };
        int[] types = { Types.BIGINT };
        //
        List stocks = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractStock(resultSet);
                    }
                });
        //
        return stocks.isEmpty() ? null : (Stock) stocks.get(0);
    }

    public Stock findStock(String symbol) {
        //
        String sql = "select * from stocks where stock_symbol=?";
        Object[] params = { symbol };
        int[] types = { Types.VARCHAR };
        //
        List stocks = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractStock(resultSet);
                    }
                });
        //
        return stocks.isEmpty() ? null : (Stock) stocks.get(0);
    }

    public List getStocks() {
        //
        String sql = "select * from stocks";
        //
        List stocks = getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException {
                return extractStock(resultSet);
            }
        });
        //     
        Collections.sort(stocks);
        return stocks;
    }

    public void insertStock(Stock stock) {
        //
        String sql = "insert into stocks values (null, ?, ?)";
        Object[] params = { stock.getSymbol(), stock.getName() };
        int[] types = { Types.VARCHAR, Types.VARCHAR };
        //
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                newPreparedStatementCreator(sql, params, types), keyHolder);
        stock.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void deleteStock(Stock stock) {
        //
        String sql = "select count(*) from market_lnk_indice where mli_indice_id=?";
        Object[] params = { stock.getId() };
        int[] types = { Types.BIGINT };
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }
        //
        sql = "select count(*) from market_lnk_stock where mls_stock_id=?";
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }
        //
        sql = "select count(*) from portfolio_lnk_stock where pls_stock_id=?";
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }
        //
        sql = "delete from stocks where stock_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from prices where price_stock_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public void updateStock(Stock stock) {
        //
        String sql = "update stocks set stock_name=? where stock_id=?";
        Object[] params = { stock.getName(), stock.getId() };
        int[] types = { Types.VARCHAR, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public Date getLastDate(Stock stock) {
        //
        String sql = "select max(price_date) as last_date from prices where price_stock_id=?";
        Object[] params = { stock.getId() };
        int[] types = { Types.BIGINT };
        //
        List dates = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return ManagerUtil.extractDate(resultSet, "last_date");
                    }
                });
        //
        return (Date) dates.get(0);
    }

    public List getPrices(Stock stock) {
        //
        String sql = "select * from prices where price_stock_id=?";
        Object[] params = { stock.getId() };
        int[] types = { Types.BIGINT };
        //
        List prices = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPrice(resultSet);
                    }
                });
        //
        Collections.sort(prices);
        return prices;
    }

    public List getPrices(Stock stock, Date fromDate, Date toDate) {
        //
        String sql = "select * from prices where price_stock_id=? and price_date>=? and price_date<=?";
        Object[] params = { stock.getId(), fromDate, toDate };
        int[] types = { Types.BIGINT, Types.DATE, Types.DATE };
        //
        List prices = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPrice(resultSet);
                    }
                });
        //
        Collections.sort(prices);
        return prices;
    }

    public List getPricesInclusive(Stock stock, Date fromDate, Date toDate) {
        //
        String sql = "select * from prices where price_stock_id=? "
                + "and price_date>=(select max(price_date) from prices where price_stock_id=? and price_date<=?) "
                + "and price_date<=(select min(price_date) from prices where price_stock_id=? and price_date>=?)";
        Object[] params = { stock.getId(), stock.getId(), fromDate,
                stock.getId(), toDate };
        int[] types = { Types.BIGINT, Types.BIGINT, Types.DATE, Types.BIGINT,
                Types.DATE };
        //
        List prices = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPrice(resultSet);
                    }
                });
        //
        Collections.sort(prices);
        return prices;
    }

    public List getPricesInclusive(Stock stock, Date fromDate) {
        //
        String sql = "select * from prices where price_stock_id=? "
                + "and price_date>=(select max(price_date) from prices where price_stock_id=? and price_date<=?) ";
        Object[] params = { stock.getId(), stock.getId(), fromDate };
        int[] types = { Types.BIGINT, Types.BIGINT, Types.DATE };
        //
        List prices = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPrice(resultSet);
                    }
                });
        //
        Collections.sort(prices);
        return prices;
    }

    public void addPrices(Stock stock, List prices) {
        //
        Iterator iterator = prices.iterator();
        while (iterator.hasNext()) {
            Price price = (Price) iterator.next();
            //
            String sql = "insert into prices values (?, ?, ?)";
            Object[] params = { stock.getId(), price.getDate(),
                    price.getValue() };
            int[] types = { Types.BIGINT, Types.DATE, Types.DOUBLE };
            //
            getJdbcTemplate().update(sql, params, types);
        }
    }

    public void clearPrices(Stock stock) {
        //
        String sql = "delete from prices where price_stock_id=?";
        Object[] params = { stock.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public static Price extractPrice(ResultSet resultSet) throws SQLException {
        Price price = new Price();
        price.setDate(ManagerUtil.extractDate(resultSet, "price_date"));
        price.setValue(ManagerUtil.extractDouble(resultSet, "price_value"));
        return price;
    }

    public static Stock extractStock(ResultSet resultSet) throws SQLException {
        Stock stock = new Stock();
        stock.setId(ManagerUtil.extractLong(resultSet, "stock_id"));
        stock.setSymbol(ManagerUtil.extractString(resultSet, "stock_symbol"));
        stock.setName(ManagerUtil.extractString(resultSet, "stock_name"));
        return stock;
    }

}