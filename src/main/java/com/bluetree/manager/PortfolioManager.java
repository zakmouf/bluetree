package com.bluetree.manager;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Position;
import com.bluetree.domain.User;
import com.bluetree.manager.mapper.MarketRowMapper;
import com.bluetree.manager.mapper.PortfolioRowMapper;
import com.bluetree.manager.mapper.PositionRowMapper;

public class PortfolioManager extends AbstractManager {

    public List<Portfolio> getPortfolios(User user) {
	String sql = "select * from portfolios, portfolio_lnk_user where portfolio_id=plu_portfolio_id and plu_user_id=?";
	Object[] params = { user.getId() };
	int[] types = { Types.BIGINT };
	List<Portfolio> portfolios = jdbcTemplate.query(sql, params, types, new PortfolioRowMapper());
	Collections.sort(portfolios);
	return portfolios;
    }

    public Portfolio findPortfolio(Long id) {
	String sql = "select * from portfolios where portfolio_id=?";
	Object[] params = { id };
	int[] types = { Types.BIGINT };
	List<Portfolio> portfolios = jdbcTemplate.query(sql, params, types, new PortfolioRowMapper());
	return portfolios.isEmpty() ? null : portfolios.get(0);
    }

    public void insertPortfolio(Portfolio portfolio) {
	String sql = "insert into portfolios values (null, ?, ?, ?, ?, ?, false, null)";
	Object[] params = { portfolio.getName(), portfolio.getFromDate(), portfolio.getToDate(), portfolio.getBeta(),
		portfolio.getSize() };
	int[] types = { Types.VARCHAR, Types.DATE, Types.DATE, Types.DOUBLE, Types.INTEGER };
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(newPreparedStatementCreator(sql, params, types), keyHolder);
	portfolio.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void setUser(Portfolio portfolio, User user) {
	//
	String sql = "delete from portfolio_lnk_user where plu_portfolio_id=?";
	Object[] params = { portfolio.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	sql = "insert into portfolio_lnk_user values (?, ?)";
	params = new Object[] { portfolio.getId(), user.getId() };
	types = new int[] { Types.BIGINT, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public Market getMarket(Portfolio portfolio) {
	String sql = "select * from portfolio_lnk_market, markets where market_id=plm_market_id and plm_portfolio_id=?";
	Object[] params = { portfolio.getId() };
	int[] types = { Types.BIGINT };
	List<Market> markets = jdbcTemplate.query(sql, params, types, new MarketRowMapper());
	return markets.isEmpty() ? null : markets.get(0);
    }

    public void setMarket(Portfolio portfolio, Market market) {
	//
	String sql = "delete from portfolio_lnk_market where plm_portfolio_id=?";
	Object[] params = { portfolio.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	sql = "insert into portfolio_lnk_market values (?, ?)";
	params = new Object[] { portfolio.getId(), market.getId() };
	types = new int[] { Types.BIGINT, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public void deletePortfolio(Portfolio portfolio) {
	//
	String sql = "delete from portfolios where portfolio_id=?";
	Object[] params = { portfolio.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from portfolio_lnk_stock where pls_portfolio_id=?";
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from portfolio_lnk_user where plu_portfolio_id=?";
	jdbcTemplate.update(sql, params, types);
	//
	sql = "delete from portfolio_lnk_market where plm_portfolio_id=?";
	jdbcTemplate.update(sql, params, types);
    }

    public void updatePortfolio(Portfolio portfolio) {
	String sql = "update portfolios set portfolio_from_date=?, portfolio_to_date=?, "
		+ "portfolio_executed=true, portfolio_error=? where portfolio_id=?";
	Object[] params = { portfolio.getFromDate(), portfolio.getToDate(), portfolio.getError(), portfolio.getId() };
	int[] types = { Types.DATE, Types.DATE, Types.VARCHAR, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public List<Position> getPositions(Portfolio portfolio) {
	String sql = "select * from portfolio_lnk_stock, stocks where pls_stock_id=stock_id and pls_portfolio_id=?";
	Object[] params = { portfolio.getId() };
	int[] types = { Types.BIGINT };
	List<Position> positions = jdbcTemplate.query(sql, params, types, new PositionRowMapper());
	Collections.sort(positions);
	return positions;
    }

    public void updatePositions(Portfolio portfolio, List<Position> positions) {
	//
	String sql = "delete from portfolio_lnk_stock where pls_portfolio_id=?";
	Object[] params = { portfolio.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	for (Position position : positions) {
	    sql = "insert into portfolio_lnk_stock values (?, ?, ?)";
	    params = new Object[] { portfolio.getId(), position.getStock().getId(), position.getWeight() };
	    types = new int[] { Types.BIGINT, Types.BIGINT, Types.DOUBLE };
	    jdbcTemplate.update(sql, params, types);
	}
    }

}