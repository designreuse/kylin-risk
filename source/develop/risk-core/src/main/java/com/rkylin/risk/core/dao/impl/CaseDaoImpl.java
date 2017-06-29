package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CaseDao;
import com.rkylin.risk.core.entity.Case;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/23.
 */
@Repository
public class CaseDaoImpl extends BaseDaoImpl<Case> implements CaseDao {
    @Override
    public List<Case> queryAll(Case cases) {
        return super.query("queryAll", cases);
    }

    @Override
    public Case queryOne(Integer id) {
        return super.queryOne(id);
    }

    @Override
    public void insert(Case cases) {
        super.add(cases);
    }

    @Override
    public List<Case> exportCaseExcel(Map map) {
        return super.query("exportCaseExcel", map);
    }
}
