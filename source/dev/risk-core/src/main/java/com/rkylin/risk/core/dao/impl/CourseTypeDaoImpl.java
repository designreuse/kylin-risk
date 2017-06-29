package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CourseTypeDao;
import com.rkylin.risk.core.entity.CourseType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjr on 2016-8-24.
 */
@Repository("courseTypeDao")
public class CourseTypeDaoImpl extends BaseDaoImpl<CourseType> implements CourseTypeDao {
  @Override
  public CourseType insert(CourseType courseType) {
    super.add(courseType);
    return courseType;
  }

  @Override
  public CourseType update(CourseType courseType) {
    super.modify(courseType);
    return courseType;
  }

  @Override
  public int delete(Integer id) {
    return super.del(id);
  }

  @Override
  public CourseType get(Integer id) {
    return super.get(id);
  }

  @Override
  public List<CourseType> queryAll(CourseType courseType) {
    return super.selectList("queryAll", courseType);
  }

  @Override
  public List querybycondition(Map map) {
    return super.selectList("querybycondition", map);
  }

  @Override
  public int checkExistTypeName(CourseType courseType) {
    return super.selectList("checkExistTypeName", courseType).size();
  }
}
