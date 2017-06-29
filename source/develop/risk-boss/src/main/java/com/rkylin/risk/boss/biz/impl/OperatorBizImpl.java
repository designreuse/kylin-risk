package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.OperatorBiz;
import com.rkylin.risk.boss.util.PasswordHash;
import com.rkylin.risk.boss.util.RandomUtil;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Functions;
import com.rkylin.risk.core.entity.Loginlog;
import com.rkylin.risk.core.entity.Menu;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.exception.RiskException;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.exception.RiskRestObjectIsNullException;
import com.rkylin.risk.core.service.FunctionService;
import com.rkylin.risk.core.service.LoginLogService;
import com.rkylin.risk.core.service.MenuService;
import com.rkylin.risk.core.service.OperatorService;
import com.rkylin.risk.core.service.RoleService;
import com.rkylin.risk.core.utils.MailUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

/**
 * @company: rkylin version: 1.0
 */
@Slf4j
@Component("operatorBiz")
public class OperatorBizImpl implements OperatorBiz {
  @Resource
  private OperatorService operatorService;
  @Resource
  private LoginLogService loginLogService;
  @Resource
  private RoleService roleService;
  @Resource
  private FunctionService functionService;
  @Resource
  private MenuService menuService;
  @Resource
  private MailUtil mailUtil;

  @Override
  public Authorization login(String username, String passwd, String ipAddress) throws
      RiskException {
    Operator operator = operatorService.queryOperatorByUsername(username);
    //用户名不存在
    if (operator == null) {
      throw new RiskException("nouser");
    }
    //密码错误
    try {
      if (!PasswordHash.validatePassword(passwd, operator.getPasswd())) {
        if (!"admin".equals(operator.getUsername())) {
          operator = setErrorTimes(operator);
        }
        throw new RiskException("wrong");
      }
    } catch (Exception e) {
      throw new RiskException("wrong");
    }
    //管理员不做账号验证
    if (!"admin".equals(operator.getUsername())) {
      //          用户状态失效
      if (operator.getStatus().equals(Constants.INACTIVE)) {
        throw new RiskException("wrongstatus");
      }
      //          密码失效时间超出
      if (operator.getPasswdexpdate().compareTo(LocalDate.now()) < 1) {
        throw new RiskException("passwdexp");
      }
    }
    Loginlog loginLog = createLoginLog(operator, ipAddress);
    Authorization auth = new Authorization();
    auth.setLogid(loginLog.getId());
    auth.setUserId(operator.getId());
    auth.setUsername(operator.getUsername());
    auth.setOperatorType(operator.getOpertype());
    auth.setRealname(operator.getRealname());
    auth.setIpAddress(ipAddress);
    auth.setPasswdexpdate(operator.getPasswdexpdate());
    List<Role> roles = roleService.getOperatorRole(operator.getId());
    auth.setRoles(roles);
    auth.setLoginTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    auth.setResources(getResources(roles));    // 获取资源的集合
    auth.setMenu(getMenu(roles));            // 获取菜单信息
    if (!StringUtils.isEmpty(operator.getProducts())) {
      String[] products = operator.getProducts().split(",");
      auth.setProducts(products);
    }
    return auth;
  }

  @Override
  public Operator addNewOperator(Operator operator, String roleIds,
      Authorization auth, String productId) {
    if (roleIds == null) {
      throw new RiskRestObjectIsNullException("角色不能为空");
    }
    String pwd = RandomUtil.random();
    operator.setCreateoper(auth.getUsername());
    operator.setEmail(operator.getUsername() + "@rongcapital.cn");
    operator.setUsername(operator.getUsername() + "@rongcapital.cn");
    try {
      String password = PasswordHash.createHash(pwd);
      operator.setPasswd(password);
    } catch (Exception e) {
      throw new RiskRestErrorException("系统错误!");
    }
    operator.setPasswdeffdate(operator.getPasswdeffdate());
    operator.setPasswdexpdate(operator.getPasswdeffdate().plusDays(Constants.PWDEXPDATE));
    operator.setCreatetime(DateTime.now());
    if (operator.getStatus() != null) {
      operator.setStatus(Constants.ACTIVE);
    } else {
      operator.setStatus(Constants.INACTIVE);
    }
    String[] roleArray = roleIds.split(",");
    operator.setProducts(productId.startsWith(",") ? productId.substring(1) : productId);
    operatorService.addNewOperator(operator, roleArray);

    //创建邮件
    Map map = new HashMap();
    map.put("userName", operator.getRealname());
    map.put("password", pwd);
    MailBean mailBean = MailBean.builder()
        .data(map)
        .template(Constants.USER_PASSWORD_MAIL_TEMPLATE)
        .toEmails(new String[] {operator.getEmail()})
        .subject("风控系统密码")
        .build();

    //发送邮件
    try {
      mailUtil.send(mailBean);
    } catch (MessagingException e) {
      throw new RiskRestErrorException("系统错误!");
    }
    return operator;
  }

