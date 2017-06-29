package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Case;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by 201507270241 on 2015/9/23.
 */
public interface CaseService {
    List<Case> queryAll(Case cases);

    Case queryOne(Integer id);

    void insert(Case cases);

    List<Case> exportCaseExcel(Case cases, DateTime beginTime, DateTime endTime);
}
