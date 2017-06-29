package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.PagingBiz;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/29 version: 1.0
 */
@org.springframework.stereotype.Controller
public class PageRestAction {
  @Resource
  private PagingBiz pagingBiz;

  private String sqlId;

  private String requestUrl;



  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public List<String> queryDubioustxn() {
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");
    return list;
  }
}
