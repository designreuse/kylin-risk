package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.PersonFactor;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
public interface PersonBiz {
    /**
     * 个人客户信息同步
     * @param personFactor
     * @return
     */
    boolean personmsg(PersonFactor personFactor);
}
