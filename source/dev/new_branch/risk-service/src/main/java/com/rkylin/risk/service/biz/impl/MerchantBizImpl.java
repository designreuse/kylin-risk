package com.rkylin.risk.service.biz.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Course;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.service.CourseService;
import com.rkylin.risk.core.service.MerchantService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.service.bean.MerchantFactor;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import com.rkylin.risk.service.biz.MerchantBiz;
import com.rkylin.risk.service.biz.ReptileBiz;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;

/**
 * Created by lina on 2016-8-15.
 */
@Component("merchantBiz")
@Slf4j
public class MerchantBizImpl extends BaseBizImpl implements MerchantBiz {
  @Resource
  private LogicRuleCalBiz logicRuleCalBiz;
  @Resource
  private MerchantService merchantService;
  @Resource
  private CourseService courseService;

  @Resource
  private ReptileBiz reptileBiz;

  @Resource
  private ObjectMapper jsonMapper;

  @Value("${maven.groupPath}")
  private String groupPath;

  @Override public String newMerchantmsgHandle(MerchantFactor merchantFactor)
      throws IOException {
    LogicRuleBean logic = getMerchantLogicRuleFromFactor(merchantFactor);
    Merchant merchant = new Merchant();
    merchant.setBranchflag(merchantFactor.getBranchflag());
    Map map = calMerchantRule(logic, merchant);
    setMerchant(merchantFactor, merchant);
    List<Course> courses = setCourseInfo(merchantFactor);
    courseService.addCourseBatch(courses);
    merchantService.addMerchant(merchant);
    reptileBiz.requestVerifyReptile(2, merchantFactor.getCorporatename(),
        merchantFactor.getCertificatenumber(), "", merchantFactor.getCompanyname(),
        merchantFactor.getOrgancertificate(), merchantFactor.getCheckorderid());
    return jsonMapper.writeValueAsString(map);
  }

  private List<Course> setCourseInfo(MerchantFactor merchantFactor) throws IOException {
    List<Map<String, String>> courseInfos =
        jsonMapper.readValue(merchantFactor.getClasslist(), List.class);
    List<Course> courses = new ArrayList<>();
    if (courseInfos != null && !courseInfos.isEmpty()) {
      Course course = null;
      String courseType = null;
      String merchantId = merchantFactor.getUserid();
      String merchantName = merchantFactor.getCompanyname();
      for (Map<String, String> courseInfo : courseInfos) {
        course = new Course();
        courseType = courseInfo.get("classtype");
        switch (courseType) {
          case "1":
            course.setCourseType("IT类/技能类");
            break;
          case "2":
            course.setCourseType("语言类");
            break;
          case "3":
            course.setCourseType("K12类");
            break;
          case "4":
            course.setCourseType("驾校类");
            break;
          case "5":
            course.setCourseType("美妆类");
            break;
          case "6":
            course.setCourseType("学历教育类");
            break;
          case "99":
            course.setCourseType("其他类");
            break;
          default:
            course.setCourseType(null);
        }
        course.setMerchantId(merchantId);
        course.setMerchantName(merchantName);
        course.setCourseName(courseInfo.get("classname"));
        course.setCoursePrice(courseInfo.get("classprice"));
        course.setCourseTime(courseInfo.get("classtime"));
        courses.add(course);
      }
    }
    return courses;
  }

  @Override public String queryMerchantmsgHandle(String checkorderid)
      throws JsonProcessingException {
    Merchant merchant = merchantService.queryByCheckorderid(checkorderid);
    if (merchant == null) {
      return "";
    }
    LogicRuleBean logic = getMerchantLogicRuleFromMerchant(merchant);
    Map map = calMerchantRule(logic, merchant);
    merchantService.update(merchant);
    return jsonMapper.writeValueAsString(map);
  }

  @Override public String calcMerchantMsgHandle(String checkorderid)
      throws JsonProcessingException {
    Merchant merchant = merchantService.queryByCheckorderid(checkorderid);
    if (merchant == null) {
      log.info("无此机构");
      return "";
    }
    Amount areateach = merchant.getAreateach();
    Amount trainnumyear = merchant.getTrainnumyear();
    Amount classprice = merchant.getClassprice();
    log.info("教学总面积=" + areateach + ",年培训人数=" + trainnumyear + ",课程均价=" + classprice);
    if (areateach == null || areateach.isEqualTo(0) || trainnumyear == null || classprice == null) {
      return "";
    }
    Map map = new HashMap();
    int people = areateach.divide(new Amount(1.5), 0, RoundingMode.FLOOR).getValue().intValue();
    Amount income = trainnumyear.multiply(classprice);
    map.put("people", people);
    map.put("income", income.getValue());
    return jsonMapper.writeValueAsString(map);
  }

