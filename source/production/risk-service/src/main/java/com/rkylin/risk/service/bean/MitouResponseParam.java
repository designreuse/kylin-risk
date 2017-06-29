package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-11-1.
 */
@Setter
@Getter
@ToString
public class MitouResponseParam<T> {
  private int flag;

  private String msg;
  private T data;
}
