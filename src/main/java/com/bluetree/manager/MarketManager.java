package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Market;
import com.bluetree.domain.Profile;
import com.bluetree.domain.Stock;

public class MarketManager extends AbstractManager {

    public Market findMarket(Long id) {
        //
        String sql = "select * from markets where market_id=?";
        Object[] params = { id };
        int[] types = { Types.BIGINT };
        //
        List markets = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractMarket(resultSet);
                    }
                });
        //
        return markets.isEmpty() ? null : (Market) markets.get(0);
    }

    public Market findMarket(String name) {
        //
        String sql = "select * from markets where market_name=?";
        Object[] params = { name };
        int[] types = { Types.VARCHAR };
        //
        List markets = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractMarket(resultSet);
                    }
                });
        //
        return markets.isEmpty() ? null : (Market) markets.get(0);
    }

    public List getMarkets() {
        //
        String sql = "select * from markets";
        //
        List markets = getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException {
                return extractMarket(resultSet);
            }
        });
        //
        Collections.sort(markets);
        return markets;
    }

    public List getMarkets(List profiles) {
        // 
        if (profiles.isEmpty()) {
            return new ArrayList();
        }
        //
        String sql = "select * from markets, market_lnk_profile "
                + "where mlp_market_id=market_id and mlp_profile_id in (";
        Iterator iterator = profiles.iterator();
        while (iterator.hasNext()) {
            Profile profile = (Profile) iterator.next();
            sql += profile.getId().toString();
            if (iterator.hasNext()) {
                sql += ",";
            }
        }
        sql += ")";
        //
        List markets = getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException {
                return extractMarket(resultSet);
            }
        });
        //
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
        getJdbcTemplate().update(
                newPreparedStatementCreator(sql, params, types), keyHolder);
        market.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void deleteMarket(Market market) {
        //
        String sql = "select count(*) from portfolio_lnk_market where plm_market_id=?";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }
        //
        sql = "delete from markets where market_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from market_lnk_indice where mli_market_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from market_lnk_stock where mls_market_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from market_lnk_profile where mlp_market_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public void updateMarket(Market market) {
        //
        String sql = "update markets set market_name=?, market_riskless=? where market_id=?";
        Object[] params = { market.getName(), market.getRiskless(),
                market.getId() };
        int[] types = { Types.VARCHAR, Types.DOUBLE, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public Stock getIndice(Market market) {
        //
        String sql = "select * from market_lnk_indice, stocks where mli_market_id=? and mli_indice_id=stock_id";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        List stocks = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return StockManager.extractStock(resultSet);
                    }
                });
        //
        return (Stock) stocks.get(0);
    }

    public void updateIndice(Market market, Stock indice) {
        //
        String sql = "delete from market_lnk_indice where mli_market_id=?";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        // 
        sql = "insert into market_lnk_indice values (?, ?)";
        params = new Object[] { market.getId(), indice.getId() };
        types = new int[] { Types.BIGINT, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public List getStocks(Market market) {
        //
        String sql = "select * from market_lnk_stock, stocks where mls_market_id=? and mls_stock_id=stock_id";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        List stocks = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return StockManager.extractStock(resultSet);
                    }
                });
        //
        Collections.sort(stocks);
        return stocks;
    }

    public void updateStocks(Market market, List stocks) {
        //
        String sql = "delete from market_lnk_stock where mls_market_id=?";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        // 
        Iterator iterator = stocks.iterator();
        while (iterator.hasNext()) {
            Stock stock = (Stock) iterator.next();
            //
            sql = "insert into market_lnk_stock values (?, ?)";
            params = new Object[] { market.getId(), stock.getId() };
            types = new int[] { Types.BIGINT, Types.BIGINT };
            //
            getJdbcTemplate().update(sql, params, types);
        }
    }

    public List getProfiles(Market market) {
        //
        String sql = "select * from market_lnk_profile, profiles where mlp_market_id=? and mlp_profile_id=profile_id";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        List profiles = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return ProfileManager.extractProfile(resultSet);
                    }
                });
        //
        Collections.sort(profiles);
        return profiles;
    }

    public void updateProfiles(Market market, List profiles) {
        //
        String sql = "delete from market_lnk_profile where mlp_market_id=?";
        Object[] params = { market.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        // 
        Iterator iterator = profiles.iterator();
        while (iterator.hasNext()) {
            Profile profile = (Profile) iterator.next();
            //
            sql = "insert into market_lnk_profile values (?, ?)";
            params = new Object[] { market.getId(), profile.getId() };
            types = new int[] { Types.BIGINT, Types.BIGINT };
            //
            getJdbcTemplate().update(sql, params, types);
        }
    }

    public static Market extractMarket(ResultSet resultSet) throws SQLException {
        Market market = new Market();
        market.setId(ManagerUtil.extractLong(resultSet, "market_id"));
        market.setName(ManagerUtil.extractString(resultSet, "market_name"));
        market.setRiskless(ManagerUtil.extractDouble(resultSet,
                "market_riskless"));
        return market;
    }

}