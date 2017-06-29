package com.rkylin.risk.service.credit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.CreditModule;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import com.rkylin.risk.core.exception.RiskRestException;
import com.rkylin.risk.core.service.CreditResultService;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import com.rkylin.risk.service.credit.factorybean.BairongProxyServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

/**
 * Created by tomalloc on 16-12-13.
 */
@Slf4j
public abstract class BairongRequester extends AbstractCreditRequester {

  @Resource
  private CreditResultService creditResultService;

  @Resource
  protected BairongProxyServer bairongProxyServer;

  @Resource
  private ObjectMapper jsonMapper;

  private Map<String, Double> modulePriceMap = new HashMap<String, Double>() {
    {
      put("SpecialList_c", 0d);
      put("ApplyLoan", 1d);
      put("EduLevel", 2.5d);
    }
  };

  protected abstract Optional<String> matchModule(String module);

  public Optional<String> validateModule(String[] moduleList) {
    if (moduleList == null || moduleList.length == 0) {
      return Optional.of("请指定查询参数(模块) modules = (SpecialList_c|ApplyLoan)|EduLevel ");
    }
    for (String module : moduleList) {
      if (StringUtils.isBlank(module)) {
        return Optional.of("查询的模块不能有空的存在");
      }
      Optional childModuleMatchResult = matchModule(module);
      if (childModuleMatchResult.isPresent()) {
        return childModuleMatchResult;
      }
    }
    return Optional.absent();
  }

  public long fetchCreditModue(String[] module) {
    CreditModule creditModule = creditResultService.fetchCreditModue(module);
    if (creditModule == null) {
      throw new RiskRestException(500,"加载征信模块异常");
    }
    return creditModule.getId();
  }

  @Override
  public List<CreditResult> queryCreditResult(CreditRequestEntity requestEntity, String[] module) {
    return creditResultService.queryBairongCreditResult(requestEntity.getName(),
        requestEntity.getMobile(),
        requestEntity.getIdNumber(), module);
  }

  protected abstract CreditResult doRequest(CreditResult creditResult, String[] module);

  protected Amount totalPrice(String[] moduleList) {
    //FIXME 价格计算
    Amount amount = new Amount(0);
    for (String module : moduleList) {
      Double value = modulePriceMap.get(module);
      amount=amount.add(value);
    }
    return amount;
  }


  protected Map<String,Object> parseCode(String json) {
    try {
      return jsonMapper.readValue(json, LinkedHashMap.class);
    } catch (IOException e) {
      log.error("解析{}错误",json);
      throw new RiskRestException(500,"json格式错误");
    }
  }

  protected String objectToString(Object object){
    return object==null?null:object.toString();
  }

  @Override public CreditResult request(CreditResult creditResult, String[] module) {
    int tryCount = 1;
    boolean requestSucess = false;
    while (tryCount < 3) {
      try {
        doRequest(creditResult, module);
        requestSucess = true;
        break;
      } catch (RiskCreditTokenExpiredException e) {
        tryCount++;
        bairongProxyServer.refreshToken(true);
      }
    }
    if (!requestSucess) {
      throw new RiskCreditTokenErrorException(500,"连接百融token过期");
    }

    creditResult.setCreditProduct(CreditProductType.BAIRONG.name());

    creditResult.setRequestTime(new DateTime());

    //FIXME 单价处理
    creditResult.setPrice(totalPrice(module));
    return creditResult;
  }
}
