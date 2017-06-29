package com.rkylin.risk.core.exception.model;

/**
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class ReturnCodeMetadata {

  /**
   * 执行成功
   */
  public static final ErrorDesc SUCCESS = new ErrorDesc(200, "");

  /**
   * 必须参数没有指定,请求有误
   */
  public static final ErrorDesc PARAMETER_MISSING = new ErrorDesc(400, "参数缺失");

  /**
   * 按照传入参数查询的数据数据在数据库不存在,请求被服务器拒绝
   */
  public static final ErrorDesc ILLEGAL_RESOURCE = new ErrorDesc(403, "非法访问");

  /**
   * api不存在
   */
  public static final ErrorDesc OBJECT_IS_NULL = new ErrorDesc(404, "数据不存在");

  /**
   * 请求的数据与服务器的冲突
   */
  public static final ErrorDesc DATA_CONFLICT = new ErrorDesc(409, "请求资源冲突");

  /**
   * 指定了不存在的参数,客户端主体部分比服务器能够处理的大
   */
  public static final ErrorDesc ILLEGAL_ARGUMENT = new ErrorDesc(413, "无效的参数");

  public static final ErrorDesc ILLEGAL_RESOURCE_TYPE = new ErrorDesc(415, "数据格式有误");

  /**
   * 值的格式错误，值不能满足验证条件，请求的资源无法满足服务器段的需要
   */
  public static final ErrorDesc INVALID_VALUE = new ErrorDesc(416, "参数类型错误");

  public static final ErrorDesc REQUEST_REPEAT = new ErrorDesc(499, "重复的请求");

  /**
   * 系统未处理的其他异常
   */
  public static final ErrorDesc SYSTEM_ERROR = new ErrorDesc(500, "系统错误");
}
