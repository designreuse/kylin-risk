package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.ScoreLevelDao;
import com.rkylin.risk.core.entity.ScoreLevel;
import com.rkylin.risk.core.service.ScoreLevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 201508031790 on 2015/10/29.
 */
@Service
public class ScoreLevelServiceImpl implements ScoreLevelService {

    @Resource
    private ScoreLevelDao scoreLevelDao;
    @Override
    public ScoreLevel getLevel(Double score) {
        return scoreLevelDao.getLevel(score);
    }
}
