package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CaseEventDao;
import com.rkylin.risk.core.entity.CaseEvent;
import com.rkylin.risk.core.service.CaseEventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lina on 2016-3-28.
 */
@Service
public class CaseEventServiceImpl implements CaseEventService {
    @Resource
    private CaseEventDao caseEventDao;

    @Override
    public CaseEvent insert(CaseEvent caseEvent) {
        return caseEventDao.insert(caseEvent);
    }
}
