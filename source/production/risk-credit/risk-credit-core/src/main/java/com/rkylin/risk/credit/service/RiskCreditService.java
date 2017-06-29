package com.rkylin.risk.credit.service;

import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.rkylin.risk.credit.exception.RiskCreditException;
import com.rkylin.risk.credit.service.cache.LocalTokenCache;
import com.rkylin.risk.credit.service.cache.ProxyTokenCache;
import com.rkylin.risk.credit.service.cache.TokenCache;
import com.rkylin.risk.credit.service.report.ReportItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 征信请求 Created by tomalloc on 16-7-28.
 */
public class RiskCreditService<T> implements InitializingBean {
  @Setter
  private List<CreditProductApi> creditProductApiList;
  @Setter
  private List<TokenCache> tokenCacheList;

  private ProxyTokenCache cache;

  private ExecutorService service;

  public Table<Integer, ReportItem, Object> query(
      final List<CreditRequestParam> requestParamList) throws IOException {
    if (requestParamList == null) {
      return null;
    }
    Table<Integer, ReportItem, Object> table =
        Tables.newCustomTable(new LinkedHashMap<Integer, Map<ReportItem, Object>>(),
            new Supplier<Map<ReportItem, Object>>() {
              @Override public Map<ReportItem, Object> get() {
                return new LinkedHashMap<ReportItem, Object>(10);
              }
            });
    for (CreditRequestParam requestParam : requestParamList) {
      Assert.notNull(requestParam, "参数不能为空");
      Assert.notNull(requestParam.getName(), "用户名不能为空");
      Assert.notNull(requestParam.getIdNumber(), "身份证号不能为空");
      //Assert.notNull(requestParam.getMobile(), "手机号不能为空");
      //Assert.notNull(requestParam.getBankCard(), "银行卡号不能为空");
      //List<Future> futureList=new ArrayList<>(creditProductApiList.size());

      for (final CreditProductApi productApi : creditProductApiList) {

        ReportItem nameItem = new ReportItem();
        nameItem.setName("姓名");
        table.put(requestParam.hashCode(), nameItem, requestParam.getName());

        ReportItem idNumberItem = new ReportItem();
        idNumberItem.setName("身份证号");
        table.put(requestParam.hashCode(), idNumberItem, requestParam.getIdNumber());

        ReportItem mobileItem = new ReportItem();
        mobileItem.setName("手机");
        table.put(requestParam.hashCode(), mobileItem, requestParam.getMobile());

        //ReportItem bankCardItem = new ReportItem();
        //bankCardItem.setName("银行卡号");
        //table.put(requestParam.hashCode(), bankCardItem, requestParam.getBankCard());

        Map<ReportItem, ?> map = productApi.request(requestParam);
        for (Map.Entry<ReportItem, ?> entry : map.entrySet()) {
          table.put(requestParam.hashCode(), entry.getKey(), entry.getValue());
        }
      }
    }
    return table;
  }

  @Override public void afterPropertiesSet() throws Exception {
    if (tokenCacheList == null) {
      tokenCacheList = new ArrayList<>();
      tokenCacheList.add(new LocalTokenCache());
    }
    cache = ProxyTokenCache.cache();
    for (TokenCache tokenCache : tokenCacheList) {
      cache.addCache(tokenCache);
    }

    //初始化默认api
    if (creditProductApiList == null) {
      throw new RiskCreditException("风控产品不能为空");
    }
    if (service == null) {
      service = Executors.newFixedThreadPool(8);
    }
    //设置缓存
    for (CreditProductApi productApi : creditProductApiList) {
      if (productApi instanceof TokenCacheService) {
        TokenCacheService cacheService = (TokenCacheService) productApi;
        cacheService.setCache(cache);
      }
    }
  }
}
