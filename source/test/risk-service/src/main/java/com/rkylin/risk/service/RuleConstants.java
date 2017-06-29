package com.rkylin.risk.service;

/**
 * Created by ChenFumin on 2016-8-31.
 */
public final class RuleConstants {

  private RuleConstants() {} //防止被实例化

  /***************************课栈网*************************/

  /**
   * 渠道号
   */
  public static final String KEZHAN_CHANNEL_ID = "P000008";

  /**
   * 规则号
   */
  public static final String KEZHAN_ORDER_RULE = "P00000817";

  /**
   * 个人规则号
   */
  public static final String KEZHAN_PERSON_RULE = "P00000816";

  /**
   * 订单规则容器名称
   */
  public static final String KEZHAN_ORDER_KIE_CONTAINER =
      KEZHAN_CHANNEL_ID + ":" + KEZHAN_ORDER_RULE;

  /**
   * 个人规则容器名称
   */
  public static final String KEZHAN_PERSON_KIE_CONTAINER =
      KEZHAN_CHANNEL_ID + ":" + KEZHAN_PERSON_RULE;


  /***************************帮帮助学*************************/

  /**
   * 渠道号
   */
  public static final String BANGBANG_CHANNEL_ID = "M000020";



  /**
   * 订单规则号
   */
  public static final String BANGBANG_ORDER_RULE = "P00014627";

  /**
   * 个人规则号
   */
  public static final String BANGBANG_PERSON_RULE = "P00014628";

  /**
   * 帮帮的产品号
   */
  public static final String BANGBANG_PRODUCTID = "P000146";

  /**
   * 订单规则容器名称
   */
  public static final String BANGBANG_ORDER_KIE_CONTAINER =
      BANGBANG_CHANNEL_ID + ":" + BANGBANG_ORDER_RULE;

  /**
   * 个人规则容器名称
   */
  public static final String BANGBANG_PERSON_KIE_CONTAINER =
      BANGBANG_CHANNEL_ID + ":" + BANGBANG_PERSON_RULE;

  /**
   * 悦视觉
   */
  public static final String YUESHIJUE_CHANNEL_ID = "M000025";

  /**
   * 美容分期
   */
  public static final String MEIRONG_CHANNEL_ID = "M000028";

  /**
   * 旅游分期
   */
  public static final String LVYOU_CHANNEL_ID = "M000027";


  /**
   * 企业钱包
   */
  public static final String QIYE_CHANNEL_ID = "M000017";

  /**
   * 汽车融资
   */
  public static final String CAR_CHANNEL_ID = "M000026";

  /**
   * 天联在线
   */
  public static final String TIANLIAN_CHANNEL_ID = "M000021";

  /**
   * 天下房仓
   */
  public static final String FANG_CHANNEL_ID = "M000016";


  /**
   * 万众国旅
   */
  public static final String WANZHONG_CHANNEL_ID = "M000015";

  /**
   * 融数钱包
   */
  public static final String RONGSHU_CHANNEL_ID = "M666666";

  public static final String KEZHAN_CONST_ID = "M000004";

  /**
   * 杏仁app
   */
  public static final String XINGREN_CHANNEL_ID = "M000029";



}
