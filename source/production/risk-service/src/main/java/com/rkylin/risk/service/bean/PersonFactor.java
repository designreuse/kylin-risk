package com.rkylin.risk.service.bean;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 个人信息同步 Created by cuixiaofang on 2016-7-28.
 */
@Getter
@Setter
@ToString
public class PersonFactor implements Serializable {
  /**
   * 申请人id
   */
  private String userid;
  /**
   * 培训机构ID
   */
  private String userrelatedid;
  /**
   * 机构号
   */
  private String constid;
  /**
   * 姓名
   */
  private String username;
  /**
   * 身份证号码
   */
  private String certificatenumber;
  /**
   * 身份证生效日期
   */
  private String certificatestartdate;
  /**
   * 身份证失效日期
   */
  private String certificateexpiredate;
  /**
   * 银行卡卡号
   */
  private String tcaccount;
  /**
   * 手机号码
   */
  private String mobilephone;
  /**
   * 身份证是否验证
   */
  private String isauthor;
  /**
   * 职业
   */
  private String occupation;
  /**
   * 年龄
   */
  private String age;
  /**
   * 学历
   */
  private String education;
  /**
   * 课程名称
   */
  private String classname;
  /**
   * 课程单价
   */
  private String classprice;
  /**
   * 课程Id
   */
  private String courseId;
  /**
   * 学校Id
   */
  private String corporationId;
  /**
   * 学校名称
   */
  private String corporationname;
  /**
   * 课程一级分类
   */
  private String couserStairClassify;
  /**
   * 课程二级分类
   */
  private String courseSecondaryClassify;
  /**
   * 工作流ID
   */
  private String checkorderid;
  /**
   * 签名
   */
  private String hmac;
  /**
   * 文件urlkey
   */
  private String urlkey;

   //第一联系人姓名  悦视觉时传配偶姓名（接米投时必填）
  private String firstman;
  /* * 第一联系人手机号*/
  private String firstmobile;
  //第一联系人身份证号  悦视觉时传配偶身份证号
  private String firstid;
  //第一联系人关系
  private String firstrelation;
  /* * 第二联系人姓名*/
  private String secondman;
  //第二联系人身份证号码
  private String secondid;
  // 第二联系人手机号
  private String secondmobile;
  //第二联系人关系
  private String secondrelation;

  //大行名称
  private String bankname;

  //银行编码
  private String bankcode;

  //支行名称
  private String bankbranch;

  //民族
  private String nation;
  // 地址
  private String address;

  //信用卡
  private String creditcard;

  //qq号
  private String qqnum;
  //邮箱
  private String email;
  //京东账号
  private String jdnum;
  //支付宝账号
  private String alipaynum;
  //银行卡开户行名称
  private String taccountbank;
  //银行卡开户省
  private String taccountprovince;
  //银行卡开户市
  private String taccountcity;
  //银行卡开户支行
  private String taccountbranch;

  //是否是学生 1-在校学生 0-非在校学生，推送米投必填
  private String isstudent;
  //学校名称  &corporationname
  private String universityName;
  //校区
  private String schoolArea;
  //入学年份
  private String enrolDate;
  //毕业学校
  private String finishSchool;
  //学校所在省 &杏仁
  private String province;
  //学校所在市 &杏仁
  private String city;


  // 工作单位名称
  private String workCompanyName;
  //工作单位性质
  private String workCompanyNature;
  //工作单位地址
  private String workCompanyAddress;
  //工作职称
  private String workTitle;
  //月收入  &杏仁
  private String salary;
  //工作单位详细地址 &杏仁
  private String unitFullAddress;
  //现居住地 &杏仁
  private String currentAddress;
  //现居住地详细地址 &杏仁
  private String currentFullAddress;
  //客户信息验证方式 &杏仁
  private String checkMethod;


  //logo照片 &杏仁
  private String logourlkey;
  //合同照片 &杏仁
  private String contracturlkey;
  //配偶性别 &杏仁
  private String matesex;
  //配偶民族 &杏仁
  private String matenation;
  //配偶地址  &杏仁
  private String mateaddress;
  //配偶身份证生效日期  &杏仁
  private String matestartDate;
  //配偶身份证失效日期  &杏仁
  private String mateendDate;
  //身份证正反面urlkey  &杏仁
  private String mateurlkey;
  //照相类型  &杏仁
  private String photographyType;
  //孩子姓名  &杏仁
  private String childName;
  //孩子证件号  &杏仁
  private String childCardId;

  //通讯录
  private String mobilelist;

  private String businessType;
}
