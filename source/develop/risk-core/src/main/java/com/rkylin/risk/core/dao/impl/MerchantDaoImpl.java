package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.MerchantDao;
import com.rkylin.risk.core.entity.Merchant;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Created by v-wangwei on 2015/9/8 0008.
 */
@Repository("merchantDao")
public class MerchantDaoImpl extends BaseDaoImpl<Merchant> implements MerchantDao {

    @Override
    public List<Merchant> queryAll(Merchant merchant) {
        return super.query("queryAll", merchant);
    }

    @Override
    public List<Merchant> queryAllMerchant() {
        return super.query("queryAllMerchant");
    }

    @Override
    public Merchant queryOne(String ids) {
        return super.queryOne(ids);
    }

    @Override
    public void addMerchantBatch(List<Merchant> merchantList) {
        super.addBatch("addMerchantBatch", merchantList);
    }

    @Override
    public Merchant queryByMerchantid(String merchantid) {
        List<Merchant> merchantList = super.query("queryByMerchantid", merchantid);
        if (merchantList.isEmpty()) {
            return null;
        } else {
            return merchantList.get(0);
        }
    }

    @Override
    public Integer addMerchant(Merchant merchant) {
        return super.insertObject(merchant);
    }

    @Override
    public Merchant queryByCheckorderid(String checkorderid) {
        List<Merchant> list = query("queryByCheckorderid", checkorderid);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void update(Merchant merchant) {
        super.modify(merchant);
    }
}
