package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Functions;
import com.rkylin.risk.core.entity.Menu;
import com.rkylin.risk.core.service.FunctionService;
import com.rkylin.risk.core.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/12.
 */
@Controller
@RequestMapping("function")
public class FunctionsAction {

  @Resource
  private FunctionService functionService;
  @Resource
  private MenuService menuService;

  /**
   * 查询功能跳转
   */
  @RequestMapping("toQueryFunction")
  public ModelAndView toQueryFunction(@ModelAttribute Functions functions) {
    ModelAndView view = new ModelAndView("function/functionQuery");
    view.addObject("functions", functions);
    return view;
  }

  /**
   * 查询详细信息
   */
  @RequestMapping("toAjaxGetFunctionDetail")
  public ModelAndView queryDetail(@ModelAttribute Functions functions, @RequestParam String ids,
      String dealType) {
    Functions function = functionService.queryOne(Short.parseShort(ids));
    List<Menu> menuList = menuService.findByLevel("2");
    ModelAndView view = new ModelAndView("function/functionDetail");
    view.addObject("function", function);
    //dealType  modify---->修改  query---->查询
    view.addObject("dealType", dealType);
    view.addObject("functions", functions);
    view.addObject("menuList", menuList);
    return view;
  }

  /**
   * 添加功能跳转
   */
  @RequestMapping("toAjaxAddFunction")
  public ModelAndView toInsert() {
    List<Menu> menuList = menuService.findByLevel("2");
    ModelAndView view = new ModelAndView("function/functionAdd");
    view.addObject(menuList);
    return view;
  }
}
