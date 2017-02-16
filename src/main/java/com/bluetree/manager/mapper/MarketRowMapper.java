package com.bluetree.manager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bluetree.domain.Market;

public class MarketRowMapper extends AbstractRowMapper implements RowMapper<Market> {

    @Override
    public Market mapRow(ResultSet rs, int rowNum) throws SQLException {
	return getMarket(rs);
    }

}
