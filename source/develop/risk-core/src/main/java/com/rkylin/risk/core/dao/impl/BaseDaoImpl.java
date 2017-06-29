package com.rkylin.risk.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
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

  {
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
  protected final void add(T entity) {
    add(PkgConst.INSERT_STATEMENT, entity);
  }

  /**
   * 增加实体
   *
   * @param sqlId sql语句id
   * @param entity 实体
   */
  protected final void add(String sqlId, T entity) {
    sqlSession.insert(getStatementId(sqlId), entity);
  }

  /**
   * 增加实体
   *
   * @param entity 实体
   */
  protected final int insertObject(T entity) {
    return sqlSession.insert(getStatementId(PkgConst.INSERT_STATEMENT), entity);
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

  /**
   * 删除
   */
  protected final int del(Serializable id) {
    return sqlSession.delete(getStatementId(PkgConst.DELETE_STATEMENT), id);
  }

  /**
   * 删除
   *
   * @param sqlId sql语句id
   * @param args 多个参数
   */
  protected final int del(String sqlId, Object... args) {
    Object parameter = wrapperParameter(args);
    return sqlSession.delete(getStatementId(sqlId), parameter);
  }

  /**
   * 更新实体
   */
  protected final int modify(T entity) {
    return sqlSession.update(
        getStatementId(PkgConst.UPDATE_STATEMENT), entity);
  }

  /**
   * 更新实体
   *
   * @param sqlId sql语句的id
   * @param arg 多个参数
   */
  protected final int modify(String sqlId, Object... arg) {
    Object parameter = wrapperParameter(arg);
    return sqlSession.update(getStatementId(sqlId), parameter);
  }

  /**
   * 查询
   */
  protected final T get(Serializable id) {
    return (T) sqlSession.selectOne(
        getStatementId(PkgConst.GET_STATEMENT), id);
  }

  /**
   * 查询所有
   */
  protected final List<T> queryAllList() {
    return sqlSession.selectList(
        getStatementId(PkgConst.QUERY_ALL_STATEMENT));
  }

  /**
   * 查询一条数据
   */
  protected final T queryOne(Object... arg) {
    return queryOne(PkgConst.QUERY_ONE_STATEMENT, arg);
  }

  /**
   * 查询一条
   *
   * @param sqlId sql的id
   * @param arg 多个参数
   */
  protected final <E> E queryOne(String sqlId, Object... arg) {
    Object parameter = wrapperParameter(arg);
    E t = sqlSession.selectOne(getStatementId(sqlId), parameter);

    return t;
  }

  /**
   * 查询
   *
   * @param sqlId sql语句的id
   * @param arg 查询的参数
   * @return 实体对象集合
   */
  protected final List<T> query(String sqlId, Object... arg) {
    return queryList(sqlId, arg);
  }

  private Object wrapperParameter(Object... arg) {
    Object parameter;
    if (arg == null || arg.length == 0) {
      parameter = null;
    } else if (arg.length == 1) {
      parameter = arg[0];
    } else {
      Map map = new HashMap();
      for (int i = 0; i < arg.length; i++) {
        map.put("" + i, arg[i]);
      }
      parameter = map;
    }
    return parameter;
  }

  protected final <E> List<E> queryList(String sqlId, Object... arg) {
    Object parameter = wrapperParameter(arg);
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter);
    return result != null ? result : Collections.<E>emptyList();
  }

  protected final <E> List<E> queryPageList(String sqlId, int offset, int limit, Object... arg) {
    if (limit < 0) {
      throw new RuntimeException("分页大小" + limit + "小于0");
    }
    RowBounds rb = new RowBounds(offset, limit);
    Object parameter = wrapperParameter(arg);
    List<E> result = sqlSession.selectList(getStatementId(sqlId), parameter,
        rb);
    return result != null ? result : Collections.<E>emptyList();
  }



  protected final <E> E query(String sqlId, final ResultCollectionHandler<E> handler,
      Object... arg) {
    Objects.requireNonNull(handler);
    Object parameter = wrapperParameter(arg);
    sqlSession.select(getStatementId(sqlId), parameter, new ResultHandler() {
      @Override public void handleResult(ResultContext resultContext) {
        handler.handle(resultContext.getResultObject());
      }
    });
    return handler.result();
  }


  private static class GenericUtils {
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