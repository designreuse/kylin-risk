package com.rkylin.risk.credit.service.report;

import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.credit.exception.RiskCreditTokenExpireException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;

/**
 * Created by tomalloc on 16-8-2.
 */
public class BaiRongTerReportProducer extends ChildReportProducer<String> {
  private Map<String, String> map = new HashMap<>();

  {
    map.put("Rule_final_decision", "最终决策结果");
    map.put("Rule_final_weight", "最终规则评分");
    //
    map.put("flag_ruleapplyloan", "多次申请输出标识");
    map.put("flag_rulespeciallist", "黑名单输出标识");
    //
    map.put("Rule_name_QJS010", "命中规则名称:银行不良");
    map.put("Rule_name_QJS015", "命中规则名称:银行欺诈");
    map.put("Rule_name_QJS020", "命中规则名称:直系亲属银行不良");
    map.put("Rule_name_QJS025", "命中规则名称:直系亲属银行欺诈");
    map.put("Rule_name_QJS030", "命中规则名称:朋友等关系银行不良");
    map.put("Rule_name_QJS035", "命中规则名称:朋友等关系银行欺诈");
    map.put("Rule_name_QJS040", "命中规则名称:银行短时逾期或拒绝");
    map.put("Rule_name_QJS045", "命中规则名称:直系亲属银行短时逾期或拒绝");
    map.put("Rule_name_QJS050", "命中规则名称:小贷或P2P不良");
    map.put("Rule_name_QJS060", "命中规则名称:直系亲属小贷或P2P不良");
    map.put("Rule_name_QJS070", "命中规则名称:朋友等其他关系小贷或P2P不良");
    map.put("Rule_name_QJS080", "命中规则名称:小贷或P2P短时逾期或拒绝");
    map.put("Rule_name_QJS085", "命中规则名称:直系亲属小贷或P2P短时逾期或拒绝");
    map.put("Rule_name_QJS090", "命中规则名称:法院失信人");
    map.put("Rule_name_QJS100", "命中规则名称:法院被执行人");
    map.put("Rule_name_QJS110", "命中规则名称:电信欠费");
    map.put("Rule_name_QJS120", "命中规则名称:直系亲属电信欠费");
    map.put("Rule_name_QJS130", "命中规则名称:朋友等其他关系电信欠费");
    map.put("Rule_name_QJF010", "命中规则名称:在银行多次申请-高度");
    map.put("Rule_name_QJF020", "命中规则名称:在银行多次申请-中度");
    map.put("Rule_name_QJF025", "命中规则名称:在银行多次申请-低度");
    map.put("Rule_name_QJF030", "命中规则名称:在非银机构多次申请-高度");
    map.put("Rule_name_QJF040", "命中规则名称:在非银机构多次申请-中度");
    map.put("Rule_name_QJF045", "命中规则名称:在非银机构多次申请-低度");
    //
    map.put("Rule_weight_QJS010", "命中规则权重:银行不良");
    map.put("Rule_weight_QJS015", "命中规则权重:银行欺诈");
    map.put("Rule_weight_QJS020", "命中规则权重:直系亲属银行不良");
    map.put("Rule_weight_QJS025", "命中规则权重:直系亲属银行欺诈");
    map.put("Rule_weight_QJS030", "命中规则权重:朋友等关系银行不良");
    map.put("Rule_weight_QJS035", "命中规则权重:朋友等关系银行欺诈");
    map.put("Rule_weight_QJS040", "命中规则权重:银行短时逾期或拒绝");
    map.put("Rule_weight_QJS045", "命中规则权重:直系亲属银行短时逾期或拒绝");
    map.put("Rule_weight_QJS050", "命中规则权重:小贷或P2P不良");
    map.put("Rule_weight_QJS060", "命中规则权重:直系亲属小贷或P2P不良");
    map.put("Rule_weight_QJS070", "命中规则权重:朋友等其他关系小贷或P2P不良");
    map.put("Rule_weight_QJS080", "命中规则权重:小贷或P2P短时逾期或拒绝");
    map.put("Rule_weight_QJS085", "命中规则权重:直系亲属小贷或P2P短时逾期或拒绝");
    map.put("Rule_weight_QJS090", "命中规则权重:法院失信人");
    map.put("Rule_weight_QJS100", "命中规则权重:法院被执行人");
    map.put("Rule_weight_QJS110", "命中规则权重:电信欠费");
    map.put("Rule_weight_QJS120", "命中规则权重:直系亲属电信欠费");
    map.put("Rule_weight_QJS130", "命中规则权重:朋友等其他关系电信欠费");
    map.put("Rule_weight_QJF010", "命中规则权重:在银行多次申请-高度");
    map.put("Rule_weight_QJF020", "命中规则权重:在银行多次申请-中度");
    map.put("Rule_weight_QJF025", "命中规则权重:在银行多次申请-低度");
    map.put("Rule_weight_QJF030", "命中规则权重:在非银机构多次申请-高度");
    map.put("Rule_weight_QJF040", "命中规则权重:在非银机构多次申请-中度");
    map.put("Rule_weight_QJF045", "命中规则权重:在非银机构多次申请-低度");
    //
    map.put("sl_id_bank_bad", "通过身份证查询银行不良");
    map.put("sl_id_bank_overdue", "通过身份证查询银行短时逾期");
    map.put("sl_id_bank_fraud", "通过身份证查询银行欺诈");
    map.put("sl_id_bank_refuse", "通过身份证查询银行拒绝");
    map.put("sl_id_p2p_bad", "通过身份证查询小贷或P2P不良");
    map.put("sl_id_p2p_overdue", "通过身份证查询小贷或P2P短时逾期");
    map.put("sl_id_p2p_fraud", "通过身份证查询小贷或P2P欺诈");
    map.put("sl_id_p2p_refuse", "通过身份证查询小贷或P2P拒绝");
    map.put("sl_id_phone_overdue", "通过身份证查询电信欠费");
    map.put("sl_id_court_bad", "通过身份证查询法院失信人");
    map.put("sl_id_court_executed", "通过身份证查询法院被执行人");
    map.put("sl_id_nbank_p2p_bad", "通过身份证查询P2P不良");
    map.put("sl_id_nbank_p2p_overdue", "通过身份证查询P2P短时逾期");
    map.put("sl_id_nbank_p2p_fraud", "通过身份证查询P2P欺诈");
    map.put("sl_id_nbank_p2p_refuse", "通过身份证查询P2P拒绝");
    map.put("sl_id_nbank_mc_bad", "通过身份证查询小贷不良");
    map.put("sl_id_nbank_mc_overdue", "通过身份证查询小贷短时逾期");
    map.put("sl_id_nbank_mc_fraud", "通过身份证查询小贷欺诈");
    map.put("sl_id_nbank_mc_refuse", "通过身份证查询小贷拒绝");
    map.put("sl_id_nbank_cf_bad", "通过身份证查询消费金融不良");
    map.put("sl_id_nbank_cf_overdue", "通过身份证查询消费金融短时逾期");
    map.put("sl_id_nbank_cf_fraud", "通过身份证查询消费金融欺诈");
    map.put("sl_id_nbank_cf_refuse", "通过身份证查询消费金融拒绝");
    map.put("sl_id_nbank_other_bad", "通过身份证查询非银其他不良");
    map.put("sl_id_nbank_other_overdue", "通过身份证查询非银其他短时逾期");
    map.put("sl_id_nbank_other_fraud", "通过身份证查询非银其他欺诈");
    map.put("sl_id_nbank_other_refuse", "通过身份证查询非银其他拒绝");
    map.put("sl_lm_cell_bank_bad", "通过联系人手机查询银行不良");
    map.put("sl_lm_cell_bank_overdue", "通过联系人手机查询银行短时逾期");
    map.put("sl_lm_cell_bank_fraud", "通过联系人手机查询银行欺诈");
    map.put("sl_lm_cell_bank_refuse", "通过联系人手机查询银行拒绝");
    map.put("sl_lm_cell_phone_overdue", "通过联系人手机查询电信欠费");
    map.put("sl_lm_cell_nbank_p2p_bad", "通过联系人手机查询P2P不良");
    map.put("sl_lm_cell_nbank_p2p_overdue", "通过联系人手机查询P2P短时逾期");
    map.put("sl_lm_cell_nbank_p2p_fraud", "通过联系人手机查询P2P欺诈");
    map.put("sl_lm_cell_nbank_p2p_refuse", "通过联系人手机查询P2P拒绝");
    map.put("sl_lm_cell_nbank_mc_bad", "通过联系人手机查询小贷不良");
    map.put("sl_lm_cell_nbank_mc_overdue", "通过联系人手机查询小贷短时逾期");
    map.put("sl_lm_cell_nbank_mc_fraud", "通过联系人手机查询小贷欺诈");
    map.put("sl_lm_cell_nbank_mc_refuse", "通过联系人手机查询小贷拒绝");
    map.put("sl_lm_cell_nbank_cf_bad", "通过联系人手机查询消费金融不良");
    map.put("sl_lm_cell_nbank_cf_overdue", "通过联系人手机查询消费金融短时逾期");
    map.put("sl_lm_cell_nbank_cf_fraud", "通过联系人手机查询消费金融欺诈");
    map.put("sl_lm_cell_nbank_cf_refuse", "通过联系人手机查询消费金融拒绝");
    map.put("sl_lm_cell_nbank_other_bad", "通过联系人手机查询非银其他不良");
    map.put("sl_lm_cell_nbank_other_overdue", "通过联系人手机查询非银其他短时逾期");
    map.put("sl_lm_cell_nbank_other_fraud", "通过联系人手机查询非银其他欺诈");
    map.put("sl_lm_cell_nbank_other_refuse", "通过联系人手机查询非银其他拒绝");
    map.put("sl_cell_bank_bad", "通过手机号查询银行不良");
    map.put("sl_cell_bank_overdue", "通过手机号查询银行短时逾期");
    map.put("sl_cell_bank_fraud", "通过手机号查询银行欺诈");
    map.put("sl_cell_bank_refuse", "通过手机号查询银行拒绝");
    map.put("sl_cell_p2p_bad", "通过手机号查询小贷或P2P不良");
    map.put("sl_cell_p2p_overdue", "通过手机号查询小贷或P2P短时逾期");
    map.put("sl_cell_p2p_fraud", "通过手机号查询小贷或P2P欺诈");
    map.put("sl_cell_p2p_refuse", "通过手机号查询小贷或P2P拒绝");
    map.put("sl_cell_phone_overdue", "通过手机号查询电信欠费");
    map.put("sl_cell_nbank_p2p_bad", "通过手机号查询P2P不良");
    map.put("sl_cell_nbank_p2p_overdue", "通过手机号查询P2P短时逾期");
    map.put("sl_cell_nbank_p2p_fraud", "通过手机号查询P2P欺诈");
    map.put("sl_cell_nbank_p2p_refuse", "通过手机号查询P2P拒绝");
    map.put("sl_cell_nbank_mc_bad", "通过手机号查询小贷不良");
    map.put("sl_cell_nbank_mc_overdue", "通过手机号查询小贷短时逾期");
    map.put("sl_cell_nbank_mc_fraud", "通过手机号查询小贷欺诈");
    map.put("sl_cell_nbank_mc_refuse", "通过手机号查询小贷拒绝");
    map.put("sl_cell_nbank_cf_bad", "通过手机号查询消费金融不良");
    map.put("sl_cell_nbank_cf_overdue", "通过手机号查询消费金融短时逾期");
    map.put("sl_cell_nbank_cf_fraud", "通过手机号查询消费金融欺诈");
    map.put("sl_cell_nbank_cf_refuse", "通过手机号查询消费金融拒绝");
    map.put("sl_cell_nbank_other_bad", "通过手机号查询非银其他不良");
    map.put("sl_cell_nbank_other_overdue", "通过手机号查询非银其他短时逾期");
    map.put("sl_cell_nbank_other_fraud", "通过手机号查询非银其他欺诈");
    map.put("sl_cell_nbank_other_refuse", "通过手机号查询非银其他拒绝");
    map.put("sl_gid_bank_bad", "通过百融标识查询银行不良");
    map.put("sl_gid_bank_overdue", "通过百融标识查询银行短时逾期");
    map.put("sl_gid_bank_fraud", "通过百融标识查询银行欺诈");
    map.put("sl_gid_bank_refuse", "通过百融标识查询银行拒绝");
    map.put("sl_gid_p2p_bad", "通过百融标识查询小贷或P2P不良");
    map.put("sl_gid_p2p_overdue", "通过百融标识查询小贷或P2P短时逾期");
    map.put("sl_gid_p2p_fraud", "通过百融标识查询小贷或P2P欺诈");
    map.put("sl_gid_p2p_refuse", "通过百融标识查询小贷或P2P拒绝");
    map.put("sl_gid_phone_overdue", "通过百融标识查询电信欠费");
    map.put("sl_gid_nbank_p2p_bad", "通过百融用户全局标识查询P2P不良");
    map.put("sl_gid_nbank_p2p_overdue", "通过百融用户全局标识查询P2P短时逾期");
    map.put("sl_gid_nbank_p2p_fraud", "通过百融用户全局标识查询P2P欺诈");
    map.put("sl_gid_nbank_p2p_refuse", "通过百融用户全局标识查询P2P拒绝");
    map.put("sl_gid_nbank_mc_bad", "通过百融用户全局标识查询小贷不良");
    map.put("sl_gid_nbank_mc_overdue", "通过百融用户全局标识查询小贷短时逾期");
    map.put("sl_gid_nbank_mc_fraud", "通过百融用户全局标识查询小贷欺诈");
    map.put("sl_gid_nbank_mc_refuse", "通过百融用户全局标识查询小贷拒绝");
    map.put("sl_gid_nbank_cf_bad", "通过百融用户全局标识查询消费金融不良");
    map.put("sl_gid_nbank_cf_overdue", "通过百融用户全局标识查询消费金融短时逾期");
    map.put("sl_gid_nbank_cf_fraud", "通过百融用户全局标识查询消费金融欺诈");
    map.put("sl_gid_nbank_cf_refuse", "通过百融用户全局标识查询消费金融拒绝");
    map.put("sl_gid_nbank_other_bad", "通过百融用户全局标识查询非银其他不良");
    map.put("sl_gid_nbank_other_overdue", "通过百融用户全局标识查询非银其他短时逾期");
    map.put("sl_gid_nbank_other_fraud", "通过百融用户全局标识查询非银其他欺诈");
    map.put("sl_gid_nbank_other_refuse", "通过百融用户全局标识查询非银其他拒绝");
    map.put("al_m3_gid_bank_selfnum", "近3个月百融标识在本银行机构的申请次数");
    map.put("al_m3_gid_bank_allnum", "近3个月百融标识在百融银行合作机构申请次数");
    map.put("al_m3_gid_bank_orgnum", "近3个月百融标识在百融银行合作机构申请机构数");
    map.put("al_m3_gid_notbank_selfnum", "近3个月百融标识在本非银机构申请次数");
    map.put("al_m3_gid_notbank_allnum", "近3个月百融标识在百融非银合作机构申请次数");
    map.put("al_m3_gid_notbank_orgnum", "近3个月百融标识在百融非银合作机构申请机构数");
    map.put("al_m3_id_bank_selfnum", "近3个月身份证在本银行机构的申请次数");
    map.put("al_m3_id_bank_allnum", "近3个月身份证在百融银行合作机构申请次数");
    map.put("al_m3_id_bank_orgnum", "近3个月身份证在百融银行合作机构申请机构数");
    map.put("al_m3_id_notbank_selfnum", "近3个月身份证在本非银机构申请次数");
    map.put("al_m3_id_notbank_allnum", "近3个月身份证在百融非银合作机构申请次数");
    map.put("al_m3_id_notbank_orgnum", "近3个月身份证在百融非银合作机构申请机构数");
    map.put("al_m3_cell_bank_selfnum", "近3个月手机号在本银行机构的申请次数");
    map.put("al_m3_cell_bank_allnum", "近3个月手机号在百融银行合作机构申请次数");
    map.put("al_m3_cell_bank_orgnum", "近3个月手机号在百融银行合作机构申请机构数");
    map.put("al_m3_cell_notbank_selfnum", "近3个月手机号在本非银机构申请次数");
    map.put("al_m3_cell_notbank_allnum", "近3个月手机号在百融非银合作机构申请次数");
    map.put("al_m3_cell_notbank_orgnum", "近3个月手机号在百融非银合作机构申请机构数");
    map.put("al_m6_gid_bank_selfnum", "近6个月百融标识在本银行机构的申请次数");
    map.put("al_m6_gid_bank_allnum", "近6个月百融标识在百融银行合作机构申请次数");
    map.put("al_m6_gid_bank_orgnum", "近6个月百融标识在百融银行合作机构申请机构数");
    map.put("al_m6_gid_notbank_selfnum", "近6个月百融标识在本非银机构申请次数");
    map.put("al_m6_gid_notbank_allnum", "近6个月百融标识在百融非银合作机构申请次数");
    map.put("al_m6_gid_notbank_orgnum", "近6个月百融标识在百融非银合作机构申请机构数");
    map.put("al_m6_id_bank_selfnum", "近6个月身份证在本银行机构的申请次数");
    map.put("al_m6_id_bank_allnum", "近6个月身份证在百融银行合作机构申请次数");
    map.put("al_m6_id_bank_orgnum", "近6个月身份证在百融银行合作机构申请机构数");
    map.put("al_m6_id_notbank_selfnum", "近6个月身份证在本非银机构申请次数");
    map.put("al_m6_id_notbank_allnum", "近6个月身份证在百融非银合作机构申请次数");
    map.put("al_m6_id_notbank_orgnum", "近6个月身份证在百融非银合作机构申请机构数");
    map.put("al_m6_cell_bank_selfnum", "近6个月手机号在本银行机构的申请次数");
    map.put("al_m6_cell_bank_allnum", "近6个月手机号在百融银行合作机构申请次数");
    map.put("al_m6_cell_bank_orgnum", "近6个月手机号在百融银行合作机构申请机构数");
    map.put("al_m6_cell_notbank_selfnum", "近6个月手机号在本非银机构申请次数");
    map.put("al_m6_cell_notbank_allnum", "近6个月手机号在百融非银合作机构申请次数");
    map.put("al_m6_cell_notbank_orgnum", "近6个月手机号在百融非银合作机构申请机构数");
    map.put("al_m12_gid_bank_selfnum", "近12个月百融标识在本银行机构的申请次数");
    map.put("al_m12_gid_bank_allnum", "近12个月百融标识在百融银行合作机构申请次数");
    map.put("al_m12_gid_bank_orgnum", "近12个月百融标识在百融银行合作机构申请机构数");
    map.put("al_m12_gid_notbank_selfnum", "近12个月百融标识在本非银机构申请次数");
    map.put("al_m12_gid_notbank_allnum", "近12个月百融标识在百融非银合作机构申请次数");
    map.put("al_m12_gid_notbank_orgnum", "近12个月百融标识在百融非银合作机构申请机构数");
    map.put("al_m12_id_bank_selfnum", "近12个月身份证在本银行机构的申请次数");
    map.put("al_m12_id_bank_allnum", "近12个月身份证在百融银行合作机构申请次数");
    map.put("al_m12_id_bank_orgnum", "近12个月身份证在百融银行合作机构申请机构数");
    map.put("al_m12_id_notbank_selfnum", "近12个月身份证在本非银机构申请次数");
    map.put("al_m12_id_notbank_allnum", "近12个月身份证在百融非银合作机构申请次数");
    map.put("al_m12_id_notbank_orgnum", "近12个月身份证在百融非银合作机构申请机构数");
    map.put("al_m12_cell_bank_selfnum", "近12个月手机号在本银行机构的申请次数");
    map.put("al_m12_cell_bank_allnum", "近12个月手机号在百融银行合作机构申请次数");
    map.put("al_m12_cell_bank_orgnum", "近12个月手机号在百融银行合作机构申请机构数");
    map.put("al_m12_cell_notbank_selfnum", "近12个月手机号在本非银机构申请次数");
    map.put("al_m12_cell_notbank_allnum", "近12个月手机号在百融非银合作机构申请次数");
    map.put("al_m12_cell_notbank_orgnum", "近12个月手机号在百融非银合作机构申请机构数");
  }

