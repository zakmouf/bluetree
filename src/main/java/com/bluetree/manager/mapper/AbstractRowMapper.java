package com.bluetree.manager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.Position;
import com.bluetree.domain.Price;
import com.bluetree.domain.Profile;
import com.bluetree.domain.Stock;
import com.bluetree.domain.User;

public abstract class AbstractRowMapper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Long getLong(ResultSet rs, String columnLabel) throws SQLException {
	long value = rs.getLong(columnLabel);
	return rs.wasNull() ? null : new Long(value);
    }

    protected String getString(ResultSet rs, String columnLabel) throws SQLException {
	return rs.getString(columnLabel);
    }

    protected Double getDouble(ResultSet rs, String columnLabel) throws SQLException {
	double value = rs.getDouble(columnLabel);
	return rs.wasNull() ? null : new Double(value);
    }

    protected Integer getInteger(ResultSet rs, String columnLabel) throws SQLException {
	int value = rs.getInt(columnLabel);
	return rs.wasNull() ? null : new Integer(value);
    }

    protected Boolean getBoolean(ResultSet rs, String columnLabel) throws SQLException {
	boolean value = rs.getBoolean(columnLabel);
	return rs.wasNull() ? null : Boolean.valueOf(value);
    }

    protected Date getDate(ResultSet rs, String columnLabel) throws SQLException {
	java.sql.Date date = rs.getDate(columnLabel);
	return rs.wasNull() ? null : new Date(date.getTime());
    }

    protected Date getTimestamp(ResultSet rs, String columnLabel) throws SQLException {
	Timestamp timestamp = rs.getTimestamp(columnLabel);
	return rs.wasNull() ? null : new Date(timestamp.getTime());
    }

    protected Market getMarket(ResultSet rs) throws SQLException {
	Market market = new Market();
	market.setId(getLong(rs, "market_id"));
	market.setName(getString(rs, "market_name"));
	market.setRiskless(getDouble(rs, "market_riskless"));
	return market;
    }

    protected Profile getProfile(ResultSet rs) throws SQLException {
	Profile profile = new Profile();
	profile.setId(getLong(rs, "profile_id"));
	profile.setName(getString(rs, "profile_name"));
	return profile;
    }

    protected Stock getStock(ResultSet rs) throws SQLException {
	Stock stock = new Stock();
	stock.setId(getLong(rs, "stock_id"));
	stock.setSymbol(getString(rs, "stock_symbol"));
	stock.setName(getString(rs, "stock_name"));
	return stock;
    }

    protected User getUser(ResultSet rs) throws SQLException {
	User user = new User();
	user.setId(getLong(rs, "user_id"));
	user.setLogin(getString(rs, "user_login"));
	user.setPassword(getString(rs, "user_password"));
	user.setName(getString(rs, "user_name"));
	user.setCompany(getString(rs, "user_company"));
	user.setEmail(getString(rs, "user_email"));
	user.setPhone(getString(rs, "user_phone"));
	user.setAddress(getString(rs, "user_address"));
	user.setZip(getString(rs, "user_zip"));
	user.setCity(getString(rs, "user_city"));
	user.setCountry(getString(rs, "user_country"));
	return user;
    }

    protected Price getPrice(ResultSet rs) throws SQLException {
	Price price = new Price();
	price.setDate(getDate(rs, "price_date"));
	price.setValue(getDouble(rs, "price_value"));
	return price;
    }

    protected Portfolio getPortfolio(ResultSet rs) throws SQLException {
	Portfolio portfolio = new Portfolio();
	portfolio.setId(getLong(rs, "portfolio_id"));
	portfolio.setName(getString(rs, "portfolio_name"));
	portfolio.setFromDate(getDate(rs, "portfolio_from_date"));
	portfolio.setToDate(getDate(rs, "portfolio_to_date"));
	portfolio.setBeta(getDouble(rs, "portfolio_beta"));
	portfolio.setSize(getInteger(rs, "portfolio_size"));
	portfolio.setExecuted(getBoolean(rs, "portfolio_executed"));
	portfolio.setError(getString(rs, "portfolio_error"));
	return portfolio;
    }

    protected Position getPosition(ResultSet rs) throws SQLException {
	Position position = new Position();
	position.setStock(getStock(rs));
	position.setWeight(getDouble(rs, "pls_weight"));
	return position;
    }

}
