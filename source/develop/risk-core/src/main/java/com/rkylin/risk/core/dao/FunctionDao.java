package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Functions;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/12.
 */
public interface FunctionDao {

    /**
     * 创建功能
     */
    Functions insert(Functions function);

    /**
     * 删除功能
     */
    void delete(Short id);

    /**
     * 更新功能
     */
    void update(Functions function);

    /**
     * 根据ID查找功能
     */
    Functions queryOne(Short id);

    /**
     * 查询所有功能
     */
    List<Functions> query(Functions function);

    List<Functions> queryAll();

    /**
     * 根据角色id查询功能信息
     * @param roleid
     * @return
     */
    List<Functions> queryFunByRole(Short roleid);
}
