package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.CustomebwgList;
import com.rkylin.risk.core.service.CustomerbwgService;
import com.rkylin.risk.core.service.OperatorLogService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508031790 on 2015/9/10.
 */

@RestController
@RequestMapping("/api/1/customerbwg")
public class CustomerBwgRestAction {

  private static final String Cust = "CustomebwgList";
  @Resource
  private CustomerbwgService customerbwgService;
  @Resource
  private OperatorLogService operatorLogService;

  @RequestMapping(value = "customerbwgModify", method = RequestMethod.POST)
  public String customerbwgModify(String ids, String opertype, String reason,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String custIds = customerbwgService.update(ids, opertype, reason, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), Cust, "更新");
    return custIds;
  }

  @RequestMapping("addBWGfromCustom")
  public CustomebwgList addBWG(@ModelAttribute CustomebwgList bwg, String addIds,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    customerbwgService.addBWGFromCustom(addIds, bwg, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), Cust, "添加");
    return bwg;
  }

  @RequestMapping("deleteBWGfromCustom")
  public String delBwg(String deleteIds, HttpServletRequest request, String reason) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    customerbwgService.delCustbwg(deleteIds, auth, reason);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), Cust, "删除");
    return deleteIds;
  }

  @RequestMapping("customerbwgVerify")
  public String verifyBwg(@ModelAttribute CustomebwgList bwg, String ids,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String custIds = customerbwgService.verifybwg(ids, bwg, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), Cust, "修改");
    return custIds;
  }
}
