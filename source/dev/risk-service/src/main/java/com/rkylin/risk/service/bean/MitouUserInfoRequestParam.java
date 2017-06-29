package com.rkylin.risk.service.bean;

import com.rkylin.risk.service.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by luke on 2016/11/3.
 */
@Setter
@Getter
public class MitouUserInfoRequestParam implements EncryptParam {
  private int platuserid;
}
