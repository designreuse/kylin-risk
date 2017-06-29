package com.rkylin.risk.core;

/**
 * 常量类
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/19 version: 1.0
 */
public final class Constants {
  public static final String USER_PASSWORD_MAIL_TEMPLATE = "template/mail/user-password.vm";
  public static final String USER_REMIND_MAIL_TEMPLATE = "template/user-remind.vm";
  public static final String QUARTZ_ERROR_MAIL_TEMPLATE = "template/mail/quartz-error.vm";
  /* 状态常量 BEGIN */
  public static final String ACTIVE = "00";
  //无效
  public static final String INACTIVE = "01";
  //删除
  public static final String DELETE = "99";
  //新增待审核  @qiuxian
  public static final String ADD_UNVERIFY = "02";
  //移除待审核  @qiuxian
  public static final String REMOVE_UNVERIFY = "03";
  //审核拒绝  @qiuxian
  public static final String UNTHREAD = "04";
  //修改待审核  @qiuxian
  public static final String MODIFY_UNVERIFY = "05";

    /* 状态常量 END*/

  /* 黑白灰名单*/
  public static final String BLACK = "01";
  public static final String WHITE = "02";
  public static final String GRAY = "03";

  /* 名单来源*/
  public static final String INNER = "01";
  public static final String OUTER = "02";

  /* 规则组业务类型*/
  public static final String BASIC_INFO = "01";
  public static final String ORDER_INFO = "02";
  public static final String NATIONAL_ORG_HEAD = "03";
  public static final String NATIONAL_ORG_BRANCH = "04";
  public static final String LARGE_ORG_HEAD = "05";
  public static final String LARGE_ORG_BRANCH = "06";
  public static final String MEDIUM_ORG = "07";

  /* 标志常量 BEGIN */
  public static final String TRUE = "1";
  public static final String FALSE = "0";
    /* 标志常量 END*/

  public static final String WARN_EMAIL = "2";
  public static final String WARN_MSG = "1";

  /* 密码有效时长 END*/
  public static final Integer PWDEXPDATE = 90;
  //密码错误次数限制
  public static final Integer PWDERRTIMES = 5;
  //密码错误时间限制：天
  public static final Integer PWDERRDATE = 0;

  public static final String DEFAULT_PWD = "qwer1234";

  /*全局权限检查开关*/
  public static final String AUTH_FLAG = "TRUE";
  public static final String UNVERIFY = "TRUE";

  /*反洗钱可疑交易来源  手动导入*/
  public static final String SOURCE = "0";

  public static final String RISK_CHECK_USER = "风控审核人员";
  public static final String RISK_OPER = "风控操作人员";

  public static final String UPLOAD_PATH = "/home/yxgly/upload/bwgfile/";
  public static final String RULE_MANAGER_NAME = "规则管理人员";

  //预警状态为开启
  public static final String WARN_STATUS_00 = "00";
  //关闭
  public static final String WARN_STATUS_01 = "01";
  //类型为身份证 @qiuxian
  public static final String IDCARDTYPE = "111";
  public static final String RISK_MAIL = "rkylin_order@aliyun.com";
  //风险等级 @qiuxian
  public static final String HIGH_LEVEL = "00";
  public static final String MIDDLE_LEVEL = "01";
  public static final String LOW_LEVEL = "02";

  //生成案例的类型:可疑交易
  public static final String MANUAL = "0";
  //风险事件
  public static final String SYSTEM = "1";

  public static final String FILE_PATH = "D:/upload/";
  //交易时间前一小时
  public static final Integer TXNTIME_MIN = 1;
  //交易金额除以5000是否为整数
  public static final Double DIV5000 = 5000d;
  //交易金额除以100
  public static final int DIV100 = 100;

  //网关支付
  public static final String B2CREC = "01";
  //快捷支付
  public static final String FASTREC = "02";

  //网银充值获取不到银行卡号，故“卡号前8位近1小时提现笔数”，取该因子50分的返回码@qiuxian
  public static final String DEFAULT_RECFACTOR_CARDNUMEX = "recCardnumex03";
  //默认客户因子分数
  public static final String DEFAULT_CUSTOMER_SCORE = "15";

  //交易前一个小时
  public static final String DEPOSIT_NUMBER_HOUR = "minunus_1hour";
  //交易前一个小时
  public static final String RECHARGE_NUMBER_DAY = "allday";



  //判断某用户是否包含全部产品ID属性(操作员管理)
  public static final String ALL_PRODUCT = "-1";
  //规则编号前置标志
  public static final String RULE_CODE_FLAG = "RULE";
  //导出文件时的内容类型
  public static final String CONTENT_TYPE = "application/x-download";
  //个人爬虫超时时间(分钟)
  public static final int REPTILEPERTIMEOUT = 5;
  //机构爬虫超时时间(分钟)
  public static final int REPTILEORGTIMEOUT = 30;
  //系统自定义发送邮件的操作员
  public static final String SYSTEM_EMAIL_OPERATOR =
      "huzixiong@rongcapital.cn,lina@rongcapital.cn";

  private Constants() {
  }

}
