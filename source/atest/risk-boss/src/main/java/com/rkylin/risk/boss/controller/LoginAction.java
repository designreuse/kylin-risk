package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.biz.OperatorBiz;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.exception.RiskException;
import com.rkylin.risk.core.service.LoginLogService;
import com.rkylin.risk.core.service.OperatorService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 201506290344 on 2015/8/14.
 */
@Controller
@Slf4j
public class LoginAction {
  @Resource
  private OperatorBiz operatorBiz;
  @Resource
  private LoginLogService loginLogService;
  @Resource
  private OperatorService operatorService;

  @RequestMapping("operatorLogin")
  public ModelAndView login(@RequestParam String username, String passwd, String randomCode,
      HttpServletRequest request) {
    String ipAddress = request.getRemoteAddr();
    ModelAndView view = null;

    try {
      if (request.getSession().getAttribute("randomcode").equals(randomCode)) {
        Authorization auth = operatorBiz.login(username, passwd, ipAddress);
        request.getSession().setAttribute("auth", auth);
        //管理员不做账号验证
        if (!"admin".equals(auth.getUsername())) {
          //首次登录时，强制修改密码
          if (loginLogService.isFirstLogin(username)) {
            view = new ModelAndView("pwdexp");
            view.addObject("errormessage", "为了您账号的安全，首次登录系统将进行密码修改");
            view.addObject("modifyType", "first");
            view.addObject("username", username);
            return view;
          }

          //用户密码修改周期超出
          if (auth.getPasswdexpdate().minusDays(1).compareTo(LocalDate.now()) == 0) {
            view = new ModelAndView("pwdexp");
            view.addObject("errormessage", "您的密码距离到期时间还有1天，为了您账号的安全，将进行密码修改");
            view.addObject("modifyType", "first");
            view.addObject("username", username);
            return view;
          }
        }
        view = new ModelAndView("redirect:main.action");
        //密码距到期还有15天以内，提醒修改
        if (auth.getPasswdexpdate().minusDays(15).isBefore(LocalDate.now()) && !"admin".equals(
            auth.getUsername())) {
          //计算两个日期之间的差值
          int dDate = auth.getPasswdexpdate().getDayOfYear() - LocalDate.now().getDayOfYear();
          view.addObject("message", "您的密码距离到期时间还有" + dDate + "天，为了您账号的安全，请进行密码修改！");
        }
      } else {
        view = new ModelAndView("login");
        view.addObject("errormessage", "验证码错误");
        return view;
      }
    } catch (RiskException ex) {
      log.info(ex.getMessage(), ex);
      if ("passwdexp".equals(ex.getMessage())) {
        view = new ModelAndView("pwdexp");
        view.addObject("errormessage", "您的密码已经失效，为了保障您的账号安全，请修改密码");
        view.addObject("modifyType", "invalid");
        view.addObject("username", username);
        return view;
      }

      ModelAndView modelAndView = new ModelAndView("login");
      if ("nouser".equals(ex.getMessage())) {
        modelAndView.addObject("errormessage", "用户名不存在");
      }
      if ("wrong".equals(ex.getMessage())) {
        Operator operator = operatorService.queryOperatorByUsername(username);
        int i = 6 - ((operator.getErrtimes() != null) ? operator.getErrtimes() : 0);
        modelAndView.addObject("errormessage", "密码错误,您还有" + i + "次机会,输错5次后账号锁定");
      }
      if ("wrongmoretimes".equals(ex.getMessage())) {
        modelAndView.addObject("errormessage", "密码错误超过5次，账号已被锁定，请联系系统管理员解锁");
      }
      if ("wrongstatus".equals(ex.getMessage())) {
        modelAndView.addObject("errormessage", "用户状态异常");
      }
      return modelAndView;
    }
    return view;
  }

  @RequestMapping("operatorLogout")
  public ModelAndView logout(HttpServletRequest request) {
    request.getSession().removeAttribute("auth");
    return new ModelAndView("login");
  }
}
