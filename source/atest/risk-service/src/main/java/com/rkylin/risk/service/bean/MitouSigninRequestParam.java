package com.rkylin.risk.service.bean;

import com.rkylin.risk.service.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-11-1.
 */
@Setter
@Getter
public class MitouSigninRequestParam implements EncryptParam {
  private String platuserid;
  private String mobile;
}
