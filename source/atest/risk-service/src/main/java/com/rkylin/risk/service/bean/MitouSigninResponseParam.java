package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by luke on 2016/11/3.
 */
@Setter
@Getter
@ToString(callSuper = true)
public class MitouSigninResponseParam extends MitouBaseResponseParam {
  private String tocken;
}
