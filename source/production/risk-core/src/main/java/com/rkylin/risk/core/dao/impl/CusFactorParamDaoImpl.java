package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CusFactorParamDao;
import com.rkylin.risk.core.entity.CusFactorParam;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
@Repository("cusFactorParamDao")
public class CusFactorParamDaoImpl extends BaseDaoImpl<CusFactorParam> implements
        CusFactorParamDao {
    @Override
    public CusFactorParam queryByCustomerid(String customerid) {
        List<CusFactorParam> list = super.query("queryByCustomerid", customerid);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public CusFactorParam updateByCustid(CusFactorParam cusFactorParam) {
        super.modify("updateByCustid", cusFactorParam);
        return cusFactorParam;
    }

    @Override
    public CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam) {
        super.add(cusFactorParam);
        return cusFactorParam;
    }

    @Override
    public CusFactorParam updateBycustomerid(CusFactorParam cusFactorParam) {
        super.modify("updateBycustomerid", cusFactorParam);
        return cusFactorParam;
    }

    @Override
    public CusFactorParam queryByCustomerid(String customerid, String orderTime) {
        Map map = new HashMap();
        map.put("customerid", customerid);
        map.put("orderTime", orderTime);
        List<CusFactorParam> list = super.query("queryByUseridAndDate", map);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer queryHighRiskbyUserId(String customerid) {
        return (Integer) super.queryList("queryHighRiskbyUserId", customerid).get(0);
    }

    @Override
    public CusFactorParam updateByUserIdAndTime(CusFactorParam cusFactorParam) {
        super.modify("updateByUserIdAndTime", cusFactorParam);
        return cusFactorParam;
    }
}
