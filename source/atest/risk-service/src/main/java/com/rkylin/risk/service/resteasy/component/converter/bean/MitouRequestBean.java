package com.rkylin.risk.service.resteasy.component.converter.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-12-2.
 */
@Setter
@Getter
@EqualsAndHashCode(of = "message")
public class MitouRequestBean<T> {
  private T bean;
  private String message;
}
