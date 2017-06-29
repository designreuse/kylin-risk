package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouCloseOrderRequestParam;
import com.rkylin.risk.service.bean.MitouGetFirstAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetFirstAreaResponseParam;
import com.rkylin.risk.service.bean.MitouGetSchoolAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetSchoolAreaResponseParam;
import com.rkylin.risk.service.bean.MitouGetSchoolRequestParam;
import com.rkylin.risk.service.bean.MitouGetSchoolResponseParam;
import com.rkylin.risk.service.bean.MitouGetSonAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetSonAreaResponseParam;
import com.rkylin.risk.service.bean.MitouGetUserInfoRequestParam;
import com.rkylin.risk.service.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.service.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.service.bean.MitouSigninRequestParam;
import com.rkylin.risk.service.bean.MitouSigninResponseParam;
import com.rkylin.risk.service.bean.MitouSignupRequestParam;
import com.rkylin.risk.service.bean.MitouSignupResponseParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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

  @Path("qapi/run.php?act=user&mod=setWindControlData")
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouRiskDataResponseParam> riskrequest(
      @FormParam("platid") int platid,
      @FormParam("data")
      MitouRiskDataRequestParam data);

  @Path(ApiMitouConstants.GET_USER_INFO_API)
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouGetUserInfoResponseParam> requestUserinfo(
      @FormParam("platid") int platid,
      @FormParam("data")
      MitouGetUserInfoRequestParam data);


  @Path("qapi/run.php?act=sys&mod=getFirstArea")
  @GET
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouGetFirstAreaResponseParam[]> getFirstArea(
      @QueryParam("platid") int platid,@QueryParam("data")
  MitouGetFirstAreaRequestParam data);


  @Path("qapi/run.php?act=sys&mod=getSonArea")
  @GET
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouGetSonAreaResponseParam[]> getSonArea(
      @QueryParam("platid") int platid, @QueryParam("data")
  MitouGetSonAreaRequestParam data);


  @Path("qapi/run.php?act=sys&mod=getSchool")
  @GET
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouGetSchoolResponseParam[]> getSchool(
      @QueryParam("platid") int platid,@QueryParam("data")
  MitouGetSchoolRequestParam data);


  @Path("qapi/run.php?act=sys&mod=getSchoolArea")
  @GET
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouGetSchoolAreaResponseParam[]> getSchoolArea(
      @QueryParam("platid") int platid,@QueryParam("data")
  MitouGetSchoolAreaRequestParam data);

  @Path("qapi/run.php?act=order&mod=doClose")
  @GET
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam closeOrder(@QueryParam("platid") int platid,@QueryParam("data")
      MitouCloseOrderRequestParam data);
}
