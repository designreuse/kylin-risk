package com.rkylin.risk.service.bean;

import com.rkylin.risk.service.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuxian
 * @create 2016-11-04 14:26
 **/
@Setter
@Getter
public class MitouGetUserInfoRequestParam implements EncryptParam {
  private String platuserid;
  private String orderid;
  private String constid;
  private Integer dataSource;
}
