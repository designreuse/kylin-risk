package com.rkylin.risk.boss.intercept;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.service.LoginLogService;
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * @company: rkylin version: 1.0
 */
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

  @Setter
  private Set<String> whiteList;
  @Setter
  private Set<String> loginWhiteList;
  @Resource
  private LoginLogService loginLogService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    HttpSession session = request.getSession();
    String servletPath = request.getServletPath();
    String contextPath = request.getContextPath();
    String actionName = "";
    //截取action名称
    if ("/".equals(servletPath)) {
      actionName = "/";
    } else {
      int begin = servletPath.lastIndexOf("/");
      if (begin == -1) {
        begin = 0;
      }
      actionName = servletPath.substring(begin + 1);
    }
    //如果没有action后缀则添加action后缀
    if (actionName.indexOf(".action") == -1) {
      actionName += ".action";
    }
    //排除登录白名单
    if (loginWhiteList.contains(actionName)) {
      return true;
    }

    //Session过期或无Session
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    if (auth == null) {
      //session过期
      response.sendRedirect(contextPath + "/noSession.action");
      return false;
    }

    //被迫登出
    if (!loginLogService.findIsAvailableSessionId(auth.getLogid())) {
      if (auth.getLogid() != 0) {
        session.removeAttribute("auth");
        //跳转登录
        request.getRequestDispatcher(contextPath + "/login.action").forward(request, response);
        return false;
      }
    }
    //白名单验证
    if (whiteList.contains(actionName)) {
      return true;
    }

    //全局权限开关检查
    String authFlag = Constants.AUTH_FLAG;
    //全局权限检查
    if ("FALSE".equals(authFlag)) {
      return true;
    }

    // 系统管理员不进行权限检查
    if ("admin".equals(auth.getUsername())) {
      return true;
    }

    // 功能权限检查
    List resource = auth.getResources();
    if (!resource.contains(actionName)) {
      //权限验证不合格
      response.sendRedirect(contextPath + "/noPermit.action");
      return false;
    }
    return true;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (whiteList == null) {
      whiteList = Sets.newHashSet();
      whiteList.add("main.action");
    }
    if (loginWhiteList == null) {
      whiteList = Sets.newHashSet();
      whiteList.add("/");
      whiteList.add("login.action");
      whiteList.add("operatorLogin.action");
      whiteList.add("operatorLogout.action");
      whiteList.add("400.action");
      whiteList.add("500.action");
      whiteList.add("noSession.action");
      whiteList.add("noPermit.action");
      whiteList.add("topwdupdate.action");
      whiteList.add("pwdupdate.action");
      whiteList.add("checkPasswd.action");
      whiteList.add("createCode.action");
    }
  }
}
