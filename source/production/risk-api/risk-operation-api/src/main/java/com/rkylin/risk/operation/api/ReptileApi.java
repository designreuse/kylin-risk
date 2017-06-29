package com.rkylin.risk.operation.api;

import java.util.List;

/**
 * Created by lina on 2016-8-10. 爬虫接口
 */
public interface ReptileApi {
  /**
   * 获取爬虫网站列表
   */
  String reptileInfo(String checkorderId, String querytype, String hmac);

  /**
   * 获取爬虫网站明细
   */
  List<String> reptileDetail(String checkorderId, String querytype, String type, String hmac);
}
