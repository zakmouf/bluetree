package com.bluetree.manager;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Market;
import com.bluetree.domain.Profile;
import com.bluetree.domain.Stock;
import com.bluetree.manager.mapper.MarketRowMapper;
import com.bluetree.manager.mapper.ProfileRowMapper;
import com.bluetree.manager.mapper.StockRowMapper;

public class MarketManager extends AbstractManager {

    public Market findMarket(Long id) {
	String sql = "select * from markets where market_id=?";
	Object[] params = { id };
	int[] types = { Types.BIGINT };
	List<Market> markets = jdbcTemplate.query(sql, params, types, new MarketRowMapper());
	return markets.isEmpty() ? null : markets.get(0);
    }

    public Market findMarket(String name) {
	String sql = "select * from markets where market_name=?";
	Object[] params = { name };
	int[] types = { Types.VARCHAR };
	List<Market> markets = jdbcTemplate.query(sql, params, types, new MarketRowMapper());
	return markets.isEmpty() ? null : markets.get(0);
    }

    public List<Market> getMarkets() {
	String sql = "select * from markets";
	List<Market> markets = jdbcTemplate.query(sql, new MarketRowMapper());
	Collections.sort(markets);
	return markets;
    }

    public List<Market> getMarkets(List<Profile> profiles) {
	//
	if (profiles.isEmpty()) {
	    return new ArrayList<Market>();
	}
	//
	String sql = "select * from markets, market_lnk_profile "
		+ "where mlp_market_id=market_id and mlp_profile_id in (";
	Iterator<Profile> iterator = profiles.iterator();
	while (iterator.hasNext()) {
	    Profile profile = iterator.next();
	    sql += profile.getId().toString();
	    if (iterator.hasNext()) {
		sql += ",";
	    }
	}
	sql += ")";
	//
	List<Market> markets = jdbcTemplate.query(sql, new MarketRowMapper());
	Collections.sort(markets);
	return markets;
    }

    public void insertMarket(Market market) {
	//
	String sql = "insert into markets values (null, ?, ?)";
	Object[] params = { market.getName(), market.getRiskless() };
	int[] types = { Types.VARCHAR, Types.DOUBLE };
	//
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(newPreparedStatementCreator(sql, params, types), keyHolder);
	market.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void deleteMarket(Market market) {
	//
	String sql = "select count(*) from portfolio_lnk_market where plm_market_id=?";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "delete from markets where market_id=?";
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from market_lnk_indice where mli_market_id=?";
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from market_lnk_stock where mls_market_id=?";
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from market_lnk_profile where mlp_market_id=?";
	jdbcTemplate.update(sql, params, types);
    }

    public void updateMarket(Market market) {
	String sql = "update markets set market_name=?, market_riskless=? where market_id=?";
	Object[] params = { market.getName(), market.getRiskless(), market.getId() };
	int[] types = { Types.VARCHAR, Types.DOUBLE, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public Stock getIndice(Market market) {
	String sql = "select * from market_lnk_indice, stocks where mli_market_id=? and mli_indice_id=stock_id";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	List<Stock> stocks = jdbcTemplate.query(sql, params, types, new StockRowMapper());
	return stocks.get(0);
    }

    public void updateIndice(Market market, Stock indice) {
	//
	String sql = "delete from market_lnk_indice where mli_market_id=?";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	sql = "insert into market_lnk_indice values (?, ?)";
	params = new Object[] { market.getId(), indice.getId() };
	types = new int[] { Types.BIGINT, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public List<Stock> getStocks(Market market) {
	String sql = "select * from market_lnk_stock, stocks where mls_market_id=? and mls_stock_id=stock_id";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	List<Stock> stocks = jdbcTemplate.query(sql, params, types, new StockRowMapper());
	Collections.sort(stocks);
	return stocks;
    }

    public void updateStocks(Market market, List<Stock> stocks) {
	//
	String sql = "delete from market_lnk_stock where mls_market_id=?";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	for (Stock stock : stocks) {
	    sql = "insert into market_lnk_stock values (?, ?)";
	    params = new Object[] { market.getId(), stock.getId() };
	    types = new int[] { Types.BIGINT, Types.BIGINT };
	    jdbcTemplate.update(sql, params, types);
	}
    }

    public List<Profile> getProfiles(Market market) {
	String sql = "select * from market_lnk_profile, profiles where mlp_market_id=? and mlp_profile_id=profile_id";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	List<Profile> profiles = jdbcTemplate.query(sql, params, types, new ProfileRowMapper());
	Collections.sort(profiles);
	return profiles;
    }

    public void updateProfiles(Market market, List<Profile> profiles) {
	//
	String sql = "delete from market_lnk_profile where mlp_market_id=?";
	Object[] params = { market.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	for (Profile profile : profiles) {
	    sql = "insert into market_lnk_profile values (?, ?)";
	    params = new Object[] { market.getId(), profile.getId() };
	    types = new int[] { Types.BIGINT, Types.BIGINT };
	    jdbcTemplate.update(sql, params, types);
	}
    }

}