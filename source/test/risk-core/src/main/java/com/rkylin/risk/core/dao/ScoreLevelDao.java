package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.ScoreLevel;

/**
 * Created by 201508031790 on 2015/10/29.
 */
public interface ScoreLevelDao {

  ScoreLevel getLevel(Double score);
}
