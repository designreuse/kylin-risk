package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.ScoreLevel;

/**
 * Created by 201508031790 on 2015/10/29.
 */
public interface ScoreLevelService {

  ScoreLevel getLevel(Double score);
}
