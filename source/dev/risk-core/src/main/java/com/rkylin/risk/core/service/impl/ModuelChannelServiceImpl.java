package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.ModuelChannelDao;
import com.rkylin.risk.core.entity.ModuelsChannel;
import com.rkylin.risk.core.service.ModuelsChannelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508240185 on 2015/10/12.
 */
@Service
public class ModuelChannelServiceImpl implements ModuelsChannelService {
    @Resource
    private ModuelChannelDao moduelChannelDao;

    @Override
    public void deleteByModule(String moduletype) {
        moduelChannelDao.deleteByModule(moduletype);
    }

    @Override
    public List<ModuelsChannel> queryByModuel(String moduelType) {
        return moduelChannelDao.queryByModuel(moduelType);
    }

    @Override
    public void insert(ModuelsChannel moduelsChannel) {
         moduelChannelDao.insert(moduelsChannel);
    }

    @Override
    public ModuelsChannel update(ModuelsChannel moduelsChannel) {
        return moduelChannelDao.update(moduelsChannel);
    }
}
