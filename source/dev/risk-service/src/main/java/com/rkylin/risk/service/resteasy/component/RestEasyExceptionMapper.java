package com.rkylin.risk.service.resteasy.component;

import com.rkylin.risk.core.dto.RestResultDTO;
import com.rkylin.risk.core.exception.RiskRestException;
import com.rkylin.risk.core.exception.model.ErrorDesc;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.spi.ApplicationException;

/**
 * Created by tomalloc on 16-12-15.
 */
@Provider
@Slf4j
public class RestEasyExceptionMapper implements ExceptionMapper<ApplicationException> {
  //@Context
  //private HttpHeaders headers;
  @Override public Response toResponse(ApplicationException exception) {
    RestResultDTO resultDTO=new RestResultDTO();
    Throwable throwable=exception.getCause();
    log.error("resteasy异常",throwable);
    if(throwable instanceof RiskRestException){
      RiskRestException riskRestException= (RiskRestException) throwable;
      ErrorDesc errorDesc=riskRestException.getErrorDesc();
      resultDTO.setCode(errorDesc.getCode());
      resultDTO.setMessage(errorDesc.getMessage());
    }else{
      resultDTO.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
      resultDTO.setMessage("系统错误");
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(resultDTO).build();
  }
}
