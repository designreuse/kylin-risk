package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.PersonFactor;
import java.util.Map;

/**
 * 鹏元征信接口
 *
 * @author qiuxian
 * @create 2016-08-29 11:37
 **/
public interface PengYuanCreditBiz {
  /**
   * 身份证号码及姓名
   *
   * @return Map
   */
  Map<String, String> creditPY(String name, String number);

  /**
   * 个人手机号码认证
   *<>暂时没有用到</>
   * @return Map
   */
  Map<String, Object> creditPhoneCheck(PersonFactor personFactor);
}
