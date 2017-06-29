package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CusFactorParamDao;
import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.service.CusFactorParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
@Service("cusFactorParamService")
public class CusFactorParamServiceImpl implements CusFactorParamService {

    @Resource
    private CusFactorParamDao cusFactorParamDao;

    @Override
    public CusFactorParam queryByCustomerid(String customerid) {
        if (isNotEmpty(customerid)) {
            return cusFactorParamDao.queryByCustomerid(customerid);
        }
        return null;
    }

    @Override
    public CusFactorParam updateByCustid(CusFactorParam cusFactorParam) {
        return cusFactorParamDao.updateByCustid(cusFactorParam);
    }

    @Override
    public CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam) {
        return cusFactorParamDao.insertCusFactorParam(cusFactorParam);
    }

    @Override
    public CusFactorParam updateBycustomerid(CusFactorParam cusFactorParam) {
        return cusFactorParamDao.updateBycustomerid(cusFactorParam);
    }

    @Override
    public CusFactorParam queryByCustomerid(String customerid, String orderTime) {
        if (isNotEmpty(customerid)) {
            return cusFactorParamDao.queryByCustomerid(customerid, orderTime);
        }
        return null;
    }

    public Integer queryHighRiskbyUserId(String customerid) {
        if (isNotEmpty(customerid)) {
            return cusFactorParamDao.queryHighRiskbyUserId(customerid);
        }
        return null;
    }

    @Override
    public CusFactorParam updateByUserIdAndTime(CusFactorParam cusFactorParam) {
        return cusFactorParamDao.updateByUserIdAndTime(cusFactorParam);
    }
}
