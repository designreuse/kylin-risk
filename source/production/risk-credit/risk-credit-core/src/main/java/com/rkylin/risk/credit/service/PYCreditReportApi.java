package com.rkylin.risk.credit.service;

import com.rkylin.gateway.dto.CommonDto;
import com.rkylin.gateway.dto.personCredit.PersonCreditRespDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import com.rkylin.risk.credit.service.report.PyCreditReportProducer;

/**
 * Created by tomalloc on 16-8-1.
 */
public class PYCreditReportApi extends PYApi<PersonCreditRespDto> {

  @Override protected PersonCreditRespDto doRequest(CreditRequestParam requestParam) {
    CommonDto baseDto = new CommonDto();
    baseDto.setIdCode(requestParam.getIdNumber());
    baseDto.setIdType(ID_TYPE);
    baseDto.setQueryName(requestParam.getName());
    setBasicInfo(baseDto);
    PersonCreditRespDto respDto = creditService.personCreditReport(baseDto);
    return respDto;
  }

  @Override protected ChildReportProducer childReportProducer(PersonCreditRespDto data) {
    return new PyCreditReportProducer(data);
  }

  @Override protected TransCode transCode() {
    return TransCode.PERSON_CREDIT;
  }
}
