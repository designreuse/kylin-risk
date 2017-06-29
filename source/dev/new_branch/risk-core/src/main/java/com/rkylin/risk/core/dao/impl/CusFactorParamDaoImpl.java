package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CusFactorParamDao;
import com.rkylin.risk.core.entity.CusFactorParam;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
@Repository("cusFactorParamDao")
public class CusFactorParamDaoImpl extends BaseDaoImpl<CusFactorParam> implements
        CusFactorParamDao {
    @Override
    public CusFactorParam queryByCustomerid(String customerid) {
        //FIXME selectList 换为 selectOne
        List<CusFactorParam> list = super.selectList("queryByCustomerid", customerid);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam) {
        super.add(cusFactorParam);
        return cusFactorParam;
    }

    @Override
    public CusFactorParam update(CusFactorParam cusFactorParam) {
        super.modify(cusFactorParam);
        return cusFactorParam;
    }



    @Override
    public Integer queryHighRiskbyUserId(String customerid) {
        return (Integer) super.selectList("queryHighRiskbyUserId", customerid).get(0);
    }


}
