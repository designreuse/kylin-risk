package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CourseTypeDao;
import com.rkylin.risk.core.entity.CourseType;
import com.rkylin.risk.core.service.CourseTypeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by wjr on 2016-8-24.
 */
@Service("courseTypeService")
public class CourseTypeServiceImpl implements CourseTypeService {
  @Resource
  private CourseTypeDao courseTypeDao;

  @Override
  public CourseType insert(CourseType courseType) {
    return courseTypeDao.insert(courseType);
  }

  @Override
  public CourseType update(CourseType courseType) {
    return courseTypeDao.update(courseType);
  }

  @Override
  public void deleteCourseType(String ids) {
    if (!org.apache.commons.lang.StringUtils.isEmpty(ids)) {
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        courseTypeDao.delete(new Integer(id));
      }
    }
  }

  @Override
  public CourseType get(Integer id) {
    return courseTypeDao.get(id);
  }

  @Override
  public List<CourseType> queryAll(CourseType courseType) {
    return courseTypeDao.queryAll(courseType);
  }

  @Override
  public List<Map> queryByCondition(CourseType courseType, String customername) {
    Map map = new HashMap();
    map.put("courseType", courseType);
    map.put("customername", customername);
    return courseTypeDao.querybycondition(map);
  }

  @Override
  public int checkExistTypeName(CourseType courseType) {
    if (courseType != null) {
      return courseTypeDao.checkExistTypeName(courseType);
    }
    return 1;
  }
}
