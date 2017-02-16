package com.bluetree.manager;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Profile;
import com.bluetree.manager.mapper.ProfileRowMapper;

public class ProfileManager extends AbstractManager {

    public Profile findProfile(Long id) {
	String sql = "select * from profiles where profile_id=?";
	Object[] params = { id };
	int[] types = { Types.BIGINT };
	List<Profile> profiles = jdbcTemplate.query(sql, params, types, new ProfileRowMapper());
	return profiles.isEmpty() ? null : profiles.get(0);
    }

    public Profile findProfile(String name) {
	String sql = "select * from profiles where profile_name=?";
	Object[] params = { name };
	int[] types = { Types.VARCHAR };
	List<Profile> profiles = jdbcTemplate.query(sql, params, types, new ProfileRowMapper());
	return profiles.isEmpty() ? null : profiles.get(0);
    }

    public Profile findDefaultProfile() {
	String sql = "select * from profiles where profile_default=1";
	List<Profile> profiles = jdbcTemplate.query(sql, new ProfileRowMapper());
	return profiles.isEmpty() ? null : profiles.get(0);
    }

    public List<Profile> getProfiles() {
	String sql = "select * from profiles";
	List<Profile> profiles = jdbcTemplate.query(sql, new ProfileRowMapper());
	Collections.sort(profiles);
	return profiles;
    }

    public void insertProfile(Profile profile) {
	String sql = "insert into profiles values (null, ?, 0)";
	Object[] params = { profile.getName() };
	int[] types = { Types.VARCHAR };
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(newPreparedStatementCreator(sql, params, types), keyHolder);
	profile.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void updateProfile(Profile profile) {
	String sql = "update profiles set profile_name=? where profile_id=?";
	Object[] params = { profile.getName(), profile.getId() };
	int[] types = { Types.VARCHAR, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public void updateDefaultProfile(Profile profile) {
	//
	String sql = "update profiles set profile_default=0";
	jdbcTemplate.update(sql);
	//
	sql = "update profiles set profile_default=1 where profile_id=?";
	Object[] params = { profile.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public void deleteProfile(Profile profile) {
	//
	String sql = "select count(*) from market_lnk_profile where mlp_profile_id=?";
	Object[] params = { profile.getId() };
	int[] types = { Types.BIGINT };
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "select count(*) from user_lnk_profile where ulp_profile_id=?";
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "select count(*) from profiles where profile_id=? and profile_default=1";
	if (jdbcTemplate.queryForObject(sql, params, types, Integer.class) > 0) {
	    return;
	}
	//
	sql = "delete from profiles where profile_id=?";
	jdbcTemplate.update(sql, params, types);
    }

}