package com.rkylin.risk.credit;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

/**
 * Created by tomalloc on 16-8-22.
 */
public class EventBusTest {
  @Test
  public void test() {
    EventBus eventBus = new EventBus("test");
    eventBus.register(new EventListener());
    eventBus.post(200);
  }

  private static class EventListener {
    @Subscribe
    public void listen(Integer event) {
      System.out.println("Message:" + event);
    }
  }
}
