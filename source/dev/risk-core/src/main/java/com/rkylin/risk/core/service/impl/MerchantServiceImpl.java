package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.MerchantDao;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.service.MerchantService;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * Created by v-wangwei on 2015/9/8 0008.
 */
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {
    @Resource
    private MerchantDao merchantDao;

    @Override
    public List<Merchant> queryAll(Merchant merchant) {
        return merchantDao.queryAll(merchant);
    }

    @Override
    //排除Merchantid 为''的情况
    public List<Merchant> queryAllMerchant() {
        return merchantDao.queryAllMerchant();
    }

    @Override
    public Merchant queryOne(String ids) {
        return merchantDao.queryOne(ids);
    }

    @Override
    public void addMerchantBatch(List<Merchant> merchantList) {
        merchantDao.addMerchantBatch(merchantList);
    }

    @Override
    public Merchant queryByMerchantid(String merchantid) {
        return merchantDao.queryByMerchantid(merchantid);
    }

    @Override
    public Integer addMerchant(Merchant merchant) {
        return merchantDao.addMerchant(merchant);
    }

    @Override
    public Merchant queryByCheckorderid(String checkorderid) {
        return merchantDao.queryByCheckorderid(checkorderid);
    }

    @Override
    public void update(Merchant merchant) {
        merchantDao.update(merchant);
    }
}
