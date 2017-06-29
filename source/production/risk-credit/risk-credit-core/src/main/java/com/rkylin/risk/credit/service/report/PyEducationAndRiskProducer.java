package com.rkylin.risk.credit.service.report;

import com.rkylin.gateway.dto.personEducation.PersonEducationCisReport;
import com.rkylin.gateway.dto.personEducation.PersonEducationRespDto;
import com.rkylin.gateway.dto.personEducation.PersonRiskStatInfo;
import com.rkylin.gateway.dto.personEducation.PersonRiskStatInfoStat;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public class PyEducationAndRiskProducer extends ChildReportProducer<PersonEducationRespDto> {

  private PersonEducationRespDto personEducationRespDto;

  public PyEducationAndRiskProducer(
      PersonEducationRespDto personEducationRespDto) {
    this.personEducationRespDto = personEducationRespDto;
  }

  @Override public boolean validateData() {
    return false;
  }

  @Override public String code() {
    return null;
  }

  @Override public Map<ReportItem, ?> run() {
    Map<ReportItem, String> map = new LinkedHashMap<>();
    PersonEducationCisReport cisReport = personEducationRespDto.getCisReport();
    if (cisReport != null) {
      PersonRiskStatInfo personRiskStatInfo = cisReport.getPersonRiskStatInfo();
      if (personRiskStatInfo != null) {
        PersonRiskStatInfoStat personRiskStatInfoStat = personRiskStatInfo.getStat();
        if (personRiskStatInfoStat != null) {
          ReportItem alCountItem = new ReportItem();
          alCountItem.setName("司法案例信息条数");
          alCountItem.setKey("alCount");
          alCountItem.setCreditProduct(CreditProductType.PY);
          map.put(alCountItem, personRiskStatInfoStat.getAlCount());

          ReportItem cqggCountItem = new ReportItem();
          cqggCountItem.setName("催欠公告信息条数");
          cqggCountItem.setKey("cqggCount");
          cqggCountItem.setCreditProduct(CreditProductType.PY);
          map.put(cqggCountItem, personRiskStatInfoStat.getCqggCount());

          ReportItem swCountItem = new ReportItem();
          swCountItem.setName("税务行政执法信息条数");
          swCountItem.setKey("swCount");
          swCountItem.setCreditProduct(CreditProductType.PY);
          map.put(swCountItem, personRiskStatInfoStat.getSwCount());

          ReportItem sxCountItem = new ReportItem();
          sxCountItem.setName("司法失信信息条数");
          sxCountItem.setKey("sxCount");
          sxCountItem.setCreditProduct(CreditProductType.PY);
          map.put(sxCountItem, personRiskStatInfoStat.getSxCount());

          ReportItem wdyqCountItem = new ReportItem();
          wdyqCountItem.setName("网贷逾期信息条数");
          wdyqCountItem.setKey("wdyqCount");
          wdyqCountItem.setCreditProduct(CreditProductType.PY);
          map.put(wdyqCountItem, personRiskStatInfoStat.getWdyqCount());

          ReportItem zxCountItem = new ReportItem();
          zxCountItem.setName("司法执行信息条数");
          zxCountItem.setKey("zxCount");
          zxCountItem.setCreditProduct(CreditProductType.PY);
          map.put(zxCountItem, personRiskStatInfoStat.getZxCount());
        }
      }
    }
    return map;
  }
}
