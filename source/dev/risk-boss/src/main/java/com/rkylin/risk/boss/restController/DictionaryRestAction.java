package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.service.DictionaryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by v-wangwei on 2015/9/14 0014.
 */
@RestController
@RequestMapping("/api/1/dictionary")
public class DictionaryRestAction {




  @Resource
  DictionaryService dictionaryService;

  @RequestMapping("addDictionary")

  public DictionaryCode addDictionary(@ModelAttribute DictionaryCode dictionaryCode) {
    return dictionaryService.insert(dictionaryCode);
  }

  @RequestMapping("dictionaryModify")
  public DictionaryCode dictionaryModify(@ModelAttribute DictionaryCode dictionaryCode,
      String ids) {

    return dictionaryService.update(dictionaryCode, ids);
  }

  @RequestMapping("deleteDic")
  public void deleteDic(String deleteIds) {
    dictionaryService.deleteDic(deleteIds);
  }

  @RequestMapping("queryProducts")
  public Map<String, String> queryProducts() {
    List<DictionaryCode> channels = dictionaryService.queryByDictCode("channel");
    Map<String, String> mapResult = new HashMap<>();
    for (DictionaryCode channel : channels) {
      Map<String, String> map = dictionaryService.queryCodeAndName(channel.getCode());
      if (!map.isEmpty()) {
        mapResult.putAll(map);
      }
    }
    return mapResult;
  }

  @RequestMapping("queryProduct")
  public Map<String, String> queryProduct(@RequestParam String channelcode) {
      Map<String, String> map = dictionaryService.queryCodeAndName(channelcode);
      if (!map.isEmpty())
        return map;
      return null;
  }
}
