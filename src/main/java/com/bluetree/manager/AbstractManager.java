package com.bluetree.manager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

public class AbstractManager {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected PreparedStatementCreator newPreparedStatementCreator(String sql,
            Object[] params, int[] types) {
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                sql, types);
        factory.setReturnGeneratedKeys(true);
        PreparedStatementCreator creator = factory
                .newPreparedStatementCreator(params);
        return creator;
    }

}
