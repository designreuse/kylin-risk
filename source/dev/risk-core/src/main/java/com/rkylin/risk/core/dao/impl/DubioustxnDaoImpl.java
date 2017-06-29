package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.DubioustxnDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Dubioustxn;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/17 0017.
 */
@Repository
public class DubioustxnDaoImpl extends BaseDaoImpl<Dubioustxn> implements DubioustxnDao {


    @Override
    public Dubioustxn getById(Integer id) {
        return super.get(id);
    }

    @Override
    public List<Dubioustxn> queryAllDubioustxn(Dubioustxn dubioustxn) {
        return super.selectList("queryAll", dubioustxn);
    }

    @Override
    public List<Dubioustxn> queryByTxnum(String orderId) {
        return super.selectList("queryByTxnum", orderId);
    }

    @Override
    public List<Dubioustxn> queryAllDubioustxnMap(Dubioustxn dubioustxn, Authorization auth) {
        Map map = new HashMap();
        if (auth.getProducts() != null && auth.getProducts().length > 0) {
            String[] products = auth.getProducts();
            if (Arrays.asList(products).contains(Constants.ALL_PRODUCT)) {
                //产品id为-1时,默认不设productid，查所有
                map.put("productall", "");
            } else {
                map.put("products", products);
            }
        } else {
            //产品id为空，设参数为0
            map.put("productnull", "0");
        }
        map.put("warndate", dubioustxn.getWarndate());
        return super.selectList("queryAllMap", map);
    }

    @Override
    public void update(Dubioustxn dubioustxn) {
        super.modify(dubioustxn);
    }

    @Override
    public List<Dubioustxn> queryByCaseId(Integer caseid) {
        return super.selectList("queryByCaseId", caseid);
    }

    @Override
    public List<Dubioustxn> queryByRiskLevel(String riskLevel) {
        return super.selectList("queryByRiskLevel", riskLevel);
    }

    @Override
    public Dubioustxn insert(Dubioustxn dubioustxn) {
        super.add(dubioustxn);
        return dubioustxn;
    }

    @Override
    public void updateWarnstatus(String warnstatus, Integer id, String dealopinion) {
        Map map = new HashMap();
        map.put("warnstatus", warnstatus);
        map.put("id", id);
        map.put("dealopinion", dealopinion);
        super.modify("updateWarnstatus", map);
    }

    @Override
    public List exportDubiousExcel(Map map) {
        return super.selectList("queryByCondition", map);
    }
}
