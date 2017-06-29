package com.rkylin.risk.core.service;

import java.util.List;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1
 * version: 1.0
 */
public interface PagingService {
    List query(String sqlId, Map params);

    List queryPage(String sqlId, int offset, int limit, Map params);
}
