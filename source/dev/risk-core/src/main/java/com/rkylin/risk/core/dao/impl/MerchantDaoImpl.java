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
        return super.selectList("queryAll", merchant);
    }

    @Override
    public List<Merchant> queryAllMerchant() {
        return super.selectList("queryAllMerchant");
    }

    @Override
    public Merchant queryOne(String ids) {
        return super.selectOne(ids);
    }

    @Override
    public void addMerchantBatch(List<Merchant> merchantList) {
        super.addBatch("addMerchantBatch", merchantList);
    }

    @Override
    public Merchant queryByMerchantid(String merchantid) {
        List<Merchant> merchantList = super.selectList("queryByMerchantid", merchantid);
        if (merchantList.isEmpty()) {
            return null;
        } else {
            return merchantList.get(0);
        }
    }

    @Override
    public Integer addMerchant(Merchant merchant) {
        return super.add(merchant);
    }

    @Override
    public Merchant queryByCheckorderid(String checkorderid) {
        List<Merchant> list = selectList("queryByCheckorderid", checkorderid);
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
