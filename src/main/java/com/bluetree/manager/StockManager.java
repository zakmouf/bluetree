package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Price;
import com.bluetree.domain.Stock;
import com.bluetree.manager.mapper.PriceRowMapper;
import com.bluetree.manager.mapper.StockRowMapper;

public class StockManager extends AbstractManager {

    public Stock findStock(Long id) {
	String sql = "select * from stocks where stock_id=?";
	Object[] params = { id };
	int[] types = { Types.BIGINT };
	List<Stock> stocks = jdbcTemplate.query(sql, params, types, new StockRowMapper());
	return stocks.isEmpty() ? null : stocks.get(0);
    }

    public Stock findStock(String symbol) {
	String sql = "select * from stocks where stock_symbol=?";
	Object[] params = { symbol };
	int[] types = { Types.VARCHAR };
	List<Stock> stocks = jdbcTemplate.query(sql, params, types, new StockRowMapper());
	return stocks.isEmpty() ? null : stocks.get(0);
    }

    public List<Stock> getStocks() {
	String sql = "select * from stocks";
	List<Stock> stocks = jdbcTemplate.query(sql, new StockRowMapper());
	Collections.sort(stocks);
	return stocks;
    }

    public void insertStock(Stock stock) {
	String sql = "insert into stocks values (null, ?, ?)";
	Object[] params = { stock.getSymbol(), stock.getName() };
	int[] types = { Types.VARCHAR, Types.VARCHAR };
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(newPreparedStatementCreator(sql, params, types), keyHolder);
	stock.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void deleteStock(Stock stock) {
	//
	String sql = "select count(*) from market_lnk_indice where mli_indice_id=?";
	Object[] params = { stock.getId() };
	int[] types = { Types.BIGINT };
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "select count(*) from market_lnk_stock where mls_stock_id=?";
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "select count(*) from portfolio_lnk_stock where pls_stock_id=?";
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "delete from stocks where stock_id=?";
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from prices where price_stock_id=?";
	jdbcTemplate.update(sql, params, types);
    }

    public void updateStock(Stock stock) {
	String sql = "update stocks set stock_name=? where stock_id=?";
	Object[] params = { stock.getName(), stock.getId() };
	int[] types = { Types.VARCHAR, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public Date getLastDate(Stock stock) {
	String sql = "select max(price_date) as last_date from prices where price_stock_id=?";
	Object[] params = { stock.getId() };
	int[] types = { Types.BIGINT };
	List<Date> dates = jdbcTemplate.query(sql, params, types, new RowMapper<Date>() {
	    @Override
	    public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
		java.sql.Date date = rs.getDate("last_date");
		return rs.wasNull() ? null : new Date(date.getTime());
	    }
	});
	return dates.get(0);
    }

    public List<Price> getPrices(Stock stock) {
	String sql = "select * from prices where price_stock_id=?";
	Object[] params = { stock.getId() };
	int[] types = { Types.BIGINT };
	List<Price> prices = jdbcTemplate.query(sql, params, types, new PriceRowMapper());
	Collections.sort(prices);
	return prices;
    }

    public List<Price> getPrices(Stock stock, Date fromDate, Date toDate) {
	String sql = "select * from prices where price_stock_id=? and price_date>=? and price_date<=?";
	Object[] params = { stock.getId(), fromDate, toDate };
	int[] types = { Types.BIGINT, Types.DATE, Types.DATE };
	List<Price> prices = jdbcTemplate.query(sql, params, types, new PriceRowMapper());
	Collections.sort(prices);
	return prices;
    }

    public List<Price> getPricesInclusive(Stock stock, Date fromDate, Date toDate) {
	String sql = "select * from prices where price_stock_id=? "
		+ "and price_date>=(select max(price_date) from prices where price_stock_id=? and price_date<=?) "
		+ "and price_date<=(select min(price_date) from prices where price_stock_id=? and price_date>=?)";
	Object[] params = { stock.getId(), stock.getId(), fromDate, stock.getId(), toDate };
	int[] types = { Types.BIGINT, Types.BIGINT, Types.DATE, Types.BIGINT, Types.DATE };
	List<Price> prices = jdbcTemplate.query(sql, params, types, new PriceRowMapper());
	Collections.sort(prices);
	return prices;
    }

    public List<Price> getPricesInclusive(Stock stock, Date fromDate) {
	String sql = "select * from prices where price_stock_id=? "
		+ "and price_date>=(select max(price_date) from prices where price_stock_id=? and price_date<=?) ";
	Object[] params = { stock.getId(), stock.getId(), fromDate };
	int[] types = { Types.BIGINT, Types.BIGINT, Types.DATE };
	List<Price> prices = jdbcTemplate.query(sql, params, types, new PriceRowMapper());
	Collections.sort(prices);
	return prices;
    }

    public void addPrices(Stock stock, List<Price> prices) {
	for (Price price : prices) {
	    String sql = "insert into prices values (?, ?, ?)";
	    Object[] params = { stock.getId(), price.getDate(), price.getValue() };
	    int[] types = { Types.BIGINT, Types.DATE, Types.DOUBLE };
	    jdbcTemplate.update(sql, params, types);
	}
    }

    public void clearPrices(Stock stock) {
	String sql = "delete from prices where price_stock_id=?";
	Object[] params = { stock.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

}