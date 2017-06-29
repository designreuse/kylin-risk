package com.rkylin.risk.service.event;

import com.google.common.eventbus.Subscribe;
import com.rkylin.oprsystem.system.service.FlowStatusChangeService;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import com.rkylin.risk.service.bean.MitouBean;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.CreditBiz;
import com.rkylin.risk.service.biz.YueshijueBiz;
import com.rkylin.risk.service.biz.impl.BaseBizImpl;
import com.rkylin.risk.service.credit.BaiRongTerRequester;
import com.rkylin.risk.service.credit.UnionPayAdvisorsRequester;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qiuxian
 * @create 2016-09-28 18:33
 **/
@Slf4j
public class PersonMitouListener extends BaseBizImpl {
  @Resource
  private FlowStatusChangeService flowStatusChangeService;
  @Resource
  private  FlowStatusChangeService newflowStatusChangeService;
  @Resource
  private YueshijueBiz yueshijueBiz;
  @Resource
  private OperateFlowService operateFlowService;
  @Resource
  private UnionPayAdvisorsRequester unionPayAdvisorsRequester;

  //@Resource
  //private BaiRongDasRequester baiRongDasRequester;

  @Resource
  private BaiRongTerRequester baiRongTerRequester;

  @Resource
  private CreditBiz creditBiz;

