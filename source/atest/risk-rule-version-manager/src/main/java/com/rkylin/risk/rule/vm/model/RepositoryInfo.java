package com.rkylin.risk.rule.vm.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

/**
 * Created by tomalloc on 16-5-9.
 */
@Getter
public class RepositoryInfo implements MapBean {

  private String userName;
  private String passWord;

  public RepositoryInfo(String userName, String passWord) {
    Objects.requireNonNull(userName);
    Objects.requireNonNull(passWord);
    this.userName = userName;
    this.passWord = passWord;
  }

  @Override public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("userName", userName);
    map.put("passWord", passWord);
    return map;
  }
}
