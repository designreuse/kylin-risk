package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.AbnormalCountrycode;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/31.
 */
public interface AbnorCountryService {
    AbnormalCountrycode queryOne(Integer id);

    List<AbnormalCountrycode> queryAll(AbnormalCountrycode abnormalCountrycode);

    void insert(List<AbnormalCountrycode> abnormalCountrycodeList);

    void delete(Integer id);

    void update(AbnormalCountrycode abnormalCountrycode);

}
