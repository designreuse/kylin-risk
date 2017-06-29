package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.exception.RiskException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * 数据库的操作类
 */
@Slf4j class BaseDaoImpl<T extends Serializable> {

  @Resource
  private SqlSession sqlSession;

  private String statementIdPrefix;

  BaseDaoImpl() {
    try {
      Class entityClass = GenericUtils.getGenericClass(this.getClass());
      statementIdPrefix = entityClass == null ? "" : entityClass.getSimpleName() + ".";
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 得到sql语句id的全namespace
   */
  private String getStatementId(String sqlId) {
    return statementIdPrefix + sqlId;
  }

  /**
   * 增加实体
   */
  protected final int add(T entity) {
    return add(PkgConst.INSERT_STATEMENT, entity);
  }

  /**
   * 增加实体
   *
   * @param sqlId sql语句id
   * @param entity 实体
   */
  protected final int add(String sqlId, T entity) {
    return sqlSession.insert(getStatementId(sqlId), entity);
  }

  /**
   * 批量增加实体 针对sql: insert into <i>tablename</i>(columni,column2,...)
   * values(value1,value2,...)(value1,value2,...);
   *
   * @param sqlId sql语句id
   * @param entitys 实体
   */
  protected final void addBatch(String sqlId, List<T> entitys) {
    sqlSession.insert(getStatementId(sqlId), entitys);
  }

  protected final int del(String id) {
    return sqlSession.delete(getStatementId(PkgConst.DELETE_STATEMENT), id);
  }

  protected final int del(Number id) {
    return sqlSession.delete(getStatementId(PkgConst.DELETE_STATEMENT), id);
  }

  protected final int del(String sqlId, String parameter) {
    return sqlSession.delete(getStatementId(sqlId), parameter);
  }

  protected final int del(String sqlId, Number parameter) {
    return sqlSession.delete(getStatementId(sqlId), parameter);
  }

  protected final int del(String sqlId, Map parameter) {
    return sqlSession.delete(getStatementId(sqlId), parameter);
  }

  protected final int del(String sqlId, T parameter) {
    return sqlSession.delete(getStatementId(sqlId), parameter);
  }

  /**
   * 更新实体
   */
  protected final int modify(T entity) {
    return sqlSession.update(
        getStatementId(PkgConst.UPDATE_STATEMENT), entity);
  }

  protected final int modify(String sqlId, T parameter) {
    return sqlSession.update(getStatementId(sqlId), parameter);
  }

  protected final int modify(String sqlId, Map parameter) {
    return sqlSession.update(getStatementId(sqlId), parameter);
  }

  /**
   * 查询
   */
  protected final T get(Serializable id) {
    return sqlSession.selectOne(getStatementId(PkgConst.GET_STATEMENT), id);
  }

  /**
   * 查询所有
   */
  protected final List<T> selectAllList() {
    return sqlSession.selectList(getStatementId(PkgConst.QUERY_ALL_STATEMENT));
  }

  /**
   * 查询一条数据
   */
  protected final T selectOne(Number parameter) {
    return sqlSession.selectOne(getStatementId(PkgConst.QUERY_ONE_STATEMENT), parameter);
  }

  protected final <E> E selectOne(String parameter) {
    return sqlSession.selectOne(getStatementId(PkgConst.QUERY_ONE_STATEMENT), parameter);
  }

  /**
   * 查询一条
   *
   * @param sqlId sql的id
   * @param parameter 多个参数
   */
  protected final <E> E selectOne(String sqlId, Map parameter) {
    return sqlSession.selectOne(getStatementId(sqlId), parameter);
  }

  protected final <E> E selectOne(String sqlId, String parameter) {
    return sqlSession.selectOne(getStatementId(sqlId), parameter);
  }

  protected final <E> E selectOne(String sqlId, Number parameter) {
    return sqlSession.selectOne(getStatementId(sqlId), parameter);
  }

  protected final <E> E selectOne(String sqlId, T parameter) {
    return sqlSession.selectOne(getStatementId(sqlId), parameter);
  }

  protected final <E> List<E> selectList(String sqlId) {
    List<E> result = sqlSession.selectList(getStatementId(sqlId));
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> selectList(String sqlId, Map parameter) {
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter);
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> selectList(String sqlId, Collection parameter) {
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter);
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> selectList(String sqlId, String parameter) {
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter);
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> selectList(String sqlId, Number parameter) {
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter);
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> selectList(String sqlId, T parameter) {
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter);
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> selectPageList(String sqlId, int offset, int limit, Map parameter) {
    if (limit < 0) {
      throw new RiskException("分页大小" + limit + "小于0");
    }
    RowBounds rb = new RowBounds(offset, limit);
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter,
        rb);
    return result != null ? result : Collections.<E>emptyList();
  }

  /**
   * 回调方式查询
   */
  protected final void select(String sqlId, final ResultHandler resultHandler,
      String parameter) {
    Objects.requireNonNull(resultHandler);
    sqlSession.select(getStatementId(sqlId), parameter, resultHandler);
  }

  protected final void select(String sqlId, final ResultHandler resultHandler,
      Number parameter) {
    Objects.requireNonNull(resultHandler);
    sqlSession.select(getStatementId(sqlId), parameter, resultHandler);
  }

  protected final void select(String sqlId, final ResultHandler resultHandler,
      Map parameter) {
    Objects.requireNonNull(resultHandler);
    sqlSession.select(getStatementId(sqlId), parameter, resultHandler);
  }

  private static class GenericUtils {
    private GenericUtils() {
    }

    /**
     * 取得泛型参类型
     *
     * @param clazz 泛型类
     * @param index 泛型参数索引
     * @return 泛型类型
     */
    private static Class getGenericClass(Class clazz, int index) {
      Type genType = clazz.getGenericSuperclass();
      if (genType instanceof ParameterizedType) {
        Type[] params = ((ParameterizedType) genType)
            .getActualTypeArguments();

        if ((params != null) && (params.length > index)) {
          return (Class) params[index];
        }
      }
      return null;
    }

    /**
     * 取得第一个泛型类型
     *
     * @param clazz 泛型类
     * @return 泛型类型
     */
    private static Class getGenericClass(Class clazz) {
      return getGenericClass(clazz, 0);
    }
  }
}