package com.rkylin.risk.service.api.impl;

import com.google.common.base.Throwables;
import com.rkylin.risk.operation.api.OperateFlowStatusApi;
import com.rkylin.risk.operation.bean.ReturnInfo;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.biz.OperateFlowSynchroBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by ChenFumin on 2016/10/20.
 */
@Component("operateFlowStatusApi")
@Slf4j
public class OperateFlowStatusApiImpl implements OperateFlowStatusApi {

  @Autowired
  private OperateFlowSynchroBiz operateFlowSynchroBiz;

  @Override
  public ReturnInfo checkorderStatus(String checkorderid, String status, String reason,
      String hmac) {

    log.info("【风控系统】接收工作流状态同步传送的数据 checkorderid:{}, status:{}, reason:{}, hmac:{}", checkorderid,
        status, reason, hmac);

    ReturnInfo resultInfo = checkDataValidity(checkorderid, status, reason, hmac);

    if (resultInfo != null) {
      return resultInfo;
    }

    String hmacString = new StringBuilder(checkorderid)
        .append(status)
        .append(ApiServiceConstants.OPER_API_HMAC)
        .toString();

    log.info("【风控系统】生成参数签名hmacString:{}", hmacString);

    if (!DigestUtils.md5Hex(hmacString).equals(hmac)) {
      return syncFailReturnInfo(checkorderid, "【风控系统】加密签名校验不一致!");
    }

    String returnMsg = null;

    try {
      returnMsg = operateFlowSynchroBiz.synchroStatus(checkorderid, status, reason);
    } catch (Exception e) {
      log.info("【风控系统】checkderid:{}, 同步时出现异常！,异常信息:{}", checkorderid,
          Throwables.getStackTraceAsString(e));
      return syncExceptionReturnInfo(checkorderid, "【风控系统】同步异常!");
    }

    return ApiServiceConstants.SUCCESS.equals(returnMsg) ? syncSuccessReturnInfo(checkorderid,
        "【风控系统】同步成功!")
        : syncFailReturnInfo(checkorderid, returnMsg);
  }

  private ReturnInfo checkDataValidity(String checkorderid, String status, String reason,
      String hmac) {
    if (isBlank(checkorderid)) {
      log.info("【风控系统】必填字段 checkorderid 为空");
      return syncFailReturnInfo(checkorderid, "【风控系统】工作流号为空!");
    }

    if (isBlank(status)) {
      log.info("【风控系统】checkorderid 为{}, 必填字段 status 为空", checkorderid);
      return syncFailReturnInfo(checkorderid, "【风控系统】工作流状态为空!");
    }

    if (isBlank(hmac)) {
      log.info("【风控系统】checkorderid 为{}, 必填字段 hmac 为空", checkorderid);
      return syncFailReturnInfo(checkorderid, "【风控系统】加密签名为空!");
    }

    return null;
  }

  /**
   * 信息同步失败
   */
  public ReturnInfo syncFailReturnInfo(String checkorderid, String resultMsg) {
    ReturnInfo returnInfo = new ReturnInfo();
    returnInfo.setCheckorderid(checkorderid);
    returnInfo.setResultCode("1");
    returnInfo.setResultMsg(resultMsg);
    log.info("【风控系统】工作流状态同步结果: {}" + returnInfo);
    return returnInfo;
  }

  /**
   * 信息同步成功
   */
  public ReturnInfo syncSuccessReturnInfo(String checkorderid, String resultMsg) {
    ReturnInfo returnInfo = new ReturnInfo();
    returnInfo.setCheckorderid(checkorderid);
    returnInfo.setResultCode("0");
    returnInfo.setResultMsg(resultMsg);
    log.info("【风控系统】工作流状态同步结果: {}" + returnInfo);
    return returnInfo;
  }

  /**
   * 信息同步异常
   */
  public ReturnInfo syncExceptionReturnInfo(String checkorderid, String resultMsg) {
    ReturnInfo returnInfo = new ReturnInfo();
    returnInfo.setCheckorderid(checkorderid);
    returnInfo.setResultCode("99");
    returnInfo.setResultMsg(resultMsg);
    log.info("【风控系统】工作流状态同步结果: {}" + returnInfo);
    return returnInfo;
  }
}
