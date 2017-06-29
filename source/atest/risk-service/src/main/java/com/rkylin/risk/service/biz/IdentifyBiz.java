package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.PersonFactor;
import java.util.Map;

/**
 * OCR
 *
 * @author qiuxian
 * @create 2016-08-29 11:37
 **/
public interface IdentifyBiz {
  /**
   * 下载文件并解密
   *
   * @param factor 个人信息
   * @imageEntityMap 获取到的图片及图片MD5值
   * @return String
   */
  public String requestFaceresult(Map<String, Object> imageEntityMap,
      PersonFactor factor);

  public String requestYSJFaceresult(Map<String, Object> imageEntityMap,
      PersonFactor factor);
}
