package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CourseType;

import java.util.List;
import java.util.Map;

/**
 * Created by wjr on 2016-8-24.
 */
public interface CourseTypeDao {
  /**
   * 添加课程类型
   */
  CourseType insert(CourseType courseType);

  /**
   * 修改课程类型
   */
  CourseType update(CourseType courseType);

  /**
   * 根据表主键删除一条数据
   */
  int delete(Integer id);

  /**
   * 根据主键查询数据
   */
  CourseType get(Integer id);

  /**
   * 查询所有课程类型
   */
  List<CourseType> queryAll(CourseType courseType);

  List querybycondition(Map map);

  /**
   * 根据课程名称查询数据
   */
  int checkExistTypeName(CourseType courseType);
}
