package com.rkylin.risk.boss.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/30 version: 1.0
 */
public class RiskWebsocketHandler extends TextWebSocketHandler {

  @Override
  protected void handleTextMessage(WebSocketSession session,
      TextMessage message) throws Exception {
    super.handleTextMessage(session, message);
    System.out.println(message.getPayload());
    TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
    session.sendMessage(returnMessage);
  }
}