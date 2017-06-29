package com.rkylin.risk.test.restController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.operation.api.PersonApi;
import com.rkylin.risk.operation.bean.PersonMsg;
import com.rkylin.risk.test.dto.PersonFactor;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomalloc on 16-9-19.
 */
@RestController
@RequestMapping("/persionApi")
public class PersonApiRestAction {

  @Resource
  private PersonApi personApi;

  private ObjectMapper objectMapper=new ObjectMapper();

  @RequestMapping(value = "personmsg", method = RequestMethod.POST)
  public PersonMsg personmsgTest(@RequestBody PersonFactor personFactor) throws JsonProcessingException {
    String hmacString = new StringBuilder(personFactor.getUserid())
        .append(personFactor.getConstid())
        .append(personFactor.getUsername())
        .append(personFactor.getClassname())
        .append("77091510953887013453090194281439")
        .toString();
    personFactor.setHmac(DigestUtils.md5Hex(hmacString));
    return personApi.personmsg(objectMapper.writeValueAsString(personFactor));
  }
}
