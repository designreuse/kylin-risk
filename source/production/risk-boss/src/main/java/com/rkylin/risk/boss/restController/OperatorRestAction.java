package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.OperatorBiz;
import com.rkylin.risk.boss.util.PasswordHash;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.service.OperatorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 201508031790 on 2015/8/31.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/operator")
public class OperatorRestAction {
  @Resource
  private OperatorService operatorService;

  @Resource
  private OperatorBiz operatorBiz;

  @RequestMapping("addOperator")
  public Operator addOperator(@ModelAttribute Operator operator, String roleIds, String productId,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    return operatorBiz.addNewOperator(operator, roleIds, auth, productId);
  }

  @RequestMapping("deleteOper")
  public void deleteOper(String deleteIds) {
    operatorService.deleteOpers(deleteIds);
  }

  @RequestMapping("reSetPWD")
  public Operator resetPWD(String id) {
    Operator operator = new Operator();
    operator.setId(Short.valueOf(id));
    operator.setPasswdexpdate(LocalDate.now());
    operator = operatorBiz.updatePassWord(operator);
    return operator;
  }

  @RequestMapping("operModify")
  public Operator operatorModify(@ModelAttribute Operator operator,
      String roleIds, String productId) {
    return operatorService.update(operator, roleIds, productId);
  }

  @RequestMapping("updatePasswd")
  public Operator updatePasswd(@ModelAttribute Operator oper, HttpServletRequest request)
      throws InvalidKeySpecException, NoSuchAlgorithmException {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    oper.setId(auth.getUserId());
    oper.setPasswd(PasswordHash.createHash(oper.getPasswd()));
    return operatorService.updatePassWord(oper);
  }

  @RequestMapping("checkUserName")
  public void checkUserName(String username, HttpServletResponse response) {
    Operator oper = operatorService.queryOperatorByUsername(username + "@rkylin.com");
    String status = "false";
    if (oper != null) {
      status = "false";
    } else {
      status = "true";
    }
    PrintWriter printWriter = null;
    try {
      printWriter = response.getWriter();
      printWriter.write(String.valueOf(status));
      printWriter.flush();
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    } finally {
      if (printWriter != null) {
        printWriter.close();
      }
    }
  }

  @RequestMapping("checkPasswd")
  public void checkpwd(String oldpwd, HttpServletResponse response, @RequestParam String
      username) {
    PrintWriter printWriter = null;
    try {
      boolean result = operatorBiz.queryPassWord(oldpwd, username);

      printWriter = response.getWriter();
      printWriter.write(Boolean.toString(result));
      printWriter.flush();
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    } finally {
      if (printWriter != null) {
        printWriter.close();
      }
    }
  }
}
