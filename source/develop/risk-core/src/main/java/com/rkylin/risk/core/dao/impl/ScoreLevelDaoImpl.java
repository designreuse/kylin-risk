package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.ScoreLevelDao;
import com.rkylin.risk.core.entity.ScoreLevel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508031790 on 2015/10/29.
 */
@Repository
public class ScoreLevelDaoImpl extends BaseDaoImpl<ScoreLevel> implements ScoreLevelDao {

  @Override
  public ScoreLevel getLevel(Double score) {
    List<ScoreLevel> list = super.query("getlevelbyscore", score);
    if (!list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }
}
