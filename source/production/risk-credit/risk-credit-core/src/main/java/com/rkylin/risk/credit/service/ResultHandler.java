package com.rkylin.risk.credit.service;

import com.rkylin.risk.credit.biz.CreditResultPersistent;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomalloc on 16-8-2.
 */
@Slf4j
public class ResultHandler implements InvocationHandler {
  private final CreditResultPersistent creditResultDao;
  private final ChildReportProducer producer;

  public ResultHandler(ChildReportProducer producer, CreditResultPersistent creditResultDao) {
    this.producer = producer;
    this.creditResultDao = creditResultDao;
  }

  @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (method.getDeclaringClass() == Object.class) {
      return method.invoke(this, args);
    }
    if (method.getName().equals("run") && ChildReportProducer.class.isAssignableFrom(
        proxy.getClass())) {
      //CreditResult creditResult=new CreditResult();
      //creditResultDao.save(creditResult);
      log.info("转换结果生成子报告");
    }
    return method.invoke(producer, args);
  }
}
