package com.rkylin.risk.credit.service;

import com.rkylin.gateway.dto.personBankChecks.PersonBankChecksDto;
import com.rkylin.gateway.dto.personBankChecks.PersonBankChecksRespDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import com.rkylin.risk.credit.service.report.PyBankCheckProducer;

/**
 * Created by tomalloc on 16-8-1.
 */
public class PYBankCheckApi extends PYApi<PersonBankChecksRespDto> {

  @Override protected PersonBankChecksRespDto doRequest(CreditRequestParam requestParam) {
    PersonBankChecksDto bankChecksDto = new PersonBankChecksDto();
    bankChecksDto.setQueryName(requestParam.getName());
    bankChecksDto.setIdType(ID_TYPE);
    bankChecksDto.setIdCode(requestParam.getIdNumber());
    bankChecksDto.setAccountNo(requestParam.getBankCard());
    bankChecksDto.setMobile(requestParam.getMobile());
    setBasicInfo(bankChecksDto);
    PersonBankChecksRespDto bankChecksRespDto = creditService.personBankChecks(bankChecksDto);
    return bankChecksRespDto;
  }

  @Override protected ChildReportProducer childReportProducer(PersonBankChecksRespDto data) {
    return new PyBankCheckProducer(data);
  }

  @Override protected TransCode transCode() {
    return TransCode.PERSON_BANK_CHECK_FOUR;
  }
}
