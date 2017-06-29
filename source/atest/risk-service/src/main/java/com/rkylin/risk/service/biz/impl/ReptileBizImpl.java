package com.rkylin.risk.service.biz.impl;

import com.Rop.api.domain.Detail;
import com.Rop.api.domain.Result;
import com.Rop.api.request.FengchaoCrawlerCreditBasicCaptureRequest;
import com.Rop.api.request.FengchaoCrawlerCreditBasicDetailRequest;
import com.Rop.api.request.FengchaoCrawlerCreditBasicResultRequest;
import com.Rop.api.response.FengchaoCrawlerCreditBasicCaptureResponse;
import com.Rop.api.response.FengchaoCrawlerCreditBasicDetailResponse;
import com.Rop.api.response.FengchaoCrawlerCreditBasicResultResponse;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Reptile;
import com.rkylin.risk.core.service.ReptileService;
import com.rkylin.risk.service.biz.ReptileBiz;
import com.rkylin.risk.service.utils.ROPUtil;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-8-5.
 */
@Slf4j
@Component("reptileBiz")
public class ReptileBizImpl implements ReptileBiz {

  @Resource
  private ReptileService reptileService;
  @Resource
  private ROPUtil commonROP;

  @Override
  public void requestVerifyReptile(int type, String name, String identity, String cellphone,
      String orgname, String orgidentity, String checkorderid) {

    Reptile reptile = new Reptile();
    reptile.setName(name);
    reptile.setIdentity(identity);
    reptile.setCheckorderid(checkorderid);
    reptile.setType("1");
    reptile.setTimeout(Constants.REPTILEPERTIMEOUT);
    log.info("个人爬虫发起：checkorderid={},name={},identity={}", checkorderid, name,
        identity);
    reptile.setVerifyId(verifyReptile(1, name, identity, cellphone, Constants.REPTILEPERTIMEOUT));
    reptileService.insert(reptile);
    if (type == 2) {
      Reptile orgReptile = new Reptile();
      orgReptile.setName(orgname);
      orgReptile.setIdentity(orgidentity);
      orgReptile.setCheckorderid(checkorderid);
      orgReptile.setTimeout(Constants.REPTILEORGTIMEOUT);
      orgReptile.setType("2");
      log.info("企业爬虫发起：checkorderid={},orgname={},orgidentity={}", checkorderid,
          orgname, orgidentity);
      orgReptile.setVerifyId(
          verifyReptile(2, orgname, orgidentity, "", Constants.REPTILEORGTIMEOUT));
      reptileService.insert(orgReptile);
    }
  }

  @Override public Map pullVerifyResult(String checkorderid, String querytype) {
    Reptile reptile = reptileService.queryByCheckorderid(checkorderid, querytype);
    Map map = new HashMap();
    if (reptile == null || reptile.getIdentity() == null) {
      log.info("无此爬虫任务 checkorderid：{}", checkorderid);
    } else {
      List<Result> resultList = getReptileList(reptile.getVerifyId());
      //查询个人爬虫结果
      if ("1".equals(querytype) && CollectionUtils.isNotEmpty(resultList)) {
        List<Result> tempList = resultList.subList(0, 8);
        tempList.add(resultList.get(23));
        map = setReptileList(reptile, tempList);
      }
      //查询机构爬虫结果
      if ("2".equals(querytype) && CollectionUtils.isNotEmpty(resultList)) {
        map = setReptileList(reptile, resultList.subList(2, 23));
      }
      reptileService.update(reptile);
    }
    return map;
  }

  @Override public List<String> pullVerifyDetail(String checkorderid, String querytype, int type) {
    Reptile reptile = reptileService.queryByCheckorderid(checkorderid, querytype);
    if (reptile == null || reptile.getIdentity() == null) {
      log.info("无此爬虫任务 checkorderid：{}", checkorderid);
      return Lists.newArrayList();
    } else {
      List<Detail> details = getReptileDetail(reptile.getVerifyId(), type);
      List<String> list = Lists.newArrayList();
      if (details != null) {
        for (Detail detail : details) {
          list.add(detail.getDetail());
        }
      }
      return list;
    }
  }

  /**
   * 发起爬虫
   */
  @Override
  public Long verifyReptile(int type, String name, String identity, String cellphone, int timeout) {
    FengchaoCrawlerCreditBasicCaptureRequest captureRequest =
        new FengchaoCrawlerCreditBasicCaptureRequest();
    captureRequest.setName(name);
    captureRequest.setIdentity(identity);
    captureRequest.setTimeout(timeout);
    captureRequest.setType(type);
    captureRequest.setCellphone(cellphone);

    FengchaoCrawlerCreditBasicCaptureResponse captureResponse =
        commonROP.getResponse(captureRequest, "json");
    if (captureResponse != null && captureResponse.isSuccess()) {
      log.info("爬虫发起成功：name={},identity={}", name, identity);
      String verifyid = captureResponse.getId();
      return verifyid == null ? null : Long.parseLong(verifyid);
    } else {
      log.info("爬虫发起失败：name={},identity={}", name, identity);
      return null;
    }
  }