  private void setMerchant(MerchantFactor merchantFactor, Merchant merchant) {
    BeanMappper.fastCopy(merchantFactor, merchant);
    merchant.setMerchantid(merchantFactor.getUserid());
    merchant.setChannelid(merchantFactor.getConstid());
    merchant.setMerchantname(merchantFactor.getCompanyname());
    merchant.setCorpname(merchantFactor.getCompanyname());
    merchant.setCorporationid(merchantFactor.getBusinesslicense());
    merchant.setMerchstartdate(merchantFactor.getCertificatestartdate());
    merchant.setMerchduedate(merchantFactor.getCertificateexpiredate());
    merchant.setCorptype(merchantFactor.getCompanytype());
    merchant.setMerchantaddress(merchantFactor.getAddress());
    merchant.setTaxid(merchantFactor.getTaxregcard());
    merchant.setOrginstdid(merchantFactor.getOrgancertificate());
    merchant.setArrpid(merchantFactor.getAcuntopenlince());
    merchant.setOwnercertname(merchantFactor.getCorporatename());
    merchant.setOwnercerttp(merchantFactor.getCertificatetype());
    merchant.setOwnercertid(merchantFactor.getCertificatenumber());
    merchant.setTrainnumyear(amountValueOf(merchantFactor.getTrainingnumyear(), null));
    merchant.setTrainincomeyear(amountValueOf(merchantFactor.getTrainingincomeyear(), null));
    merchant.setAreateach(amountValueOf(merchantFactor.getAreateaching(), null));
    merchant.setProvincenum(merchantFactor.getProvincenum() == null ? null
        : Integer.parseInt(merchantFactor.getProvincenum()));
    merchant.setSubagencynum(merchantFactor.getSubagencynum() == null ? null
        : Integer.parseInt(merchantFactor.getSubagencynum()));
    merchant.setClassprice(amountValueOf(merchantFactor.getClassprice(), null));
  }

  private LogicRuleBean getMerchantLogicRuleFromFactor(MerchantFactor merchantFactor) {
    LogicRuleBean logic = new LogicRuleBean();
    logic.setProvincenum(amountValueOf(merchantFactor.getProvincenum(), null));
    logic.setSubagencynum(amountValueOf(merchantFactor.getSubagencynum(), null));
    logic.setTrainingnumyear(amountValueOf(merchantFactor.getTrainingnumyear(), null));
    logic.setTrainingincomeyear(amountValueOf(merchantFactor.getTrainingincomeyear(), null));
    logic.setFoundage(amountValueOf(merchantFactor.getFoundage(), null));
    logic.setAreateaching(amountValueOf(merchantFactor.getAreateaching(), null));
    return logic;
  }

  private LogicRuleBean getMerchantLogicRuleFromMerchant(Merchant merchant) {
    LogicRuleBean logic = new LogicRuleBean();
    logic.setProvincenum(amountValueOf(merchant.getProvincenum(), null));
    logic.setSubagencynum(amountValueOf(merchant.getSubagencynum(), null));
    logic.setTrainingnumyear(merchant.getTrainnumyear());
    logic.setTrainingincomeyear(merchant.getTrainincomeyear());
    logic.setFoundage(merchant.getFoundage());
    logic.setAreateaching(merchant.getAreateach());
    return logic;
  }

  private Map calMerchantRule(LogicRuleBean logic, Merchant merchant) {
    Map map = new LinkedHashMap();
    if ("1".equals(merchant.getBranchflag())) {
      ResultBean groupHeadResult = null;
      if (checkRule("P00000822", groupPath + "P000008")) {
        groupHeadResult = logicRuleCalBiz.calGroupHeadLogicRule(logic);
      }
      if (groupHeadResult != null && StringUtils.isEmpty(groupHeadResult.getOffMsg())) {
        map.put("全国集团类培训机构总部", "true");
        map.put("大型机构总部", "false");
        map.put("中小型机构", "false");
        merchant.setCheckstatus("1");
      } else {
        groupHeadResult = groupHeadResult != null ? groupHeadResult : new ResultBean();
        ResultBean largeHeadResult = null;
        map.put("全国集团类培训机构总部", groupHeadResult.getOffMsg());
        if (checkRule("P00000823", groupPath + "P000008")) {
          largeHeadResult = logicRuleCalBiz.calLargeHeadLogicRule(logic);
        }
        if (largeHeadResult != null && StringUtils.isEmpty(largeHeadResult.getOffMsg())) {
          map.put("大型机构总部", "true");
          map.put("中小型机构", "false");
          merchant.setCheckstatus("2");
        } else {
          largeHeadResult = largeHeadResult != null ? largeHeadResult : new ResultBean();
          map.put("大型机构总部", largeHeadResult.getOffMsg());
          ResultBean mediumResult = null;
          if (checkRule("P00000824", groupPath + "P000008")) {
            mediumResult = logicRuleCalBiz.calmediumRule(logic);
          }
          if (mediumResult != null && StringUtils.isEmpty(mediumResult.getOffMsg())) {
            map.put("中小型机构", "true");
            merchant.setCheckstatus("3");
          } else {
            mediumResult = mediumResult != null ? mediumResult : new ResultBean();
            map.put("中小型机构", mediumResult.getOffMsg());
          }
        }
      }
    } else if ("0".equals(merchant.getBranchflag())) {
      //无分支机构
      ResultBean largeBranchResult = null;
      if (checkRule("P00000825", groupPath + "P000008")) {
        largeBranchResult = logicRuleCalBiz.calLargeBranchLogicRule(logic);
      }
      if (largeBranchResult != null && StringUtils.isEmpty(largeBranchResult.getOffMsg())) {
        map.put("大型机构分支机构", "true");
        map.put("全国集团类培训机构分支机构", "false");
        merchant.setCheckstatus("4");
      } else {
        largeBranchResult = largeBranchResult != null ? largeBranchResult : new ResultBean();
        map.put("大型机构分支机构", largeBranchResult.getOffMsg());
        ResultBean groupBranchResult = null;
        if (checkRule("P00000825", groupPath + "P000008")) {
          groupBranchResult = logicRuleCalBiz.calGroupBranchLogicRule(logic);
        }
        if (groupBranchResult != null && StringUtils.isEmpty(groupBranchResult.getOffMsg())) {
          map.put("全国集团类培训机构分支机构", "true");
          merchant.setCheckstatus("5");
        } else {
          groupBranchResult = groupBranchResult != null ? groupBranchResult : new ResultBean();
          map.put("全国集团类培训机构分支机构", groupBranchResult.getOffMsg());
        }
      }
    }
    return map;
  }
}
