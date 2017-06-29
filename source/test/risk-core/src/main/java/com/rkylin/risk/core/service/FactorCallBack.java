package com.rkylin.risk.core.service;

import com.rkylin.risk.commons.entity.Amount;
import java.util.List;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/24
 * version: 1.0
 */
public interface FactorCallBack {
    List<String> doGetFactor();
    Amount defaultScore();
    String getOffmsg();
    boolean getIsoff();

}
