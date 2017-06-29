package com.rkylin.risk.core.mybatis.handler;

import com.rkylin.risk.commons.entity.Amount;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 金额转换器，默认为java的BigDecimal
 */
@MappedJdbcTypes(JdbcType.DECIMAL)
@MappedTypes(Amount.class)
public class AmountDecimalTypeHandler extends BaseTypeHandler<Amount> {
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Amount parameter, JdbcType jdbcType)
      throws SQLException {
    if (parameter != null) {
      ps.setBigDecimal(i, parameter.getValue());
    }
  }

  private Amount setAmount(BigDecimal number) {
    Amount amount = null;
    if (number != null) {
      amount = new Amount(number);
    }
    return amount;
  }

  private Amount setAmount(double number) {
    return new Amount(number);
  }

  @Override
  public Amount getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return setAmount(rs.getBigDecimal(columnName));
  }

  @Override
  public Amount getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return setAmount(rs.getBigDecimal(columnIndex));
  }

  @Override
  public Amount getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return setAmount(cs.getBigDecimal(columnIndex));
  }
}
