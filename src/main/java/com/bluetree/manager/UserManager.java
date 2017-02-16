package com.bluetree.manager;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Profile;
import com.bluetree.domain.User;
import com.bluetree.manager.mapper.ProfileRowMapper;
import com.bluetree.manager.mapper.UserRowMapper;

public class UserManager extends AbstractManager {

    public User findUser(Long id) {
	String sql = "select * from users where user_id=?";
	Object[] params = { id };
	int[] types = { Types.BIGINT };
	List<User> users = jdbcTemplate.query(sql, params, types, new UserRowMapper());
	return users.isEmpty() ? null : users.get(0);
    }

    public User findUser(String login) {
	String sql = "select * from users where user_login=?";
	Object[] params = { login };
	int[] types = { Types.VARCHAR };
	List<User> users = jdbcTemplate.query(sql, params, types, new UserRowMapper());
	return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getUsers() {
	String sql = "select * from users";
	List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
	Collections.sort(users);
	return users;
    }

    public void insertUser(User user) {
	String sql = "insert into users values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	Object[] params = { user.getLogin(), user.getPassword(), user.getName(), user.getCompany(), user.getEmail(),
		user.getPhone(), user.getAddress(), user.getZip(), user.getCity(), user.getCountry() };
	int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(newPreparedStatementCreator(sql, params, types), keyHolder);
	user.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void updateUser(User user) {
	String sql = "update users set user_password=?, ";
	sql += "user_name=?, user_company=?, user_email=?, user_phone=?, ";
	sql += "user_address=?, user_zip=?, user_city=?, user_country=? ";
	sql += "where user_id=?";
	Object[] params = { user.getPassword(), user.getName(), user.getCompany(), user.getEmail(), user.getPhone(),
		user.getAddress(), user.getZip(), user.getCity(), user.getCountry(), user.getId() };
	int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
    }

    public List<Profile> getProfiles(User user) {
	String sql = "select * from user_lnk_profile, profiles where ulp_user_id=? and ulp_profile_id=profile_id";
	Object[] params = { user.getId() };
	int[] types = { Types.BIGINT };
	List<Profile> profiles = jdbcTemplate.query(sql, params, types, new ProfileRowMapper());
	Collections.sort(profiles);
	return profiles;
    }

    public void updateProfiles(User user, List<Profile> profiles) {
	//
	String sql = "delete from user_lnk_profile where ulp_user_id=?";
	Object[] params = { user.getId() };
	int[] types = { Types.BIGINT };
	jdbcTemplate.update(sql, params, types);
	//
	for (Profile profile : profiles) {
	    sql = "insert into user_lnk_profile values (?, ?)";
	    params = new Object[] { user.getId(), profile.getId() };
	    types = new int[] { Types.BIGINT, Types.BIGINT };
	    jdbcTemplate.update(sql, params, types);
	}
    }

}
