package com.rkylin.risk.credit.service.report;

import com.rkylin.gateway.dto.personCredit.PersonCreditCisReport;
import com.rkylin.gateway.dto.personCredit.PersonCreditRespDto;
import com.rkylin.gateway.dto.personCredit.PersonRiskStatInfo;
import com.rkylin.gateway.dto.personCredit.PersonRiskStatInfoItem;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public class PyCreditReportProducer extends ChildReportProducer<PersonCreditRespDto> {

  private PersonCreditRespDto personCreditRespDto;

  public PyCreditReportProducer(
      PersonCreditRespDto personCreditRespDto) {
    this.personCreditRespDto = personCreditRespDto;
  }

  @Override public boolean validateData() {
    return false;
  }

  @Override public String code() {
    return null;
  }

  @Override public Map<ReportItem, ?> run() {
    Map<ReportItem, String> map = new LinkedHashMap<>();
    PersonCreditCisReport creditCisReport = personCreditRespDto.getCisReport();
    if (creditCisReport != null) {
      PersonRiskStatInfo riskStatInfo = creditCisReport.getPersonRiskStatInfo();
      if (riskStatInfo != null) {
        PersonRiskStatInfoItem infoItem = riskStatInfo.getStat();
        if (infoItem != null) {
          ReportItem alCountItem = new ReportItem();
          alCountItem.setName("司法案例信息条数");
          alCountItem.setKey("alCount");
          alCountItem.setCreditProduct(CreditProductType.PY);
          map.put(alCountItem, infoItem.getAlCount());

          ReportItem cqggCountItem = new ReportItem();
          cqggCountItem.setName("催欠公告信息条数");
          cqggCountItem.setKey("cqggCount");
          cqggCountItem.setCreditProduct(CreditProductType.PY);
          map.put(cqggCountItem, infoItem.getCqggCount());

          ReportItem swCountItem = new ReportItem();
          swCountItem.setName("税务行政执法信息条数");
          swCountItem.setKey("swCount");
          swCountItem.setCreditProduct(CreditProductType.PY);
          map.put(swCountItem, infoItem.getSwCount());

          ReportItem sxCountItem = new ReportItem();
          sxCountItem.setName("司法失信信息条数");
          sxCountItem.setKey("sxCount");
          sxCountItem.setCreditProduct(CreditProductType.PY);
          map.put(sxCountItem, infoItem.getSxCount());

          ReportItem wdyqCountItem = new ReportItem();
          wdyqCountItem.setName("网贷逾期信息条数");
          wdyqCountItem.setKey("wdyqCount");
          wdyqCountItem.setCreditProduct(CreditProductType.PY);
          map.put(wdyqCountItem, infoItem.getWdyqCount());

          ReportItem zxCountItem = new ReportItem();
          zxCountItem.setName("司法执行信息条数");
          zxCountItem.setKey("zxCount");
          zxCountItem.setCreditProduct(CreditProductType.PY);
          map.put(zxCountItem, infoItem.getZxCount());
        }
      }
    }
    return map;
  }
}
