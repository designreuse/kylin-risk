package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.CreditBiz;
import com.rkylin.risk.core.dto.CreditParam;
import com.rkylin.risk.core.dto.CreditRequestEntity;
import com.rkylin.risk.core.entity.Authorization;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjr on 2016-12-12. 银联智策查询Action
 */
@RestController
@RequestMapping("/api/1/unionPayAdvisors")
@Slf4j
public class UnionPayAdvisorsRestAction {

  @Resource
  private CreditBiz creditBiz;

  //跳转列表页面
  @RequestMapping("queryUnionPayAdvisors")
  @ResponseBody
  public String queryUnionPayAdvisors(HttpServletRequest request, @RequestParam String bankCard,@RequestParam String queryAgain)
      throws IOException {
    // 接口
    // http://localhost:8080/service/api/credit/unionpay
    log.info("银联智策查询调用银联智策接口");
    CreditRequestEntity createReqEntity = new CreditRequestEntity();
    CreditParam creditParam = new CreditParam();
    createReqEntity.setBankCard(bankCard);
    creditParam.setQueryAgain(Boolean.getBoolean(queryAgain));
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    creditParam.setQuerier(auth.getRealname());
    List<CreditRequestEntity> list = new ArrayList();
    list.add(createReqEntity);
    creditParam.setData(list);
    log.info("银行卡号："+bankCard+"是否查询银联智策接口："+queryAgain+"操作人："+auth);
    return creditBiz.requestProxy("/api/credit/unionpay", creditParam);

  }
}
