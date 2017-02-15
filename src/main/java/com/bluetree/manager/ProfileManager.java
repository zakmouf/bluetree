package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bluetree.domain.Profile;

public class ProfileManager extends AbstractManager {

    public Profile findProfile(Long id) {
        //
        String sql = "select * from profiles where profile_id=?";
        Object[] params = { id };
        int[] types = { Types.BIGINT };
        //
        List profiles = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractProfile(resultSet);
                    }
                });
        //
        return profiles.isEmpty() ? null : (Profile) profiles.get(0);
    }

    public Profile findProfile(String name) {
        //
        String sql = "select * from profiles where profile_name=?";
        Object[] params = { name };
        int[] types = { Types.VARCHAR };
        //
        List profiles = getJdbcTemplate().query(sql, params, types,
                new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException {
                        return extractProfile(resultSet);
                    }
                });
        //
        return profiles.isEmpty() ? null : (Profile) profiles.get(0);
    }

    public Profile findDefaultProfile() {
        //
        String sql = "select * from profiles where profile_default=1";
        //
        List profiles = getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException {
                return extractProfile(resultSet);
            }
        });
        //
        return profiles.isEmpty() ? null : (Profile) profiles.get(0);
    }

    public List getProfiles() {
        //
        String sql = "select * from profiles";
        //
        List profiles = getJdbcTemplate().query(sql, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException {
                return extractProfile(resultSet);
            }
        });
        //
        Collections.sort(profiles);
        return profiles;
    }

    public void insertProfile(Profile profile) {
        //
        String sql = "insert into profiles values (null, ?, 0)";
        Object[] params = { profile.getName() };
        int[] types = { Types.VARCHAR };
        //
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                newPreparedStatementCreator(sql, params, types), keyHolder);
        profile.setId(new Long(keyHolder.getKey().longValue()));
    }

    public void updateProfile(Profile profile) {
        //
        String sql = "update profiles set profile_name=? where profile_id=?";
        Object[] params = { profile.getName(), profile.getId() };
        int[] types = { Types.VARCHAR, Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public void updateDefaultProfile(Profile profile) {
        //
        String sql = "update profiles set profile_default=0";
        //
        getJdbcTemplate().update(sql);
        //
        sql = "update profiles set profile_default=1 where profile_id=?";
        Object[] params = { profile.getId() };
        int[] types = { Types.BIGINT };
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public void deleteProfile(Profile profile) {
        //
        String sql = "select count(*) from market_lnk_profile where mlp_profile_id=?";
        Object[] params = { profile.getId() };
        int[] types = { Types.BIGINT };
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }        
        //
        sql = "select count(*) from user_lnk_profile where ulp_profile_id=?";
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }      
        //
        sql = "select count(*) from profiles where profile_id=? and profile_default=1";
        //
        if (getJdbcTemplate().queryForInt(sql, params, types) > 0) {
            return;
        }
        //
        sql = "delete from profiles where profile_id=?";
        //
        getJdbcTemplate().update(sql, params, types);
    }

    public static Profile extractProfile(ResultSet resultSet)
            throws SQLException {
        Profile profile = new Profile();
        profile.setId(ManagerUtil.extractLong(resultSet, "profile_id"));
        profile.setName(ManagerUtil.extractString(resultSet, "profile_name"));
        return profile;
    }

}