  @Subscribe
  public void updateRiskMsgListen(MitouBean event) {
    //OCR接口
    String riskmessage = "";
    String ocrmsg = "";
    String mitoumsg = "";
    String mtresult = "";
    String unionpaymsg = "";
    String bairongmsg = "";
    if ((RuleConstants.YUESHIJUE_CHANNEL_ID.equals(event.getPersonFactor().getConstid())
        || RuleConstants.XINGREN_CHANNEL_ID.equals(event.getPersonFactor().getConstid()))
        && event.getPersonFactor().getTaccountbank() != null) {
      unionpaymsg = unionpayMsg(event.getPersonFactor());
      bairongmsg = baiRongMsg(event.getPersonFactor());
    }
    Map map = yueshijueBiz.handleYueShijue(event.getPersonFactor());
    ocrmsg = map.get("ocrmsg") == null ? "" : map.get("ocrmsg").toString();
    mtresult = map.get("mtresult") == null ? "" : map.get("mtresult").toString();
    //exception  代表接口调用过程中发生异常，需要boss再次发起，validatefalse代表数据校验不通过 refuse代表该渠道不调用米投接口
    //success  已成功提交数据，待取报告，datafalse 米投回复数据校验不通过，不用再提交数据
    if ("false".equals(mtresult) || "validatefalse".equals(mtresult)||"datafalse".equals(mtresult)) {
      mitoumsg = "米投返回：" + map.get("mtmsg").toString();
    }
    riskmessage =
        unionpaymsg + bairongmsg + ocrmsg + (event.getRiskmsg() == null ? "" : event.getRiskmsg());
    log.info("message------------------------" + riskmessage);
    OperateFlow operateFlow =
        operateFlowService.queryByCheckorderid(event.getPersonFactor().getCheckorderid());
    if (operateFlow == null) {
      operateFlow = new OperateFlow();
      operateFlow.setRiskmsg(riskmessage);
      operateFlow.setMtMsg(mitoumsg);
      if ("success".equals(mtresult)) {
        operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_WAIT);
      } else if ("exception".equals(mtresult)) {
        operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_FALURE);
      } else if ("validatefalse".equals(mtresult)) {
        operateFlow.setMtApiStatus(ApiMitouConstants.DATA_VALIDATE_FALSE);
      } else if("datafalse".equals(mtresult)){
        operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_FORBIDE);
      }
      operateFlow.setCheckorderid(event.getPersonFactor().getCheckorderid());
      operateFlow.setPersoncommitcount("1");
      operateFlowService.insert(operateFlow);
    } else {
      operateFlow.setRiskmsg(riskmessage);
      operateFlow.setMtMsg(mitoumsg);
      if ("success".equals(mtresult)) {
        operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_WAIT);
      } else if ("exception".equals(mtresult)) {
        operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_FALURE);
      } else if ("validatefalse".equals(mtresult)) {
        operateFlow.setMtApiStatus(ApiMitouConstants.DATA_VALIDATE_FALSE);
      } else if("datafalse".equals(mtresult)){
        operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_FORBIDE);
      }
      operateFlow.setPersoncommitcount("1");
      operateFlowService.updateOperteFlow(operateFlow);
    }

    if (StringUtils.isNotEmpty(riskmessage + mitoumsg)) {

      if("1".equals(event.getPersonFactor().getBusinessType())){
        log.info("大金融，修改新运营平台审批风险提示为：{}", riskmessage + mitoumsg);
        newflowStatusChangeService.updateFlowStatusId(event.getPersonFactor().getCheckorderid(),
            null, null, "风控系统返回：" + riskmessage + mitoumsg);
      }else{
        log.info("课栈卡，修改运营平台审批风险提示为：{}", riskmessage + mitoumsg);
        flowStatusChangeService.updateFlowStatusId(event.getPersonFactor().getCheckorderid(),
            null, null, "风控系统返回：" + riskmessage + mitoumsg);
      }
      //发送邮件-风险提示取消邮件发送
      /*sendMails(ocrmessage + mitoumsg, event.getPersonFactor().getConstid(),
          event.getPersonFactor().getUserid(), "WARNSET", Constants.MIDDLE_LEVEL);*/
      log.info("【dubbo服务】{},风控验证为中风险，原因：{}", event.getPersonFactor().getUserid(),
          riskmessage + mitoumsg);
    }
  }

  private String unionpayMsg(PersonFactor factor) {
    StringBuffer msg = new StringBuffer();
    msg.append("银联智策返回：");
    try {
      CreditRequestEntity creditRequestEntity = new CreditRequestEntity();
      creditRequestEntity.setName(factor.getUsername());
      creditRequestEntity.setBankCard(factor.getTcaccount());
      creditRequestEntity.setChannel(factor.getConstid());
      creditRequestEntity.setIdNumber(factor.getCertificatenumber());
      creditRequestEntity.setMobile(factor.getMobilephone());
      creditRequestEntity.setWorkflowId(factor.getCheckorderid());
      creditRequestEntity.setUserId(factor.getUserid());
      List<CreditResult> creditResultList =
          creditBiz.queryCreditResult(creditRequestEntity, unionPayAdvisorsRequester, null);
      if (creditResultList.size() > 0) {
        CreditResult creditResult = creditResultList.get(0);
        String json = creditResult.getResult();
        JSONObject js = JSONObject.fromObject(json);
        String resultcode = js.get("error_code") == null ? "" : js.get("error_code").toString();
        if ("0".equals(resultcode)) {
          JSONArray array = js.getJSONArray("data");
          String score = "";
          String scoretype = "";
          for (Object o : array) {
            JSONObject object = (JSONObject) o;
            score = object.getString("CSRL001");
            scoretype = object.getString("CSRL002");
            log.info("分数：" + object.getString("CSRL001"));
            log.info("类型：" + object.getString("CSRL002"));
          }
          String risktypemsg = scoretype;
          if (scoretype != null && !"\"null\"".equals(scoretype)) {
            int rsk_scoretype = Integer.parseInt(scoretype);
            if (rsk_scoretype == 9990) {
              risktypemsg = risktypemsg + "极大风险（黑名单）";
            } else if (rsk_scoretype == 9991) {
              risktypemsg = risktypemsg + "极小风险（白名单）";
            } else if (rsk_scoretype <= 4) {
              risktypemsg = risktypemsg + "高逾期风险";
            } else if (rsk_scoretype >= 5 && rsk_scoretype <= 6) {
              risktypemsg = risktypemsg + "中高逾期风险";
            } else if (rsk_scoretype >= 7 && rsk_scoretype <= 11) {
              risktypemsg = risktypemsg + "中低逾期风险";
            } else {
              risktypemsg = risktypemsg + "没有找到匹配的风险得分类型";
            }
          } else {
            risktypemsg = risktypemsg + "未查询到风险得分分类";
          }
          String riskscoremsg = score + ",";
          if (score != null && !"\"null\"".equals(score)) {
            int rsk_score = Integer.parseInt(score);
            if (rsk_score == 9990) {
              riskscoremsg = riskscoremsg + "极大风险（黑名单）";
            } else if (rsk_score == 9991) {
              riskscoremsg = riskscoremsg + "极小风险（白名单）";
            } else if (rsk_score > 0 && rsk_score <= 400) {
              riskscoremsg = riskscoremsg + "高逾期风险";
            } else if (rsk_score >= 401 && rsk_score <= 600) {
              riskscoremsg = riskscoremsg + "中高逾期风险";
            } else if (rsk_score >= 601 && rsk_score <= 1000) {
              riskscoremsg = riskscoremsg + "中低逾期风险";
            }
          } else {
            riskscoremsg = "未查询到风险得分";
          }
          msg.append("风险得分分类：");
          msg.append(risktypemsg);
          msg.append(";风险得分：");
          msg.append(riskscoremsg);
        } else if ("10005".equals(resultcode)) {
          msg.append("智策云平台无此参数结果数据");
        } else {
          sendMails(msg.toString() + "异常返回码" + resultcode, factor.getConstid(),
              factor.getUserid(), "WARNSET", Constants.MIDDLE_LEVEL);
        }
      } else {
        msg.append("银联智策list返回为空");
      }
    } catch (Exception e) {
      msg.append("智策云API异常");
      e.printStackTrace();
    }
    msg.append(";");
    log.info("---------" + msg);
    return msg.toString();
  }

  private String baiRongMsg(PersonFactor factor) {
    StringBuffer msg = new StringBuffer();
    msg.append("百融返回：");
    try {
      CreditRequestEntity creditRequestEntity = new CreditRequestEntity();
      creditRequestEntity.setName(factor.getUsername());
      creditRequestEntity.setBankCard(factor.getTcaccount());
      creditRequestEntity.setChannel(factor.getConstid());
      creditRequestEntity.setIdNumber(factor.getCertificatenumber());
      creditRequestEntity.setMobile(factor.getMobilephone());
      creditRequestEntity.setWorkflowId(factor.getCheckorderid());
      creditRequestEntity.setUserId(factor.getUserid());
      List<CreditResult> creditResultList =
          creditBiz.queryCreditResult(creditRequestEntity, baiRongTerRequester,
              new String[] {"SpecialList_c"});
      if (creditResultList.size() > 0) {
        CreditResult creditResult = creditResultList.get(0);
        String json = creditResult.getResult();
        JSONObject js = JSONObject.fromObject(json);
        String resultcode =
            js.get("flag_specialList_c") == null ? "" : js.get("flag_specialList_c").toString();
        if ("0".equals(resultcode)) {
          msg.append("未命中百融黑名单");
        } else if ("1".equals(resultcode)) {
          msg.append("命中百融黑名单");
        } else {
          msg.append("未知结果");
        }
      }
    } catch (Exception e) {
      msg.append("调用百融API异常");
      e.printStackTrace();
    }
    msg.append(";");
    log.info("----------" + msg);
    return msg.toString();
  }
}
