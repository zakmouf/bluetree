package com.bluetree.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

public class AbstractManager {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected PreparedStatementCreator newPreparedStatementCreator(String sql, Object[] params, int[] types) {
	PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, types);
	factory.setReturnGeneratedKeys(true);
	PreparedStatementCreator creator = factory.newPreparedStatementCreator(params);
	return creator;
    }

}
