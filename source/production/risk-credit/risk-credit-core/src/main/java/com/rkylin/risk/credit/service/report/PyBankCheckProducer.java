package com.rkylin.risk.credit.service.report;

import com.rkylin.gateway.dto.personBankChecks.PersonBankChecksInfo;
import com.rkylin.gateway.dto.personBankChecks.PersonBankChecksInfoCisReport;
import com.rkylin.gateway.dto.personBankChecks.PersonBankChecksInfoItem;
import com.rkylin.gateway.dto.personBankChecks.PersonBankChecksRespDto;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public class PyBankCheckProducer extends ChildReportProducer<PersonBankChecksRespDto> {
  private PersonBankChecksRespDto personBankChecksRespDto;

  public PyBankCheckProducer(
      PersonBankChecksRespDto personBankChecksRespDto) {
    this.personBankChecksRespDto = personBankChecksRespDto;
  }

  @Override public boolean validateData() {
    return false;
  }

  @Override public String code() {
    return null;
  }

  @Override public Map<ReportItem, ?> run() {
    Map<ReportItem, String> map = new LinkedHashMap<>();
    PersonBankChecksInfoCisReport checksInfoCisReport = personBankChecksRespDto.getCisReport();
    if (checksInfoCisReport != null) {
      PersonBankChecksInfo bankChecksInfo = checksInfoCisReport.getPersonBankCheckInfo();
      if (bankChecksInfo != null) {
        PersonBankChecksInfoItem checksInfoItem = bankChecksInfo.getItem();
        if (checksInfoItem != null) {
          ReportItem item = new ReportItem();
          item.setName("个人银行账号状态");
          item.setKey("status");
          item.setCreditProduct(CreditProductType.PY);
          map.put(item, checksInfoItem.getStatus());
          ReportItem itemMessage = new ReportItem();
          itemMessage.setName("个人银行账号描述");
          itemMessage.setKey("message");
          itemMessage.setCreditProduct(CreditProductType.PY);
          map.put(itemMessage, checksInfoItem.getMessage());
        }
      }
    }
    return map;
  }
}
