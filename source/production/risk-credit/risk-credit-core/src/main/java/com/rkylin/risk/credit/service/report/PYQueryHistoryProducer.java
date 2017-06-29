package com.rkylin.risk.credit.service.report;

import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryCisReport;
import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryInfo;
import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryInfoItem;
import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryRespDto;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public class PYQueryHistoryProducer extends ChildReportProducer<PersonHistoryQueryRespDto> {

  private PersonHistoryQueryRespDto personHistoryQueryRespDto;

  public PYQueryHistoryProducer(
      PersonHistoryQueryRespDto personHistoryQueryRespDto) {
    this.personHistoryQueryRespDto = personHistoryQueryRespDto;
  }

  @Override public boolean validateData() {
    return false;
  }

  @Override public String code() {
    return personHistoryQueryRespDto.getReturnCode();
  }

  @Override public Map<ReportItem, ?> run() {
    String code = personHistoryQueryRespDto.getReturnCode();
    Map<ReportItem, String> map = new LinkedHashMap<>();
    if ("100000".equals(code)) {
      PersonHistoryQueryCisReport cisReport = personHistoryQueryRespDto.getCisReport();
      if (cisReport != null) {
        PersonHistoryQueryInfo queryInfo = cisReport.getHistoryQueryInfo();
        if (queryInfo != null) {
          List<PersonHistoryQueryInfoItem> listItem = queryInfo.getItem();
          if (listItem != null) {
            int i = 1;
            for (PersonHistoryQueryInfoItem item : listItem) {
              ReportItem unitMemberItem = new ReportItem();
              unitMemberItem.setName("查询机构类型名称" + i);
              unitMemberItem.setKey("unitMember");
              unitMemberItem.setCreditProduct(CreditProductType.PY);
              map.put(unitMemberItem, item.getUnitMember());

              ReportItem queryDateItem = new ReportItem();
              queryDateItem.setName("查询日期" + i);
              queryDateItem.setKey("queryDate");
              queryDateItem.setCreditProduct(CreditProductType.PY);
              map.put(queryDateItem, item.getQueryDate());

              ReportItem unitMemberIDItem = new ReportItem();
              unitMemberIDItem.setName("查询机构类型ID" + i);
              unitMemberIDItem.setKey("unitMemberID");
              unitMemberIDItem.setCreditProduct(CreditProductType.PY);
              map.put(unitMemberIDItem, item.getUnitMemberID());

              ReportItem unitItem = new ReportItem();
              unitItem.setName("查询单位名称" + i);
              unitItem.setKey("unit");
              unitItem.setCreditProduct(CreditProductType.PY);
              map.put(unitItem, item.getUnit());

              ReportItem unitIDItem = new ReportItem();
              unitIDItem.setName("查询单位ID" + i);
              unitIDItem.setKey("unitID");
              unitIDItem.setCreditProduct(CreditProductType.PY);
              map.put(unitIDItem, item.getUnitID());

              ReportItem queryReasonItem = new ReportItem();
              queryReasonItem.setName("查询原因" + i);
              queryReasonItem.setKey("queryReason");
              queryReasonItem.setCreditProduct(CreditProductType.PY);
              map.put(queryReasonItem, item.getQueryReason());
              i++;
            }
          }
        }
      }
    }
    return map;
  }
}