  @Override
  public Operator updatePassWord(Operator operator) {
    short restTime = 0;
    try {
      operator.setPasswd(PasswordHash.createHash(Constants.DEFAULT_PWD));
    } catch (Exception e) {
      e.printStackTrace();
    }

    operator.setErrtimes(restTime);
    return operatorService.updatePassWord(operator);
  }

  @Override
  public boolean queryPassWord(String oldpwd, String username) {
    Operator oper = operatorService.queryOperatorByUsername(username);
    if (oper != null) {
      try {
        return PasswordHash.validatePassword(oldpwd, oper.getPasswd());
      } catch (Exception e) {
        log.error("密码校验异常", e);
        throw new RiskException("密码校验异常");
      }
    }
    return false;
  }

  public Operator setErrorTimes(Operator operator) {
    //密码错误次数限制
    int pwderrtimes = Constants.PWDERRTIMES;
    //密码错误时间周期限制
    int pwderrdate = Constants.PWDERRDATE;
    //上次密码错误时间距当前时间超过一天，重置错误时间和错误次数

    if (operator.getLastpwderrdate() == null || !LocalDate.now()
        .minusDays(pwderrdate)
        .isEqual(operator.getLastpwderrdate())) {
      operator.setLastpwderrdate(LocalDate.now());
      operator.setErrtimes((short) 1);
      operatorService.update(operator);
      throw new RiskException("wrong");
    }
    //同一时间错误，不足5次，则错误次数加1
    if (LocalDate.now().minusDays(pwderrdate).isEqual(operator.getLastpwderrdate())
        && operator.getErrtimes() < (short) pwderrtimes) {
      operator.setErrtimes((short) (operator.getErrtimes() + (short) 1));
      operatorService.update(operator);
      throw new RiskException("wrong");
    }
    //同一时间错误，等于5次，则锁定
    if (LocalDate.now().minusDays(pwderrdate).isEqual(operator.getLastpwderrdate())
        && operator.getErrtimes() == (short) pwderrtimes) {
      operator.setStatus(Constants.INACTIVE);
      operatorService.update(operator);
      throw new RiskException("wrongmoretimes");
    }
    return operator;
  }

  // 角色资源转换，生成资源(action)的集合
  private List getResources(List<Role> roles) {

    List<String> resources = new ArrayList<String>();
    for (Role role : roles) {
      if (Constants.ACTIVE.equals(role.getStatus())) {
        List<Functions> functions = functionService.queryByRole(role.getId());
        for (Functions function : functions) {
          if (Constants.ACTIVE.equals(function.getStatus())) {
            String actionName = function.getUrl();
            if (StringUtils.isNotBlank(actionName)) {
              resources.add(actionName);
            }
          }
        }
      }
    }
    return resources;
  }

  // 生成系统菜单
  private Menu getMenu(List<Role> roles) {

    Menu rootMenu = new Menu();                // ROOT
    Map<Short, Menu> level1 = new HashMap<Short, Menu>();  // 一级菜单
    Map<Short, Menu> level2 = new HashMap<Short, Menu>();  // 二级菜单
    for (Role role : roles) {
      if (Constants.ACTIVE.equals(role.getStatus())) {

        List<Menu> menus = menuService.queryMeunByRole(role.getId());
        for (Menu menu : menus) {
          if (Constants.ACTIVE.equals(menu.getStatus())) {
            int level = Integer.parseInt(menu.getMenulevel());
            switch (level) {
              case 0:
                rootMenu = menu;
                break;
              case 1:
                level1.put(menu.getId(), menu);
                break;
              case 2:
                level2.put(menu.getId(), menu);
                break;
              default:
                throw new UnsupportedOperationException("不支持" + level);
            }
          }
        }
      }
    }
    List<Menu> list = new ArrayList<Menu>();  // 一级菜单

    for (Map.Entry<Short, Menu> entry1 : level1.entrySet()) {
      Short id = entry1.getKey();
      Menu menu = entry1.getValue();
      List<Menu> childList = new ArrayList<Menu>(); // 二级菜单

      for (Map.Entry<Short, Menu> entry2 : level2.entrySet()) {
        Menu subMenu = entry2.getValue();
        if (id.equals(subMenu.getParentid())) {
          childList.add(subMenu);
        }
      }

      Collections.sort(childList, menu);
      menu.setChildren(childList);
      list.add(menu);
    }

    Collections.sort(list, rootMenu);
    rootMenu.setChildren(list);
    return rootMenu;
  }

  // 创建登录日志
  public Loginlog createLoginLog(Operator operator, String requestIp) {
    Loginlog loginLog = new Loginlog();
    loginLog.setUsername(operator.getUsername());
    loginLog.setLoginip(requestIp);
    loginLog.setLogintime(DateTime.now());
    loginLog.setOperatorid(operator.getId());
    return loginLogService.insert(loginLog);
  }
}
