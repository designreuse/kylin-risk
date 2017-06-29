package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.PagingDao;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1 version: 1.0
 */
@Repository("pagingDao")
public class PagingDaoImpl extends BaseDaoImpl implements PagingDao {

  /**
   * 查询
   *
   * @param sqlId sql语句的id
   * @param map 查询的参数
   * @return 实体对象集合
   */
  @Override
  public List query(String sqlId, Map map) {
    return super.queryList(sqlId, map);
  }

  /**
   * 分页查询
   *
   * @param sqlId sql语句的id
   * @param offset 起始页
   * @param limit 查询条数
   * @param param 参数
   * @return 实体对象集合
   */
  @Override
  public List queryPage(String sqlId, int offset, int limit, Map param) {
    return super.queryPageList(sqlId, offset, limit, param);
  }
}
