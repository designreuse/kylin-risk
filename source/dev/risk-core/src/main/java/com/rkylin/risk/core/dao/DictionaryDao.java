package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.DictionaryCode;

import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/14 0014.
 */
public interface DictionaryDao {
  DictionaryCode insert(DictionaryCode dictionaryCode);

  DictionaryCode update(DictionaryCode dictionaryCode);

  List<DictionaryCode> queryAll(DictionaryCode dictionaryCode);

  Integer deleteDic(Integer id);

  DictionaryCode queryById(Integer id);

  List<DictionaryCode> queryByDictCode(String dictCode);

  DictionaryCode queryByCode(String code);

  Map<String, String> queryCodeAndName(String dictcode);

  List<DictionaryCode> queryConnByDicAndCode(String dictcode, String[] codes);

  DictionaryCode queryByDictAndCode(String dictCode, String code);

  /**
   * 根据一级字典编号查询二级字典
   */
  List<DictionaryCode> queryConnDicByCode(String code);

  DictionaryCode queryByDictAndName(String dictCode, String name);
}
