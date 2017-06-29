package com.rkylin.risk.credit.service;

import com.rkylin.gateway.dto.CommonDto;
import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryRespDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import com.rkylin.risk.credit.service.report.PYQueryHistoryProducer;

/**
 * Created by tomalloc on 16-8-1.
 */
public class PYQueryHistoryApi extends PYApi<PersonHistoryQueryRespDto> {
  @Override public PersonHistoryQueryRespDto doRequest(CreditRequestParam requestParam) {
    CommonDto baseDto = new CommonDto();
    baseDto.setIdCode(requestParam.getIdNumber());
    baseDto.setIdType(ID_TYPE);
    baseDto.setQueryName(requestParam.getName());
    setBasicInfo(baseDto);
    return creditService.personHistoryQuery(baseDto);
  }

  @Override protected ChildReportProducer childReportProducer(PersonHistoryQueryRespDto data) {
    return new PYQueryHistoryProducer(data);
  }

  @Override protected TransCode transCode() {
    return TransCode.PERSON_HISTORY_QUERY;
  }
}
