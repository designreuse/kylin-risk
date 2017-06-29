package com.rkylin.risk.service.api.rest;

import com.google.common.base.Optional;
import com.rkylin.risk.core.dto.RestResultDTO;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.service.bean.CreditParam;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import com.rkylin.risk.service.biz.CreditBiz;
import com.rkylin.risk.service.credit.BaiRongDasRequester;
import com.rkylin.risk.service.credit.BaiRongTerRequester;
import com.rkylin.risk.service.credit.BairongRequester;
import com.rkylin.risk.service.credit.UnionPayAdvisorsRequester;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by tomalloc on 16-12-12.
 */
@Path("credit")
@Slf4j
@Named
public class CreditApi {

  @Resource
  private CreditBiz creditBiz;

  @Resource
  private UnionPayAdvisorsRequester unionPayAdvisorsRequester;

  @Resource
  private BaiRongDasRequester baiRongDasRequester;

  @Resource
  private BaiRongTerRequester baiRongTerRequester;

  @POST
  @Path("unionpay")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResultDTO unionpayAdvisor(CreditParam param) {
    RestResultDTO resultDTO = new RestResultDTO();
    Optional<String> optional = validate(param, new Validator() {
      @Override public Optional<String> call(CreditRequestEntity creditParam) {
        if (StringUtils.isBlank(creditParam.getBankCard())) {
          return Optional.of("银行卡号不能有空的存在");
        }
        creditParam.setBankCard(creditParam.getBankCard().trim());
        return Optional.absent();
      }
    });
    if (optional.isPresent()) {
      resultDTO.setCode(400);
      resultDTO.setMessage(optional.get());
    } else {
      resultDTO.setCode(200);
      List<CreditResult> creditResults =
          creditBiz.queryCreditResult(param, unionPayAdvisorsRequester, null);
      resultDTO.setData(creditResults);
    }
    return resultDTO;
  }


  /**
   * 百融查询
   *
   * @param param 查询参数
   */
  @POST
  @Path("bairong/{apiType}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResultDTO bairong(CreditParam param,
      @PathParam("apiType") @DefaultValue("TER") String apiType,
      @QueryParam("modules") String module, @Context UriInfo uriInfo) {
    RestResultDTO resultDTO = new RestResultDTO();
    //TODO 验证数据正确性
    BairongRequester creditRequester = null;
    if ("ter".equals(apiType)) {
      creditRequester = baiRongTerRequester;
    } else if ("das".equals(apiType)) {
      creditRequester = baiRongDasRequester;
    } else {
      String requestUri = uriInfo.getBaseUriBuilder().toTemplate();
      resultDTO.setCode(400);
      String message=String.format("请求的url只支持 %scredit/bairong/ter 和 %scredit/bairong/das。",requestUri,requestUri);
      resultDTO.setMessage(message);
    }
    if (creditRequester == null) {
      return resultDTO;
    }
    Optional<String> moduleOptional=creditRequester.validateModule(new String[]{module});
    if(moduleOptional.isPresent()){
      resultDTO.setCode(400);
      resultDTO.setMessage(moduleOptional.get());
      return resultDTO;
    }

    Optional<String> optional = validate(param, new Validator() {
      @Override public Optional<String> call(CreditRequestEntity creditParam) {
        if (StringUtils.isBlank(creditParam.getName())) {
          return Optional.of("查询参数中的姓名不能有空的存在");
        }
        if (StringUtils.isBlank(creditParam.getIdNumber())) {
          return Optional.of("查询参数中的身份证号不能有空的存在");
        }
        if (StringUtils.isBlank(creditParam.getMobile())) {
          return Optional.of("查询参数中的手机号不能有空的存在");
        }
        creditParam.setName(creditParam.getName().trim());
        creditParam.setIdNumber(creditParam.getIdNumber().trim());
        creditParam.setMobile(creditParam.getMobile().trim());
        return Optional.absent();
      }
    });
    if (optional.isPresent()) {
      resultDTO.setCode(400);
      resultDTO.setMessage(optional.get());
    } else {
      resultDTO.setCode(200);
      List<CreditResult> creditResults =
          creditBiz.queryCreditResult(param, creditRequester, new String[] {module});
      resultDTO.setData(creditResults);
    }
    return resultDTO;
  }

  private interface Validator {
    Optional<String> call(CreditRequestEntity creditParam);
  }

  private Optional<String> validate(CreditParam param, Validator validator) {
    List<CreditRequestEntity> list = param.getData();
    if (list == null || list.isEmpty()) {
      return Optional.of("查询的数据不能为空");
    }
    for (CreditRequestEntity creditParam : list) {
      Optional<String> optional = validator.call(creditParam);
      if (optional.isPresent()) {
        return optional;
      }
    }
    return Optional.absent();
  }
}
