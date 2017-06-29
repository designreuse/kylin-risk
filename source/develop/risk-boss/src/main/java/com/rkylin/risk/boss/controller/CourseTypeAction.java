package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.CourseType;
import com.rkylin.risk.core.service.CourseTypeService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wjr on 2016-8-24. 课程类型Action
 */
@Controller
@RequestMapping("courseType")
@Slf4j
public class CourseTypeAction {

  /*
  增删改查
   */
  @Resource
  private CourseTypeService courseTypeService;

  @RequestMapping("toQueryCourseType")
  public ModelAndView toQueryCourseType() {
    return new ModelAndView("courseType/courseTypeQuery");
  }

  /**
   * 跳转到添加页面
   */
  @RequestMapping("toAddCourseType")
  public ModelAndView toAddCourseType() {
    return new ModelAndView("courseType/courseTypeModify");
  }

  /**
   * 跳转到修改页面
   */
  @RequestMapping("queryCourseTypeDetail") ModelAndView queryCourseTypeDetail(
      @RequestParam Integer id) {
    ModelAndView view = new ModelAndView("courseType/courseTypeModify");
    CourseType courseType = null;
    if (!StringUtils.isEmpty(id)) {
      courseType = courseTypeService.get(id);
    }
    view.addObject("courseType", courseType);
    return view;
  }
}
