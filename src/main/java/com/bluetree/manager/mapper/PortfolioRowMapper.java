package com.bluetree.manager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bluetree.domain.Portfolio;

public class PortfolioRowMapper extends AbstractRowMapper implements RowMapper<Portfolio> {

    @Override
    public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
	return getPortfolio(rs);
    }

}
