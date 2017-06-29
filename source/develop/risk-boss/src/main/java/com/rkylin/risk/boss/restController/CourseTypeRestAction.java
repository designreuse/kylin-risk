package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.CourseType;
import com.rkylin.risk.core.service.CourseTypeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjr on 2016-8-24. 课程类型Action
 */
@Slf4j
@RestController
@RequestMapping("/api/1/courseType")
public class CourseTypeRestAction {

  @Resource
  private CourseTypeService courseTypeService;

  /*
  添加修改课程类型
   */
  @RequestMapping("updateCourseType")
  public CourseType updateCourseType(@ModelAttribute CourseType courseType) {
    if (courseType != null && !StringUtils.isEmpty(courseType.getId())) {
      courseTypeService.update(courseType);
    } else {
      courseTypeService.insert(courseType);
    }
    return new CourseType();
  }

  @RequestMapping("deleteCourseType")
  public CourseType deleteCourseType(@RequestParam String id) {
    courseTypeService.deleteCourseType(id);
    return new CourseType();
  }

  @RequestMapping("checkTypeName")
  public boolean checkTypeName(@RequestParam String coursetypename,
      HttpServletRequest request) {
    if (!StringUtils.isEmpty(coursetypename)) {
      CourseType courseType = new CourseType();
      courseType.setCoursetypename(coursetypename);
      int i = courseTypeService.checkExistTypeName(courseType);
      if (i > 0) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }
}
