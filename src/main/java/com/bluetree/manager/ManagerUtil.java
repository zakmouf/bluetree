package com.bluetree.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ManagerUtil {

    public static Long extractLong(ResultSet resultSet, String columnName)
            throws SQLException {
        long value = resultSet.getLong(columnName);
        return resultSet.wasNull() ? null : new Long(value);
    }

    public static String extractString(ResultSet resultSet, String columnName)
            throws SQLException {
        return resultSet.getString(columnName);
    }

    public static Double extractDouble(ResultSet resultSet, String columnName)
            throws SQLException {
        double value = resultSet.getDouble(columnName);
        return resultSet.wasNull() ? null : new Double(value);
    }

    public static Integer extractInteger(ResultSet resultSet, String columnName)
            throws SQLException {
        int value = resultSet.getInt(columnName);
        return resultSet.wasNull() ? null : new Integer(value);
    }

    public static Boolean extractBoolean(ResultSet resultSet, String columnName)
            throws SQLException {
        boolean value = resultSet.getBoolean(columnName);
        return resultSet.wasNull() ? null : Boolean.valueOf(value);
    }

    public static Date extractDate(ResultSet resultSet, String columnName)
            throws SQLException {
        java.sql.Date date = resultSet.getDate(columnName);
        return resultSet.wasNull() ? null : new Date(date.getTime());
    }

    public static Date extractTime(ResultSet resultSet, String columnName)
            throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnName);
        return resultSet.wasNull() ? null : new Date(timestamp.getTime());
    }

}
