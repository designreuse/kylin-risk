package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.DictionaryDao;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/14 0014.
 */
@Slf4j
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
  @Resource
  DictionaryDao dictionaryDao;

  @Override
  public DictionaryCode insert(DictionaryCode dictionaryCode) {
    return dictionaryDao.insert(dictionaryCode);
  }

  @Override
  public DictionaryCode queryById(String ids) {
    Integer id = Integer.parseInt(ids);
    return dictionaryDao.queryById(id);
  }

  @Override
  public List<DictionaryCode> queryByDictCode(String dictCode) {
    return dictionaryDao.queryByDictCode(dictCode);
  }

  @Override
  public DictionaryCode update(DictionaryCode dictionaryCode, String ids) {
    return dictionaryDao.update(dictionaryCode);
  }

  @Override
  public List<DictionaryCode> queryAllDict(DictionaryCode dictionaryCode) {
    return dictionaryDao.queryAll(dictionaryCode);
  }

  @Override
  public List<DictionaryCode> queryConnByDicAndCode(String dictcode, String[] codes) {
    return dictionaryDao.queryConnByDicAndCode(dictcode, codes);
  }

  @Override
  public Boolean deleteDic(String ids) {
    String[] idsarray = ids.split(",");
    try {
      for (int i = 0; i < idsarray.length; i++) {
        //DictionaryCode dictionaryCode = dictionaryDao.queryById(Integer.valueOf(idsarray[i]));
        dictionaryDao.deleteDic(Integer.valueOf(idsarray[i]));
      }
      return true;
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return false;
    }
  }

  @Override public DictionaryCode queryByDictAndCode(String dictCode, String code) {
    return dictionaryDao.queryByDictAndCode(dictCode, code);
  }

  @Override public DictionaryCode queryByDictAndName(String dictCode, String name) {
    return dictionaryDao.queryByDictAndName(dictCode, name);
  }

  @Override public Map<String, String> queryCodeAndName(String dictcode) {
    return dictionaryDao.queryCodeAndName(dictcode);
  }

  @Override public List<DictionaryCode> queryConnDicByCode(String code) {
    return dictionaryDao.queryConnDicByCode(code);
  }

  @Override
  public DictionaryCode queryByCode(String code) {
    return dictionaryDao.queryByCode(code);
  }
}
