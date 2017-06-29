package com.rkylin.risk.core.dao.impl;

import com.google.common.base.Throwables;
import com.rkylin.risk.core.dao.OverdueDao;
import com.rkylin.risk.core.entity.Overdue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Created by ChenFumin on 2016-8-30.
 */
@Repository
@Slf4j
public class OverdueDaoImpl extends BaseDaoImpl<Overdue> implements OverdueDao {

  /**
   * 需要查逾期率的数据源
   */
  @Resource
  private DataSource report;

  @Override
  public String queryOverdueRate(String merchantid, String yesterday) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql =
          "SELECT CURRAMOUNTPERLAST "
              + " FROM V_OVERDUE_RATE "
              + " WHERE TRAINING_AGENCY_ID = ? "
              + " AND STATISTICS_DATE = ?";
      conn = report.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, merchantid);
      pstmt.setString(2, yesterday);
      rs = pstmt.executeQuery();
      if (!rs.next()) {
        return null;
      }
      return rs.getString(1);
    } catch (Exception e) {
      log.info("查询逾期率出现异常，请检查连接，异常信息:{}", Throwables.getStackTraceAsString(e));
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          log.info(e.getMessage(), e);
        }
      }
      if (pstmt != null) {
        try {
          pstmt.close();
        } catch (SQLException e) {
          log.info(e.getMessage(), e);
        }
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          log.info(e.getMessage(), e);
        }
      }
    }
    return null;
  }
}
