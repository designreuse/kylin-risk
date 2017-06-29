package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-11-1.
 */
@Setter
@Getter
@ToString(callSuper=true)
public class MitouSignupResponseParam extends MitouBaseResponseParam {
  private String id;
  private String mobile;
}
