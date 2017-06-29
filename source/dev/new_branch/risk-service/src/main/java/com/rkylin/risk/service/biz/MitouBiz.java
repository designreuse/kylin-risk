package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.MitouGetUserInfoRequestParam;
import com.rkylin.risk.service.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.service.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.service.bean.MitouSigninRequestParam;
import com.rkylin.risk.service.bean.MitouSigninResponseParam;
import com.rkylin.risk.service.bean.MitouSignupRequestParam;
import com.rkylin.risk.service.bean.MitouSignupResponseParam;
import com.rkylin.risk.service.bean.MitouUserInfoRequestParam;
import com.rkylin.risk.service.bean.MitouUserInfoResponseParam;
import com.rkylin.risk.service.api.ApiMitouConstants;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by tomalloc on 16-11-1.
 */
public interface MitouBiz {
  @Path(ApiMitouConstants.REGISTER_API)
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouSignupResponseParam> signup(
      @FormParam("platid") int platid,
      @FormParam("data")
          MitouSignupRequestParam data);

  @Path(ApiMitouConstants.GET_TOKEN_API)
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouSigninResponseParam> signin(
      @FormParam("platid") int platid,
      @FormParam("data")
          MitouSigninRequestParam data);



  @Path(ApiMitouConstants.GET_USER_INFO_API)
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouUserInfoResponseParam> userInfo(@FormParam("platid") int platid,
      @FormParam("data")
          MitouUserInfoRequestParam data);


  @Path("qapi/run.php?act=user&mod=setWindControlData")
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouRiskDataResponseParam> riskrequest(
      @FormParam("platid") int platid,
      @FormParam("data")
      MitouRiskDataRequestParam data);

  @Path("qapi/run.php?act=login&mod=get")
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouGetUserInfoResponseParam> requestUserinfo(
      @FormParam("platid") int platid,
      @FormParam("data")
      MitouGetUserInfoRequestParam data);

}
