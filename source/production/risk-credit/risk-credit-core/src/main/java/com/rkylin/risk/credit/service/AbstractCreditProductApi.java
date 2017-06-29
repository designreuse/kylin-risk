package com.rkylin.risk.credit.service;

import com.google.gson.Gson;
import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryRespDto;
import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.credit.biz.CreditResultPersistent;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import com.rkylin.risk.credit.service.report.ReportItem;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import java.util.Date;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * Created by tomalloc on 16-8-2.
 */
@Slf4j
public abstract class AbstractCreditProductApi<T> implements CreditProductApi {

  @Setter
  private CreditResultPersistent creditResultPersistent;
  private XStream xStream = new XStream(new JsonHierarchicalStreamDriver());

  Gson gson = new Gson();

  protected abstract ExternalCreditResult queryFromDataBase(CreditRequestParam requestParam);

  @Override public Map<ReportItem, ?> request(CreditRequestParam requestParam) {
    ExternalCreditResult dbCeditResult = queryFromDataBase(requestParam);
    if (dbCeditResult == null) {

      T data = doRequest(requestParam);
      ChildReportProducer reportProducer = childReportProducer(data);

      ExternalCreditResult creditResult = new ExternalCreditResult();
      creditResult.setUserName(requestParam.getName());
      creditResult.setIdNumber(requestParam.getIdNumber());
      creditResult.setBankCard(requestParam.getBankCard());
      creditResult.setMobile(requestParam.getMobile());

      creditResult.setCreditProduct(productType());
      creditResult.setCreditCode(reportProducer.code());
      creditResult.setCreditModule(module());
      creditResult.setRequestTime(new Date());

      if (reportProducer.validateData()) {
        creditResult.setRiskCode("00");
      }
      String result = null;
      if (data instanceof String) {
        result = (String) data;
      } else if (data instanceof JSONObject) {
        result = data.toString();
      } else {
        //如果是鹏元的api
        if (PYApi.class.isAssignableFrom(this.getClass())) {
          result = xStream.toXML(data);
        } else {
          //不支持的操作
          throw new UnsupportedOperationException("不支持的数据");
        }
      }
      log.info(result);
      creditResult.setResult(result);
      creditResultPersistent.persistent(creditResult);
      return reportProducer.run();
    }
    if (productType() == CreditProductType.BAIRONG) {
      ChildReportProducer reportProducer = childReportProducer((T) dbCeditResult.getResult());
      return reportProducer.run();
    } else if (productType() == CreditProductType.UNIONPAY) {
      ChildReportProducer reportProducer =
          childReportProducer((T) JSONObject.fromObject(dbCeditResult.getResult()));
      return reportProducer.run();
    } else if (productType() == CreditProductType.PY) {
      String jsonData = dbCeditResult.getResult();
      ChildReportProducer reportProducer = null;
      jsonData = JSONObject.fromObject(jsonData)
          .getString("com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryRespDto");
      reportProducer = childReportProducer(
          (T) gson.fromJson(jsonData, PersonHistoryQueryRespDto.class));
      return reportProducer.run();
    }
    throw new UnsupportedOperationException("不支持类型");
  }

  protected abstract T doRequest(CreditRequestParam requestParam);

  protected abstract ChildReportProducer childReportProducer(T data);

  /**
   * 产品类型
   */
  protected abstract CreditProductType productType();

  /**
   * 模块
   */
  protected abstract String module();

  protected CreditResultPersistent getCreditResultPersistent() {
    return creditResultPersistent;
  }
}
