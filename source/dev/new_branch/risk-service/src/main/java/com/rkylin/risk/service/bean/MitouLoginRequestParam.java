package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by luke on 2016/11/3.
 */
@Setter
@Getter
public class MitouLoginRequestParam {
  private String token;
  private String back_url;
  private String only_phone;
}
