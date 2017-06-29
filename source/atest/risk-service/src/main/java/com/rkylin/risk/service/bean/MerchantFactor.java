package com.rkylin.risk.service.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;

/**
 * Created by lina on 2016-8-15.
 */
@Getter
@Setter
@ToString
public class MerchantFactor {
  /*
  申请人id
   */
  private String userid;

  /*
  机构号
   */
  private String constid;

  /*
  企业名称
   */
  @NotBlank(message = "企业名称不能为空")
  private String companyname;

  /*
  企业登记机关号
   */
  private String registrationorga;

  /*
  营业执照号
   */
  @NotBlank(message = "营业执照号不能为空")
  private String businesslicense;

  /*
  营业执照生效日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate certificatestartdate;

  /*
  营业执照失效日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate certificateexpiredate;

  /*
  企业类型
   */
  private String companytype;

  /*
  注册资本
   */
  private String registfinance;

  /*
  企业地址
   */
  private String address;

  /*
  税务登记证号
   */
  private String taxregcard;

  /*
    组织机构代码证号
     */
  private String organcertificate;

  /*
    开户许可证
     */
  private String acuntopenlince;

  /*
    法人姓名
     */
  @NotBlank(message = "法人名称不能为空")
  private String corporatename;

  /*
    法人证件类型
     */
  private String certificatetype;

  /*
    法人证件号码
     */
  @NotBlank(message = "法人证件号码不能为空")
  private String certificatenumber;

  /*
    遍布省份数量
     */
  @NotBlank(message = "遍布省份数量不能为空")
  private String provincenum;

  /*
    分支机构数量
     */
  @NotBlank(message = "分支机构数量不能为空")
  private String subagencynum;

  /*
    年培训人数
     */
  @NotBlank(message = "年培训人数不能为空")
  private String trainingnumyear;

  /*
    年培训收入
     */
  @NotBlank(message = "年培训收入不能为空")
  private String trainingincomeyear;

  /*
    成立年限
     */
  @NotBlank(message = "成立年限不能为空")
  private String foundage;

  /*
    教学总面积
     */
  @NotBlank(message = "教学总面积不能为空")
  private String areateaching;

  /*
    分支机构标志
     */
  @NotBlank(message = "分支机构标志不能为空")
  private String branchflag;

  /*
    课程均价
     */
  @NotBlank(message = "课程均价不能为空")
  private String classprice;

  /*
    流水号
     */
  @NotBlank(message = "流水号不能为空")
  private String checkorderid;

  /*
    签名
     */
  private String hmac;

  /*
    课程列表
   */
  private String classlist;
}
