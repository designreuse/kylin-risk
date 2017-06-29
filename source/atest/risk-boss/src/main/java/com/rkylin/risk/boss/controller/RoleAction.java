package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Functions;
import com.rkylin.risk.core.entity.Menu;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.entity.RoleFunction;
import com.rkylin.risk.core.entity.RoleMenu;
import com.rkylin.risk.core.service.FunctionService;
import com.rkylin.risk.core.service.MenuService;
import com.rkylin.risk.core.service.RoleFunctionService;
import com.rkylin.risk.core.service.RoleMenuService;
import com.rkylin.risk.core.service.RoleService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 201507270241 on 2015/8/19.
 */
@Controller
@RequestMapping("role")
public class RoleAction {
  @Resource
  private RoleService roleService;
  @Resource
  private MenuService menuService;
  @Resource
  private FunctionService functionService;
  @Resource
  private RoleFunctionService roleFunctionService;
  @Resource
  private RoleMenuService roleMenuService;

  @RequestMapping("toQueryRole")
  public ModelAndView toQueryRole(@ModelAttribute Role roles) {
    ModelAndView view = new ModelAndView("role/roleQuery");
    view.addObject("roles", roles);
    return view;
  }

  @RequestMapping("toAjaxAddRole")
  public ModelAndView toAddRole() {
    List<Functions> functionsList = functionService.queryAll();
    //获取所有菜单信息
    List<Menu> menuList = menuService.queryAllMenu();
    //一级菜单
    List<Menu> menuFirstLev = new ArrayList<Menu>();
    //二级菜单
    List<Menu> menuSecondLev = new ArrayList<Menu>();
    for (int i = 0; i < menuList.size(); i++) {
      if ("1".equals(menuList.get(i).getMenulevel())) {
        menuFirstLev.add(menuList.get(i));
      }
      if ("2".equals(menuList.get(i).getMenulevel())) {
        menuSecondLev.add(menuList.get(i));
      }
    }
    ModelAndView view = new ModelAndView("role/roleAdd");
    view.addObject("functionsList", functionsList);
    view.addObject("menuFirstLev", menuFirstLev);
    view.addObject("menuSecondLev", menuSecondLev);
    return view;
  }

  /**
   * 查询角色详细信息
   */
  @RequestMapping("toAjaxGetRoleDetail")
  public ModelAndView queryOne(@ModelAttribute Role roles, @RequestParam Short ids,
      String dealType) {
    List<RoleFunction> roleFunctions = roleFunctionService.queryAll(ids);
    Set functionids = new HashSet();
    for (RoleFunction roleFunction : roleFunctions) {
      functionids.add(roleFunction.getFunctionid());
    }
    List<RoleMenu> roleMenus = roleMenuService.queryAll(ids);
    Set menuids = new HashSet();
    for (RoleMenu roleMenu : roleMenus) {
      menuids.add(roleMenu.getMenuid());
    }

    List<Functions> functionsList = functionService.queryAll();
    for (Functions function : functionsList) {
      if (functionids.contains(function.getId())) {
        //利用此属性判断当前角色是否有此功能
        function.setUrl("1");
      } else {
        function.setUrl("2");
      }
    }
    //获取所有菜单信息
    List<Menu> menuList = menuService.queryAllMenu();
    //一级菜单
    List<Menu> menuFirstLev = new ArrayList<Menu>();
    //二级菜单
    List<Menu> menuSecondLev = new ArrayList<Menu>();
    for (int i = 0; i < menuList.size(); i++) {
      if (menuids.contains(menuList.get(i).getId())) {
        menuList.get(i).setMenuurl("1");
      } else {
        menuList.get(i).setMenuurl("2");
      }
      if ("1".equals(menuList.get(i).getMenulevel())) {
        menuFirstLev.add(menuList.get(i));
      }
      if ("2".equals(menuList.get(i).getMenulevel())) {
        menuSecondLev.add(menuList.get(i));
      }
    }
    Role role = roleService.queryOne(ids);
    ModelAndView view = new ModelAndView("role/roleDetail");
    //dealType--->操作类型  modify---->修改  query---->查询
    view.addObject("dealType", dealType);
    view.addObject("role", role);
    //查询条件
    view.addObject("roles", roles);
    view.addObject("functionsList", functionsList);
    view.addObject("menuFirstLev", menuFirstLev);
    view.addObject("menuSecondLev", menuSecondLev);
    return view;
  }
}
