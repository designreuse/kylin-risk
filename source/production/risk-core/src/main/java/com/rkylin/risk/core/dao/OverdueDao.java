package com.rkylin.risk.core.dao;

/**
 * Created by ChenFumin on 2016-8-30.
 */
public interface OverdueDao {
  String queryOverdueRate(String merchantid, String yesterday);
}
