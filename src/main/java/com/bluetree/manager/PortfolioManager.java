package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Position;
import com.bluetree.domain.User;

public class PortfolioManager extends AbstractManager {

    public List getPortfolios(User user) {
        //
        String sql = "select * from portfolios, portfolio_lnk_user where portfolio_id=plu_portfolio_id and plu_user_id=?";
        Object[] params = { user.getId() };
        int[] types = { Types.BIGINT };
        // 
        List portfolios = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPortfolio(resultSet);
                    }
                });
        //
        Collections.sort(portfolios);
        return portfolios;
    }

    public Portfolio findPortfolio(Long id) {
        //
        String sql = "select * from portfolios where portfolio_id=?";
        Object[] params = { id };
        int[] types = { Types.BIGINT };
        // 
        List portfolios = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPortfolio(resultSet);
                    }
                });
        //
        return portfolios.isEmpty() ? null : (Portfolio) portfolios.get(0);
    }

    public void insertPortfolio(Portfolio portfolio) {
        //
        String sql = "insert into portfolios values (null, ?, ?, ?, ?, ?, false, null)";
        Object[] params = { portfolio.getName(), portfolio.getFromDate(),
                portfolio.getToDate(), portfolio.getBeta(), portfolio.getSize() };
        int[] types = { Types.VARCHAR, Types.DATE, Types.DATE, Types.DOUBLE,
                Types.INTEGER };
        //
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                newPreparedStatementCreator(sql, params, types), keyHolder);
        portfolio.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void setUser(Portfolio portfolio, User user) {
        //
        String sql = "delete from portfolio_lnk_user where plu_portfolio_id=?";
        Object[] params = { portfolio.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "insert into portfolio_lnk_user values (?, ?)";
        params = new Object[] { portfolio.getId(), user.getId() };
        types = new int[] { Types.BIGINT, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        //
    }

    public Market getMarket(Portfolio portfolio) {
        //
        String sql = "select * from portfolio_lnk_market, markets where market_id=plm_market_id and plm_portfolio_id=?";
        Object[] params = { portfolio.getId() };
        int[] types = { Types.BIGINT };
        //
        List markets = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return MarketManager.extractMarket(resultSet);
                    }
                });
        //
        return markets.isEmpty() ? null : (Market) markets.get(0);
    }

    public void setMarket(Portfolio portfolio, Market market) {
        //
        String sql = "delete from portfolio_lnk_market where plm_portfolio_id=?";
        Object[] params = { portfolio.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "insert into portfolio_lnk_market values (?, ?)";
        params = new Object[] { portfolio.getId(), market.getId() };
        types = new int[] { Types.BIGINT, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public void deletePortfolio(Portfolio portfolio) {
        //
        String sql = "delete from portfolios where portfolio_id=?";
        Object[] params = { portfolio.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from portfolio_lnk_stock where pls_portfolio_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from portfolio_lnk_user where plu_portfolio_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
        //
        sql = "delete from portfolio_lnk_market where plm_portfolio_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public void updatePortfolio(Portfolio portfolio) {
        //
        String sql = "update portfolios set portfolio_from_date=?, portfolio_to_date=?, "
                + "portfolio_executed=true, portfolio_error=? where portfolio_id=?";
        Object[] params = { portfolio.getFromDate(), portfolio.getToDate(),
                portfolio.getError(), portfolio.getId() };
        int[] types = { Types.DATE, Types.DATE, Types.VARCHAR, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public List getPositions(Portfolio portfolio) {
        //
        String sql = "select * from portfolio_lnk_stock, stocks where pls_stock_id=stock_id and pls_portfolio_id=?";
        Object[] params = { portfolio.getId() };
        int[] types = { Types.BIGINT };
        //
        List positions = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractPosition(resultSet);
                    }
                });
        //
        Collections.sort(positions);
        return positions;
    }

    public void updatePositions(Portfolio portfolio, List positions) {
        //
        String sql = "delete from portfolio_lnk_stock where pls_portfolio_id=?";
        Object[] params = { portfolio.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        //
        Iterator iterator = positions.iterator();
        while (iterator.hasNext()) {
            Position position = (Position) iterator.next();
            //
            sql = "insert into portfolio_lnk_stock values (?, ?, ?)";
            params = new Object[] { portfolio.getId(),
                    position.getStock().getId(), position.getWeight() };
            types = new int[] { Types.BIGINT, Types.BIGINT, Types.DOUBLE };
            //
            getJdbcTemplate().update(sql, params, types);
        }
    }

    public static Portfolio extractPortfolio(ResultSet resultSet)
            throws SQLException {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(ManagerUtil.extractLong(resultSet, "portfolio_id"));
        portfolio.setName(ManagerUtil
                .extractString(resultSet, "portfolio_name"));
        portfolio.setFromDate(ManagerUtil.extractDate(resultSet,
                "portfolio_from_date"));
        portfolio.setToDate(ManagerUtil.extractDate(resultSet,
                "portfolio_to_date"));
        portfolio.setBeta(ManagerUtil
                .extractDouble(resultSet, "portfolio_beta"));
        portfolio.setSize(ManagerUtil.extractInteger(resultSet,
                "portfolio_size"));
        portfolio.setExecuted(ManagerUtil.extractBoolean(resultSet,
                "portfolio_executed"));
        portfolio.setError(ManagerUtil.extractString(resultSet,
                "portfolio_error"));
        return portfolio;
    }

    public static Position extractPosition(ResultSet resultSet)
            throws SQLException {
        Position position = new Position();
        position.setStock(StockManager.extractStock(resultSet));
        position.setWeight(ManagerUtil.extractDouble(resultSet, "pls_weight"));
        return position;
    }

}