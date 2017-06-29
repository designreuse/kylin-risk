package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Course;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.CourseService;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.MerchantService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglingzhi on 2016-8-24.
 */

@RestController
@RequestMapping("api/1/course")
public class CourseRestAction {

  @Resource
  private CourseService courseService;

  @Resource
  DictionaryService dictionaryService;

  @Resource
  private MerchantService merchantService;

  @RequestMapping("deleteCourse")
  public Course deleteCourse(@RequestParam String id) {
    courseService.deleteCourse(id);
    return new Course();
  }

  @RequestMapping("addCourse")
  public Course addCourse(@RequestParam String courseType, @RequestParam String merchantName,
      @ModelAttribute Course course) {
    checkCourseParams(course);
    course.setCourseType(courseType);
    course.setMerchantName(merchantName);
    courseService.addCourse(course);
    return new Course();
  }

  @RequestMapping("updateCourse")
  public Course updateCourse(@RequestParam String courseType, @RequestParam String merchantName,
      @ModelAttribute Course course) {
    checkCourseParams(course);
    course.setCourseType(courseType);
    course.setMerchantName(merchantName);
    courseService.updateCourse(course);
    return new Course();
  }

  public void checkCourseParams(Course course) {
    DictionaryCode dictcode = dictionaryService.queryByDictAndCode("stuage", course.getStuAge());
    if (dictcode != null && !StringUtils.isEmpty(dictcode.getName())) {
      course.setStuAge(dictcode.getName());
    }
    List<Course> courselist = courseService.queryCourseByCondition(course);
    if (courselist.size() > 0) {
      throw new RiskRestErrorException("课程信息已经存在，请重新填写数据。");
    }
  }
}

