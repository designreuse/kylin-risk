package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.PersonFactor;
import java.util.Map;

/**
 * 身份证识别接口
 *
 * @author qiuxian
 * @create 2016-08-29 11:37
 **/
public interface IdentifyBiz {
  /**
   * 下载文件并解密
   *
   * @param personFactor ROP上文件路径key
   * @return Map
   */
  Map<String, Object> downloadFile(PersonFactor personFactor);

  Map<String, Object> creditPY(PersonFactor personFactor, String name, String number);

  Map<String, Object> creditPhoneCheck(PersonFactor personFactor);


}
