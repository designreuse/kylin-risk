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
@MappedJdbcTypes(JdbcType.DOUBLE)
@MappedTypes(Amount.class)
public class AmountDoubleTypeHandler extends BaseTypeHandler<Amount> {
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Amount amount, JdbcType jdbcType)
      throws SQLException {
    BigDecimal decimal = amount.getValue();
    if (decimal != null) {
      ps.setDouble(i, decimal.doubleValue());
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
    return setAmount(rs.getDouble(columnName));
  }

  @Override
  public Amount getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return setAmount(rs.getDouble(columnIndex));
  }

  @Override
  public Amount getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return setAmount(cs.getDouble(columnIndex));
  }
}
