package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Course;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.service.CourseService;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.MerchantService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wanglingzhi on 2016-8-24.
 */
@RequestMapping("course")
@Controller
public class CourseAction {
  @Resource
  private CourseService courseService;

  @Resource
  private MerchantService merchantService;

  @Resource
  DictionaryService dictionaryService;

  @RequestMapping("toQueryCourse")
  public ModelAndView toQueryCourse() {
    return new ModelAndView("course/courseQuery");
  }

  @RequestMapping("toAjaxAddCourse")
  public ModelAndView toAddRule() {
    ModelAndView view = new ModelAndView("course/courseAdd");
    List<Merchant> merchantList = merchantService.queryAllMerchant();
    view.addObject("merchants", merchantList);
    return view;
  }

  @RequestMapping("toAjaxCourseDetail")
  public ModelAndView updateCourse(Integer id) {
    ModelAndView view = new ModelAndView("course/courseModify");
    Course course = courseService.queryById(id);
    DictionaryCode dictcode = dictionaryService.queryByDictAndName("stuage", course.getStuAge());
    if (dictcode != null && !StringUtils.isEmpty(dictcode.getCode())) {
      course.setStuAge(dictcode.getCode());
    }
    List<Merchant> merchant = merchantService.queryAllMerchant();
    view.addObject("course", course);
    view.addObject("merchants", merchant);
    return view;
  }
}
