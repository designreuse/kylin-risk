package com.rkylin.risk.service.validator;

import com.rkylin.risk.service.validator.core.ValidateResult;
import java.io.Serializable;

/**
 * Created by tomalloc on 16-4-5.
 */
public interface ServiceValidator<T extends Serializable> {

  ValidateResult validate(T simpleOrder, String hmac);
}
