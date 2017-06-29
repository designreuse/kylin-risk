package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.AbnorIndustry;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/28.
 */
public interface AbnorIndustryService {

    AbnorIndustry queryOne(Integer id);

    List<AbnorIndustry> queryAll(AbnorIndustry abnorIndustry);

    void insert(List<AbnorIndustry> abnorIndustryList);

    void delete(Integer id);

    void update(AbnorIndustry abnorIndustry);


}
