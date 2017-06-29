package com.rkylin.risk.core.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


/**
 * 将java的Timestamp转换为Joda的DateTime
 */
@MappedJdbcTypes(JdbcType.TIMESTAMP)
@MappedTypes(DateTime.class)
public class DateTimeHandler extends BaseTypeHandler<DateTime> {

  @Override
  public DateTime getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnName);
    if (timestamp != null) {
      return new DateTime(timestamp, DateTimeZone.getDefault());
    }
    return null;
  }

  @Override
  public DateTime getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnIndex);
    if (timestamp != null) {
      return new DateTime(timestamp, DateTimeZone.getDefault());
    }
    return null;
  }

  @Override
  public DateTime getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    Timestamp timestamp = cs.getTimestamp(columnIndex);
    if (timestamp != null) {
      return new DateTime(timestamp, DateTimeZone.getDefault());
    }
    return null;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i,
      DateTime dateTime, JdbcType jdbcType) throws SQLException {
    Timestamp timestamp = null;
    if (dateTime != null) {
      timestamp = new Timestamp(dateTime.getMillis());
    }
    ps.setTimestamp(i, timestamp);
  }
}
