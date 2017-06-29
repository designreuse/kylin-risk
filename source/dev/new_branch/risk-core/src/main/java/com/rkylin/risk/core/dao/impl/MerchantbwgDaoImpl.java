package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.MerchantbwgDao;
import com.rkylin.risk.core.entity.MerchantbwgList;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/14.
 */
@Repository("merchantbwgDao")
public class MerchantbwgDaoImpl extends BaseDaoImpl<MerchantbwgList> implements MerchantbwgDao {

    @Override
    public List<MerchantbwgList> queryAll() {
        return Collections.emptyList();
    }

    @Override
    public MerchantbwgList queryById(Integer id) {
        return super.get(id);
    }

    @Override
    public MerchantbwgList update(MerchantbwgList mercbwg) {
        super.modify(mercbwg);
        return mercbwg;
    }

    @Override
    public MerchantbwgList addMercbwg(MerchantbwgList mercbwg) {
        super.add(mercbwg);
        return mercbwg;
    }

    @Override
    public void delMercbwg(Integer id) {
        super.del(id);
    }

    @Override
    public MerchantbwgList queryByMercId(String mercid) {
        List<MerchantbwgList> list = super.selectList("queryMerbwgBymercId", mercid);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
