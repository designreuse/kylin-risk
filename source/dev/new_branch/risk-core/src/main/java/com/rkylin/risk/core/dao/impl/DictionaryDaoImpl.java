package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.DictionaryDao;
import com.rkylin.risk.core.entity.DictionaryCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

/**
 * Created by v-wangwei on 2015/9/14 0014.
 */
@Repository("dictionaryDao")
public class DictionaryDaoImpl extends BaseDaoImpl<DictionaryCode> implements DictionaryDao {
  @Override
  public DictionaryCode insert(DictionaryCode dictionaryCode) {
    super.add(dictionaryCode);
    return dictionaryCode;
  }

  @Override
  public DictionaryCode update(DictionaryCode dictionaryCode) {
    super.modify(dictionaryCode);
    return dictionaryCode;
  }

  @Override
  public List<DictionaryCode> queryAll(DictionaryCode dictionaryCode) {
    return super.selectList("queryAll", dictionaryCode);
  }

  @Override
  public Integer deleteDic(Integer id) {
    return super.del(id);
  }

  @Override
  public DictionaryCode queryById(Integer id) {
    return super.get(id);
  }

  @Override
  public List<DictionaryCode> queryByDictCode(String dictcode) {
    return super.selectList("queryByDictCode", dictcode);
  }

  @Override
  public DictionaryCode queryByCode(String code) {
    List<DictionaryCode> dictionaryCodeList = super.selectList("queryByCode", code);
    if (!dictionaryCodeList.isEmpty()) {
      return dictionaryCodeList.get(0);
    } else {
      return null;
    }
  }

  @Override
  public Map<String, String> queryCodeAndName(String dictcode) {
    final Map<String, String> map = new HashMap<>();
    super.select("queryCodeAndName", new ResultHandler() {
      @Override public void handleResult(ResultContext resultContext) {
        Object obj = resultContext.getResultObject();
        if (obj != null && Map.class.isAssignableFrom(obj.getClass())) {
          Map<String, String> rowMap = (Map<String, String>) obj;
          String key = rowMap.get("code");
          String value = rowMap.get("name");
          map.put(key, value);
        }
      }
    }, dictcode);
    return map;
  }

  @Override
  public List<DictionaryCode> queryConnByDicAndCode(String dictcode, String[] codes) {
    Map map = new HashMap();
    map.put("dictcode", dictcode);
    map.put("codes", codes);
    return super.selectList("queryConnByDicAndCode", map);
  }

  @Override
  public DictionaryCode queryByDictAndCode(String dictCode, String code) {
    Map map = new HashMap();
    map.put("dictcode", dictCode);
    map.put("code", code);
    return super.selectOne("queryByDictAndCode", map);
  }

  @Override
  public DictionaryCode queryByDictAndName(String dictCode, String name) {
    Map map = new HashMap();
    map.put("dictcode", dictCode);
    map.put("name", name);
    return super.selectOne("queryByDictAndName", map);
  }

  @Override
  public List<DictionaryCode> queryConnDicByCode(String code) {
    return super.selectList("queryConnDicByCode", code);
  }
}
