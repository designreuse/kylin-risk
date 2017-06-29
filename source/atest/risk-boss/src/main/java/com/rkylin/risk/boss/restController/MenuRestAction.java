package com.rkylin.risk.boss.restController;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkylin.risk.core.entity.Menu;
import com.rkylin.risk.core.service.MenuService;

/**
 * Created by 201508031790 on 2015/9/1.
 */

@RestController
@RequestMapping("/api/1/menu")
public class MenuRestAction {
  @Resource
  private MenuService menuService;

  @RequestMapping("menuAdd")
  public Menu addMenu(@ModelAttribute Menu menu) {
    Menu newmenu = new Menu();
    newmenu.setMenuname(menu.getMenuname());
    newmenu.setMenulevel(menu.getMenulevel());
    newmenu.setMenuurl(menu.getMenuurl());
    newmenu.setDisplayorder(menu.getDisplayorder());
    newmenu.setParentid(menu.getParentid());
    newmenu.setStatus(menu.getStatus());
    Menu addMenu = menuService.addMenu(newmenu);

    return addMenu;
  }

  @RequestMapping("menuModify")
  public Menu menuModify(@ModelAttribute Menu menu) {
    if (menu != null) {
      menuService.updateMenu(menu);
    }

    return menu;
  }

  @RequestMapping("menuDelete")
  public void deleteMenu(String id) {
    menuService.deleteMenuById(Short.valueOf(id));
  }
}
