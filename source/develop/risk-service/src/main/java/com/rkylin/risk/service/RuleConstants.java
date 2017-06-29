package com.rkylin.risk.service;

/**
 * Created by ChenFumin on 2016-8-31.
 */
public class RuleConstants {
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
   * 订单规则容器名称
   */
  public static final String BANGBANG_ORDER_KIE_CONTAINER =
      BANGBANG_CHANNEL_ID + ":" + BANGBANG_ORDER_RULE;

  /**
   * 个人规则容器名称
   */
  public static final String BANGBANG_PERSON_KIE_CONTAINER =
      BANGBANG_CHANNEL_ID + ":" + BANGBANG_PERSON_RULE;
}
