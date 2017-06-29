package com.rkylin.risk.service.json.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.rkylin.risk.core.entity.Order;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * @author ChenFumin
 * @versioin 2016/12/16
 */
@Slf4j
public class OrderMessageConverter implements MessageConverter {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override public Message toMessage(Object object, Session session)
      throws JMSException, MessageConversionException {
    return null;
  }

  @Override
  public Object fromMessage(Message message)
      throws JMSException, MessageConversionException {
    // 传过来的是json串，直接转为文本消息
    TextMessage textMessage = (TextMessage) message;
    log.info("【风控系统】-----接受的json字符串是: {}---------", textMessage.getText());
    String content = null;
    List<Order> list = null;
    try {
      content = textMessage.getText();
      if (StringUtils.isNotBlank(content)) {
        JsonNode node = objectMapper.readTree(content);
        String type = node.get("type").asText();
        if ("firstCheck".equals(type)) {
          log.info("【风控系统】--------初审订单更改状态--------");
        } else if ("finalCheck".equals(type)) {
          log.info("【风控系统】--------终审订单更改状态--------");
        } else if ("agreeConfirm".equals(type)) {
          log.info("【风控系统】--------协议审状态更改--------");
        } else if ("loanCheck".equals(type)) {
          log.info("【风控系统】--------放款状态更改----------");
        }
        list = handleJsonNode(node);
      } else {
        log.info("【风控系统】---------- 传输的json没有内容 ----------");
      }
    } catch (IOException e) {
      log.info("【风控系统】解析订单Json出现问题, 错误细信息:{}", Throwables.getStackTraceAsString(e));
    }
    return list;
  }

  private List<Order> handleJsonNode(JsonNode node) throws IOException {
    log.info("【风控系统】---------开始解析node节点-----------");
    JsonNode orderContent = node.get("content");
    List<Order> list = Lists.newArrayList();
    Order order = null;
    if (orderContent.isArray()) {
      List<Map<String, String>> orders = objectMapper.readValue(orderContent.toString(), List.class);
      log.info("【风控系统】需要更新的订单数量:{}", orders.size());
      if (CollectionUtils.isNotEmpty(orders)) {
        for (Map<String, String> map : orders) {
          order = new Order();
          order.setOrderid(map.get("orderId"));
          order.setStatusid(map.get("status"));
          list.add(order);
        }
      }
      log.info("【风控系统】---------node节点解析完成-----------");
    } else {
      log.info("【风控系统】---------- 传过来的json信息格式不正确 ----------");
    }
    return list;
  }
}
