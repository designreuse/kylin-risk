package com.rkylin.risk.service.biz;

import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.bean.PersonFactor;
import java.util.Map;

/**
 * 米投
 *
 * @author qiuxian
 * @create 2016-08-29 11:37
 **/
public interface MitouRiskBiz {
  /**
   * 下载文件并解密
   *
   * @param factor 个人信息
   * @map 获取到的图片及图片MD5值
   * @return Map
   */
   Map requestMitouRisk(Map<String, Object> map, PersonFactor factor,String simility);

  /**
   * 下载文件并解密
   *
   * @param order  订单信息
   * @return Map
   */
  Map requestMitouForOrder(SimpleOrder order);


}
