package com.rkylin.risk.service.api;

/**
 * Created by luke on 2016/11/4.
 */
public final class ApiMitouConstants {

  public static final String VALID_MOBILE = "1";

  /**
   * 注册用户API
   */
  public static final String REGISTER_API = "qapi/run.php?act=login&mod=reg";

  /**
   * 获取 token API
   */
  public static final String GET_TOKEN_API = "qapi/run.php?act=login";

  /**
   * 获取用户信息API
   */
  public static final String GET_USER_INFO_API = "qapi/run.php?act=login&mod=get";

  /**
   * 用户登陆API
   */
  public static final String LOGIN_API = "wap/index.php?ctl=apilogin";



  //订单缺少数据
  public static final String DATA_VALIDATE_FALSE="0";
  //已提交米投风控参数接口，待回调报告
  public static final String MITOU_API_WAIT="1";
  //提交米投风控参数接口 发生异常，待重新提交参数
  public static final String MITOU_API_FALURE="2";
  //成功回调米投风控报告
  public static final String MITOU_API_SUCCESS="3";
  //米投风控报告永久打回，不能再次提交数据
  public static final String MITOU_API_FORBIDE="4";

  public static final String NOT_COMMIT="not_commit";

  public static final String COMMIT="commit";

  public static final String REFUSE="refuse";

  public static final String ACCEPT="accept";

  public static final String FOREVER_REFUSE="foreverRefuse";







  private ApiMitouConstants() {}
}