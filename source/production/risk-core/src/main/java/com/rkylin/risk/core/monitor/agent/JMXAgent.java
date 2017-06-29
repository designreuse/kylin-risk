package com.rkylin.risk.core.monitor.agent;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

/**
 * JMX监控
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-3-12 下午4:31 version: 1.0
 */
@Slf4j
public class JMXAgent implements SmartLifecycle, DisposableBean {
  private MetricRegistry metricRegistry;
  private JmxReporter reporter;

  private boolean running = false;

  public JMXAgent(MetricRegistry metricRegistry) {
    this.metricRegistry = metricRegistry;
  }

  @Override
  public void destroy() throws Exception {
    stop();
  }

  @Override
  public boolean isAutoStartup() {
    return true;
  }

  @Override
  public void stop(Runnable callback) {

  }

  @Override
  public void start() {
    if (!running) {
      log.info("######################start running JMXAgent monitor#####################");
      running = true;
      reporter = JmxReporter.forRegistry(metricRegistry).build();
      reporter.start();
    }
  }

  @Override
  public void stop() {
    if (running) {
      log.info("######################stop running JMXAgent monitor#####################");
      running = false;
      if (reporter != null) {
        reporter.stop();
      }
    }
  }

  @Override
  public boolean isRunning() {
    return running;
  }

  @Override
  public int getPhase() {
    return Integer.MAX_VALUE;
  }
}
