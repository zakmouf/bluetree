package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Profile;
import com.bluetree.domain.User;

public class UserManager extends AbstractManager {

    public User findUser(Long id) {
        //
        String sql = "select * from users where user_id=?";
        Object[] params = { id };
        int[] types = { Types.BIGINT };
        //
        List users = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractUser(resultSet);
                    }
                });
        //
        return users.isEmpty() ? null : (User) users.get(0);
    }

    public User findUser(String login) {
        //
        String sql = "select * from users where user_login=?";
        Object[] params = { login };
        int[] types = { Types.VARCHAR };
        //
        List users = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractUser(resultSet);
                    }
                });
        //
        return users.isEmpty() ? null : (User) users.get(0);
    }

    public List getUsers() {
        //
        String sql = "select * from users";
        //
        List users = getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException {
                return extractUser(resultSet);
            }
        });
        //
        Collections.sort(users);
        return users;
    }

    public void insertUser(User user) {
        //
        String sql = "insert into users values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = { user.getLogin(), user.getPassword(),
                user.getName(), user.getCompany(), user.getEmail(),
                user.getPhone(), user.getAddress(), user.getZip(),
                user.getCity(), user.getCountry() };
        int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
        //
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                newPreparedStatementCreator(sql, params, types), keyHolder);
        user.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void updateUser(User user) {
        //
        String sql = "update users set user_password=?, ";
        sql += "user_name=?, user_company=?, user_email=?, user_phone=?, ";
        sql += "user_address=?, user_zip=?, user_city=?, user_country=? ";
        sql += "where user_id=?";
        Object[] params = { user.getPassword(), user.getName(),
                user.getCompany(), user.getEmail(), user.getPhone(),
                user.getAddress(), user.getZip(), user.getCity(),
                user.getCountry(), user.getId() };
        int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public List getProfiles(User user) {
        //
        String sql = "select * from user_lnk_profile, profiles where ulp_user_id=? and ulp_profile_id=profile_id";
        Object[] params = { user.getId() };
        int[] types = { Types.BIGINT };
        //
        List profiles = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return ProfileManager.extractProfile(resultSet);
                    }
                });
        //
        Collections.sort(profiles);
        return profiles;
    }

    public void updateProfiles(User user, List profiles) {
        //
        String sql = "delete from user_lnk_profile where ulp_user_id=?";
        Object[] params = { user.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
        //
        Iterator iterator = profiles.iterator();
        while (iterator.hasNext()) {
            Profile profile = (Profile) iterator.next();
            //
            sql = "insert into user_lnk_profile values (?, ?)";
            params = new Object[] { user.getId(), profile.getId() };
            types = new int[] { Types.BIGINT, Types.BIGINT };
            //
            getJdbcTemplate().update(sql, params, types);
        }
    }

    public static User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(ManagerUtil.extractLong(resultSet, "user_id"));
        user.setLogin(ManagerUtil.extractString(resultSet, "user_login"));
        user.setPassword(ManagerUtil.extractString(resultSet, "user_password"));
        user.setName(ManagerUtil.extractString(resultSet, "user_name"));
        user.setCompany(ManagerUtil.extractString(resultSet, "user_company"));
        user.setEmail(ManagerUtil.extractString(resultSet, "user_email"));
        user.setPhone(ManagerUtil.extractString(resultSet, "user_phone"));
        user.setAddress(ManagerUtil.extractString(resultSet, "user_address"));
        user.setZip(ManagerUtil.extractString(resultSet, "user_zip"));
        user.setCity(ManagerUtil.extractString(resultSet, "user_city"));
        user.setCountry(ManagerUtil.extractString(resultSet, "user_country"));
        return user;
    }

}
