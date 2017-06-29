package com.rkylin.risk.boss.biz;

import com.rkylin.risk.boss.resteasy.bean.MitouGetUserInfoRequestParam;
import com.rkylin.risk.boss.resteasy.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.boss.resteasy.bean.MitouResponseParam;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataResponseParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


public interface MitouBiz {

  @Path("qapi/run.php?act=login&mod=get")
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html") MitouResponseParam<MitouGetUserInfoResponseParam> requestUserinfo(
      @FormParam("platid") int platid,
      @FormParam("data")
      MitouGetUserInfoRequestParam data);

  @Path("qapi/run.php?act=user&mod=setWindControlData")
  @POST
  @Produces("application/x-www-form-urlencoded")
  @Consumes("text/html")
  MitouResponseParam<MitouRiskDataResponseParam> riskrequest(
      @FormParam("platid") int platid,
      @FormParam("data")
          MitouRiskDataRequestParam data);

}
