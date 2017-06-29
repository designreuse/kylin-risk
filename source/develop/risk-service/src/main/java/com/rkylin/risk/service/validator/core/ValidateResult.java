package com.rkylin.risk.service.validator.core;

import com.rkylin.risk.service.validator.builder.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-4-14.
 */
@Getter
@Setter
public class ValidateResult<T extends Builder> {
  private boolean pass;
  private T builder;
}
