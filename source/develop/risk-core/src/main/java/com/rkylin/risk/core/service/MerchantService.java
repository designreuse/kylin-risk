package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Merchant;

import java.util.List;

/**
 * Created by v-wangwei on 2015/9/6 0006.
 */
public interface MerchantService {
    List<Merchant> queryAll(Merchant merchant);

    List<Merchant> queryAllMerchant();

    Merchant queryOne(String ids);

    void addMerchantBatch(List<Merchant> merchantList);

    Merchant queryByMerchantid(String merchantid);

    Integer addMerchant(Merchant merchant);

    Merchant queryByCheckorderid(String checkorderid);

    void update(Merchant merchant);
}
