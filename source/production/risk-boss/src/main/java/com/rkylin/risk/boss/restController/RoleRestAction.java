package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.service.RoleService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 201507270241 on 2015/9/10.
 */
@RestController
@RequestMapping("/api/1/role")
public class RoleRestAction {

  @Resource
  private RoleService roleService;

  /**
   * 更新角色信息
   */
  @RequestMapping("updateRole")
  public Role update(@RequestParam String funchoose, String menuchoose, @ModelAttribute Role role) {
    roleService.update(funchoose, menuchoose, role);
    return new Role();
  }

  /**
   * 添加角色信息
   */
  @RequestMapping("addRole")
  public Role insert(@RequestParam String funchoose, String menuchoose, @ModelAttribute Role role) {
    roleService.insert(funchoose, menuchoose, role);
    return new Role();
  }

  /**
   * 删除
   */
  @RequestMapping("deleteRole")
  public Role delete(@RequestParam Short[] ids) {
    roleService.delete(ids);
    return new Role();
  }
}
