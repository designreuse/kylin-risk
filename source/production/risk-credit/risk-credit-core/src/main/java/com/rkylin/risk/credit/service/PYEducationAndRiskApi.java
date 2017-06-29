package com.rkylin.risk.credit.service;

import com.rkylin.gateway.dto.personEducation.PersonEducationDto;
import com.rkylin.gateway.dto.personEducation.PersonEducationRespDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import com.rkylin.risk.credit.service.report.PyEducationAndRiskProducer;

/**
 * Created by tomalloc on 16-8-1.
 */
public class PYEducationAndRiskApi extends PYApi<PersonEducationRespDto> {

  @Override protected PersonEducationRespDto doRequest(CreditRequestParam requestParam) {
    PersonEducationDto personEducationDto = new PersonEducationDto();
    personEducationDto.setQueryName(requestParam.getName());
    personEducationDto.setIdType(ID_TYPE);
    personEducationDto.setIdCode(requestParam.getIdNumber());
    personEducationDto.setCollegeLevel(3);
    personEducationDto.setCollege("北京大学");
    personEducationDto.setLevelNo("2");
    personEducationDto.setGraduateTime("2008");
    setBasicInfo(personEducationDto);
    PersonEducationRespDto bankChecksRespDto = creditService.educationAndRisk(personEducationDto);
    return bankChecksRespDto;
  }

  @Override protected ChildReportProducer childReportProducer(PersonEducationRespDto data) {
    return new PyEducationAndRiskProducer(data);
  }

  @Override protected TransCode transCode() {
    return TransCode.PERSON_EDUCATION;
  }
}
