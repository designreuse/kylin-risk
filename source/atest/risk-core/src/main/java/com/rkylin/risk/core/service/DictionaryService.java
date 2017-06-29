package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.DictionaryCode;

import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/14 0014.
 */
public interface DictionaryService {
  DictionaryCode insert(DictionaryCode dictionaryCode);

  DictionaryCode queryById(String id);

  List<DictionaryCode> queryByDictCode(String dictcode);

  DictionaryCode update(DictionaryCode dictionaryCode, String ids);

  List<DictionaryCode> queryAllDict(DictionaryCode dictionaryCode);

  /**
   * 根据一级字典的dictcode和二级字典的code查询
   */
  List<DictionaryCode> queryConnByDicAndCode(String dictcode, String[] codes);

  Boolean deleteDic(String ids);

  DictionaryCode queryByDictAndCode(String dictCode, String code);

  Map<String, String> queryCodeAndName(String dictcode);

  /**
   * 根据一级字典编号查询二级字典
   */
  List<DictionaryCode> queryConnDicByCode(String code);

  DictionaryCode queryByCode(String code);

  DictionaryCode queryByDictAndName(String dictCode, String name);
}
