package com.rkylin.risk.credit.service.report;

import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 * Created by tomalloc on 16-8-24.
 */
public class BaiRongDasReportProducer extends ChildReportProducer<String> {

  private final JSONObject jsonObject;

  public BaiRongDasReportProducer(String s) {
    jsonObject = JSONObject.fromObject(s);
  }

  @Override public boolean validateData() {
    return false;
  }

  @Override public String code() {
    return jsonObject.getString("code");
  }

  private Map<String, String> map = new HashMap<>();

  {
    map.put("graduate", "毕业院校");
    map.put("educationDegree", "学历");
    map.put("enrolDate", "入学年份");
    map.put("specialityName", "专业");
    map.put("garauateTime", "毕业时间");
    map.put("studyResult", "毕业结论");
    map.put("studyStyle", "学历类型");
  }

  @Override public Map<ReportItem, ?> run() {
    String product = jsonObject.getString("product");
    Map<ReportItem, String> resultMap = new LinkedHashMap<>();
    if (product == null) {
      return resultMap;
    }

    JSONObject productJson = JSONObject.fromObject(product);
    if (productJson == null) {
      return resultMap;
    }
    String data = productJson.getString("data");
    if (data == null) {
      return resultMap;
    }
    JSONObject dataJson = JSONObject.fromObject(data);
    if (dataJson == null) {
      return resultMap;
    }
    String result = dataJson.getString("result");
    boolean status = dataJson.getBoolean("status");
    ReportItem resultReportItem = new ReportItem();
    resultReportItem.setCreditProduct(CreditProductType.BAIRONG);
    resultReportItem.setName("结果");
    resultReportItem.setKey("result");
    resultMap.put(resultReportItem, result);

    ReportItem statusReportItem = new ReportItem();
    statusReportItem.setCreditProduct(CreditProductType.BAIRONG);
    statusReportItem.setName("返回接口状态");
    statusReportItem.setKey("status");
    resultMap.put(statusReportItem, String.valueOf(status));

    if ("not_found".equals(result)) {
      return resultMap;
    }
    String info = dataJson.getString("info");

    if (info == null) {
      return resultMap;
    }
    JSONObject infoJson = JSONObject.fromObject(info);
    if (infoJson == null) {
      return resultMap;
    }
    Iterator iterable = infoJson.keys();
    while (iterable.hasNext()) {
      String key = iterable.next().toString();
      if (!map.containsKey(key)) {
        continue;
      }
      ReportItem reportItem = new ReportItem();
      reportItem.setCreditProduct(CreditProductType.BAIRONG);
      reportItem.setName(map.get(key));
      reportItem.setKey(key);
      Object valueObj = infoJson.get(key);
      String value = "";
      if (valueObj != null) {
        value = valueObj.toString();
      }
      resultMap.put(reportItem, value);
    }
    return resultMap;
  }
}
