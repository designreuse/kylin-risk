package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.PersonFactor;
import java.io.IOException;
import java.util.Map;

/**
 * 课栈&帮帮助学
 *
 * @author qiuxian
 * @create 2016-08-29 11:37
 **/
public interface KezhanBiz {
  /**
   * 下载文件并解密
   *
   * @param personFactor 个人信息
   * @return String
   */
  String downloadFile(PersonFactor personFactor);

  Map<String, Object> unzip(byte[] zipdatas) throws IOException;
}
