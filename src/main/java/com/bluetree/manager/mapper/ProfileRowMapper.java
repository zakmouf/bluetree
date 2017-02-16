package com.bluetree.manager.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bluetree.domain.Profile;

public class ProfileRowMapper extends AbstractRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
	return getProfile(rs);
    }

}
