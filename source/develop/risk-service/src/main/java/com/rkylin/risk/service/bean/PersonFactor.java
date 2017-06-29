package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
   * 工作流ID
   */
  private String checkorderid;
  /**
   * 签名
   */
  private String hmac;
  /*  *//**
   * 文件urlkey
   *//*
    private String urlkey;
    *//**
   * 第一联系人姓名
   *//*
    private String firstman;
    *//**
   * 第一联系人手机号
   *//*
    private String firstmobile;
    *//**
   * 第二联系人姓名
   *//*
    private String secondman;
    *//**
   * 第二联系人手机号
   *//*
    private String secondmobile;
    *//**
   * 通讯录
   *//*
    private String  mobilelist;*/
}
