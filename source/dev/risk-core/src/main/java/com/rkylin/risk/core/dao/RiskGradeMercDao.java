package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.RiskGradeMerc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201508240185 on 2015/9/16.
 */
public interface RiskGradeMercDao {

  List<HashMap> queryPayGradeMerc(Map<String, Object> map);

  RiskGradeMerc queryById(Integer id);

  RiskGradeMerc update(RiskGradeMerc grade);
}