  private Set<String> set = new HashSet<>();

  {
    set.add("code");
    set.add("swift_number");
  }

  private JSONObject jsonObject;

  public BaiRongTerReportProducer(String s) {
    jsonObject = JSONObject.fromObject(s);
  }

  @Override public boolean validateData() {
    String code = jsonObject.getString("code");
    if ("00".equals(code)) {
      return true;
    } else if ("100002".equals(code)) {
      //匹配结果为空
      return true;
    } else if ("100007".equals(code)) {
      throw new RiskCreditTokenExpireException("token过期");
    }
    return false;
  }

  @Override public String code() {
    return jsonObject.getString("code");
  }

  @Override public Map<ReportItem, ?> run() {
    Iterator iterable = jsonObject.keys();
    Map<ReportItem, String> resultMap = new LinkedHashMap<>();
    while (iterable.hasNext()) {
      String key = iterable.next().toString();
      if (set.contains(key)) {
        continue;
      }
      String readableKey = key;
      if (map.containsKey(key)) {
        readableKey = map.get(key);
      }
      Object valueObj = jsonObject.get(key);
      String value = "";
      if (valueObj != null) {
        value = valueObj.toString();
      }
      ReportItem item = new ReportItem();
      item.setName(readableKey);
      item.setKey(key);
      item.setCreditProduct(CreditProductType.BAIRONG);
      resultMap.put(item, value);
    }
    return resultMap;
  }
}
