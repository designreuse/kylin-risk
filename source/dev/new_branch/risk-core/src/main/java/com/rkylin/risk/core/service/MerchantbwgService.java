package com.rkylin.risk.core.service;


import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.MerchantbwgList;

import java.util.List;


/**
 * Created by 201508031790 on 2015/9/14.
 */
public interface MerchantbwgService {

    List<MerchantbwgList> queryAll();

    MerchantbwgList queryById(Integer id);

    String update(String ids, String opertype, String reason, Authorization auth);

    MerchantbwgList addMercbwg(MerchantbwgList merbwg);

    void delMerbwg(String ids, Authorization auth, String reason);

    void addBWGFromMerchant(String ids, MerchantbwgList mer, Authorization auth);

    String verifybwg(String ids, MerchantbwgList bwg, Authorization auth);
}
