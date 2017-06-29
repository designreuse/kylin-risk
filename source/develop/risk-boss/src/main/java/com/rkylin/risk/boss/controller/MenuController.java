package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Menu;
import com.rkylin.risk.core.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 201508031790 on 2015/8/12.
 */
@Slf4j
@Controller("menuController")
@RequestMapping("menu")
public class MenuController {
  @Resource
  private MenuService menuService;

  @RequestMapping("toAddMenu")
  public ModelAndView toAddMenu(String targetParentId, String targetParentLevel) {
    ModelAndView modelAndView = new ModelAndView();
    Menu menu = menuService.findById(Short.valueOf(targetParentId));
    modelAndView.addObject("targetParentId", targetParentId);
    modelAndView.addObject("targetParentLevel", targetParentLevel);
    modelAndView.addObject("parentNodeName", menu.getMenuname());
    modelAndView.setViewName("menu/menuAdd");
    return modelAndView;
  }

  @RequestMapping("toMenuModify")
  public ModelAndView toMenuModify(String id) {
    Menu menu = menuService.findById(Short.valueOf(id));
    ModelAndView view = new ModelAndView("menu/menuModify");
    view.addObject("menu", menu);
    return view;
  }

  @RequestMapping("toQueryMenu")
  public ModelAndView topage() {
    return new ModelAndView("menu/menuQuery");
  }

  @RequestMapping("menuQuery")
  public ModelAndView loadMenu() {
    String sbf = menuService.findMenuInTree();
    sbf = "[" + sbf + "]";
    ModelAndView view = new ModelAndView("menu/menuQueryResult");
    view.addObject("menuTreeJSONString", sbf);
    return view;
  }

  @RequestMapping("menuAdd")
  public ModelAndView addMenu(@ModelAttribute Menu menu) {
    Menu newmenu = new Menu();
    newmenu.setMenuname(menu.getMenuname());
    newmenu.setMenulevel(menu.getMenulevel());
    newmenu.setMenuurl(menu.getMenuurl());
    newmenu.setDisplayorder(menu.getDisplayorder());
    newmenu.setParentid(menu.getParentid());
    newmenu.setStatus(menu.getStatus());
    Menu addMenu = menuService.addMenu(newmenu);
    ModelAndView view = new ModelAndView("menu/menuAddSuccess");
    view.addObject("menu", addMenu);
    return view;
  }

  @RequestMapping("menuModify")
  public ModelAndView menuModify(@ModelAttribute Menu menu) {
    if (menu != null) {
      menuService.updateMenu(menu);
    }
    ModelAndView view = new ModelAndView("menu/menuAddSuccess");
    view.addObject("menu", menu);
    return view;
  }

  @RequestMapping("menuDelete")
  public void deleteMenu(String id, HttpServletResponse response) {
    menuService.deleteMenuById(Short.valueOf(id));
    PrintWriter printWriter = null;
    try {
      printWriter = response.getWriter();
      printWriter.flush();
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    } finally {
      if (printWriter != null) {
        printWriter.close();
      }
    }
  }
}