  /*
  查询爬虫结果
   */
  @Override
  public List<Result> getReptileList(Long verifyId) {
    FengchaoCrawlerCreditBasicResultRequest resultRequest =
        new FengchaoCrawlerCreditBasicResultRequest();
    resultRequest.setId(verifyId);

    FengchaoCrawlerCreditBasicResultResponse resultResponse =
        commonROP.getResponse(resultRequest, "json");
    return resultResponse.getResults();
  }

  /*
  查询结果详情
   */
  @Override
  public List<Detail> getReptileDetail(Long verifyId, int type) {
    FengchaoCrawlerCreditBasicDetailRequest detailRequest =
        new FengchaoCrawlerCreditBasicDetailRequest();
    detailRequest.setId(verifyId);
    detailRequest.setType(type);

    FengchaoCrawlerCreditBasicDetailResponse detailResponse =
        commonROP.getResponse(detailRequest, "json");
    return detailResponse.getDetails();
  }

  public Map setReptileList(Reptile reptile, List<Result> resultList) {
    Map<String, String> map = new LinkedHashMap<>();
    for (Result result : resultList) {

      switch (result.getType()) {
        case "1":
          reptile.setLainum(Integer.parseInt(result.getCount()));
          map.put("老赖网", result.getCount() + "|" + result.getType());
          break;
        case "2":
          reptile.setLoanblacknum(Integer.parseInt(result.getCount()));
          map.put("网贷黑名单", result.getCount() + "|" + result.getType());
          break;
        case "3":
          reptile.setCreditnetnum(Integer.parseInt(result.getCount()));
          map.put("失信网", result.getCount() + "|" + result.getType());
          break;
        case "4":
          reptile.setExecutionnetnum(Integer.parseInt(result.getCount()));
          map.put("执行网", result.getCount() + "|" + result.getType());
          break;
        case "5":
          reptile.setRefereenetnum(Integer.parseInt(result.getCount()));
          map.put("裁判文书网", result.getCount() + "|" + result.getType());
          break;
        case "6":
          reptile.setBaidunum(Integer.parseInt(result.getCount()));
          map.put("百度搜索", result.getCount() + "|" + result.getType());
          break;
        case "7":
          reptile.setSogounum(Integer.parseInt(result.getCount()));
          map.put("搜狗搜索", result.getCount() + "|" + result.getType());
          break;
        case "8":
          reptile.setThreesearchnum(Integer.parseInt(result.getCount()));
          map.put("360搜索", result.getCount() + "|" + result.getType());
          break;
        case "9":
          reptile.setBaseinfonum(Integer.parseInt(result.getCount()));
          map.put("启信宝-基本信息", result.getCount() + "|" + result.getType());
          break;
        case "10":
          reptile.setShareholdernum(Integer.parseInt(result.getCount()));
          map.put("启信宝-股东", result.getCount() + "|" + result.getType());
          break;
        case "11":
          reptile.setKeypersionnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-主要人员", result.getCount() + "|" + result.getType());
          break;
        case "12":
          reptile.setBuschangenum(Integer.parseInt(result.getCount()));
          map.put("启信宝-工商变更", result.getCount() + "|" + result.getType());
          break;
        case "13":
          reptile.setJudicialnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-法院判决", result.getCount() + "|" + result.getType());
          break;
        case "14":
          reptile.setCourtnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-法院公告", result.getCount() + "|" + result.getType());
          break;
        case "15":
          reptile.setExecupersonnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-被执行人员信息", result.getCount() + "|" + result.getType());
          break;
        case "16":
          reptile.setDishonestnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-失信人信息", result.getCount() + "|" + result.getType());
          break;
        case "17":
          reptile.setJudicialsalenum(Integer.parseInt(result.getCount()));
          map.put("启信宝-司法拍卖", result.getCount() + "|" + result.getType());
          break;
        case "18":
          reptile.setOperabnormalnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-经营异常", result.getCount() + "|" + result.getType());
          break;
        case "19":
          reptile.setTaxinfonum(Integer.parseInt(result.getCount()));
          map.put("启信宝-欠税信息", result.getCount() + "|" + result.getType());
          break;
        case "20":
          reptile.setOutstocknum(Integer.parseInt(result.getCount()));
          map.put("启信宝-股权出质", result.getCount() + "|" + result.getType());
          break;
        case "21":
          reptile.setChattelmortnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-动产抵押", result.getCount() + "|" + result.getType());
          break;
        case "22":
          reptile.setAnnounnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-开庭公告", result.getCount() + "|" + result.getType());
          break;
        case "23":
          reptile.setInvestabroadnum(Integer.parseInt(result.getCount()));
          map.put("启信宝-对外投资", result.getCount() + "|" + result.getType());
          break;
        case "24":
          reptile.setBeedata(Integer.parseInt(result.getCount()));
          map.put("蜜蜂的失信信息", result.getCount() + "|" + result.getType());
          break;
        default:
          throw new IllegalArgumentException("无效的reptile参数：" + result.getType());
      }
    }
    return map;
  }
}
