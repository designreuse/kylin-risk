package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CaseDao;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.service.CaseService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/23.
 */
@Service
public class CaseServiceImpl implements CaseService {
    @Resource
    private CaseDao caseDao;

    @Override
    public List<Case> queryAll(Case cases) {
        return caseDao.queryAll(cases);
    }

    @Override
    public Case queryOne(Integer id) {
        return caseDao.queryOne(id);
    }

    @Override
    public void insert(Case cases) {
        caseDao.insert(cases);
    }

    @Override
    public List<Case> exportCaseExcel(Case cases, DateTime beginTime, DateTime endTime) {
        Map map = new HashMap();
        map.put("cases", cases);
        map.put("createtimebeg", beginTime);
        map.put("createtimeend", endTime);
        return caseDao.exportCaseExcel(map);
    }
}
