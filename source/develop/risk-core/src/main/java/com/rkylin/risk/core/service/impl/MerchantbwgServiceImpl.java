package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.MerchantDao;
import com.rkylin.risk.core.dao.MerchantbwgDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.entity.MerchantbwgList;
import com.rkylin.risk.core.service.MerchantbwgService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/14.
 */
@Service("merchantbwgService")
public class MerchantbwgServiceImpl implements MerchantbwgService {

    @Resource
    private MerchantbwgDao merchantbwgDao;

    @Resource
    private MerchantDao merchantDao;

    @Override
    public List<MerchantbwgList> queryAll() {
        return null;
    }

    @Override
    public MerchantbwgList queryById(Integer id) {
        return merchantbwgDao.queryById(id);
    }

    @Override
    public String update(String ids, String opertype, String reason, Authorization auth) {
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            MerchantbwgList bwg = merchantbwgDao.queryById(Integer.valueOf(idArray[i]));
            if (bwg != null) {
                if ("remove".equals(opertype)) {
                    bwg.setStatus(Constants.REMOVE_UNVERIFY);
                    bwg.setReason(reason);
                    bwg.setUserid(auth.getUserId());
                    bwg.setUsername(auth.getUsername());
                    bwg.setUpdatetime(DateTime.now());
                    merchantbwgDao.update(bwg);
                } else if ("recover".equals(opertype)) {
                    bwg.setStatus(Constants.ACTIVE);
                    bwg.setReason(reason);
                    bwg.setUserid(auth.getUserId());
                    bwg.setUsername(auth.getUsername());
                    bwg.setUpdatetime(DateTime.now());
                    merchantbwgDao.update(bwg);
                } else if ("time".equals(opertype)) {
                    bwg.setEffecttime(null);
                    bwg.setFailuretime(null);
                    bwg.setUserid(auth.getUserId());
                    bwg.setUsername(auth.getUsername());
                    bwg.setUpdatetime(DateTime.now());
                    merchantbwgDao.update(bwg);
                }
            }
        }

        return ids;
    }

    @Override
    public MerchantbwgList addMercbwg(MerchantbwgList merbwg) {
        return null;
    }

    @Override
    public void delMerbwg(String ids, Authorization auth, String reason) {

        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            MerchantbwgList bwg = merchantbwgDao.queryById(Integer.valueOf(idArray[i]));
            if (bwg != null) {
                bwg.setStatus(Constants.DELETE);
                bwg.setReason(reason);
                bwg.setUserid(auth.getUserId());
                bwg.setUsername(auth.getUsername());
                bwg.setUpdatetime(DateTime.now());
                merchantbwgDao.update(bwg);
            } else {
                continue;
            }

        }

    }

    @Override
    public void addBWGFromMerchant(String ids, MerchantbwgList mer, Authorization auth) {
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            MerchantbwgList merbwg = merchantbwgDao.queryByMercId(idArray[i]);
            if (merbwg == null) {
                Merchant merchant = merchantDao.queryOne(idArray[i]);
                if (merchant != null) {
                    MerchantbwgList merbwglist = new MerchantbwgList();
                    merbwglist.setMerchantid(merchant.getMerchantid());
                    merbwglist.setMerchid(merchant.getId());
                    merbwglist.setMerchantname(merchant.getMerchantname());
                    merbwglist.setSource(Constants.INNER);
                    if ("black".equals(mer.getType())) {
                        merbwglist.setType(Constants.BLACK);
                    } else if ("white".equals(mer.getType())) {
                        merbwglist.setType(Constants.WHITE);
                    } else if ("gray".equals(mer.getType())) {
                        merbwglist.setType(Constants.GRAY);
                    }
                    merbwglist.setEffecttime(mer.getEffecttime());
                    merbwglist.setFailuretime(mer.getFailuretime());
                    merbwglist.setStatus(Constants.ADD_UNVERIFY);
                    merbwglist.setUserid(auth.getUserId());
                    merbwglist.setUsername(auth.getUsername());
                    merbwglist.setUpdatetime(DateTime.now());
                    merbwglist.setCreatetime(DateTime.now());
                    merchantbwgDao.addMercbwg(merbwglist);
                }
            } else {
//                    throw  new CreditEaseRestDataConflictException("商户["
//                            +merbwg.getMerchantname()+"]在名单中已存在");
            }
        }
    }

    @Override
    public String verifybwg(String ids, MerchantbwgList bwglist, Authorization auth) {
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            MerchantbwgList bwg = merchantbwgDao.queryById(Integer.valueOf(idArray[i]));
            if (bwg != null) {
                bwg.setStatus(bwglist.getStatus().
                        equals(Constants.ACTIVE) ? Constants.ACTIVE : Constants.UNTHREAD);
                bwg.setReason(bwglist.getReason());
                bwg.setCheckid(auth.getUserId());
                bwg.setCheckname(auth.getUsername());
                bwg.setChecktime(DateTime.now());
                merchantbwgDao.update(bwg);
            }
        }
        return ids;
    }
}
