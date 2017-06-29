package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Course;
import java.util.List;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
public interface CourseService {

  List<Course> queryByUserrelatedid(String userrelatedid);

  List<String> queryCourseNameByUserrelatedid(String userrelatedid);

  Course queryByMerchantidAndCourseName(String merchantid, String coursename);

  void addCourseBatch(List<Course> courses);

  Course queryById(Integer id);

  void addCourse(Course course);

  void deleteCourse(String id);

  void deleteByMerchantid(String merchantid);

  void updateCourse(Course course);

  List<Course>  queryCourseByCondition(Course  course);

}
