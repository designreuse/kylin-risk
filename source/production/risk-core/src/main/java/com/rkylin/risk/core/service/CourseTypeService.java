package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CourseType;

import java.util.List;
import java.util.Map;

/**
 * 课程类型 Created by wjr on 2016-8-24.
 */
public interface CourseTypeService {
  /**
   * 添加课程类型
   */
  CourseType insert(CourseType courseType);

  /**
   * 修改课程类型
   */
  CourseType update(CourseType courseType);

  void deleteCourseType(String ids);

  /**
   * 根据主键查询数据
   */
  CourseType get(Integer id);

  /**
   * 查询所有课程类型
   */
  List<CourseType> queryAll(CourseType courseType);

  List<Map> queryByCondition(CourseType courseType, String coursetypeName);

  /**
   * 根据课程类型名称查询课程类型
   */
  int checkExistTypeName(CourseType courseType);
}
