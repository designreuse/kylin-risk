package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.MerchantbwgList;

import java.util.List;

/**
 * Created by 201508031790 on 2015/9/14.
 */
public interface MerchantbwgDao {

    List<MerchantbwgList> queryAll();

    MerchantbwgList queryById(Integer id);

    MerchantbwgList update(MerchantbwgList mercbwg);

    MerchantbwgList addMercbwg(MerchantbwgList mercbwg);

    void delMercbwg(Integer id);

    MerchantbwgList queryByMercId(String mercid);
}
