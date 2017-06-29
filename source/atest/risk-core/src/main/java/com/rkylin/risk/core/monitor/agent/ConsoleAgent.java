package com.rkylin.risk.core.monitor.agent;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.SmartLifecycle;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

/**
 * 将监控信息输出到控制台
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-3-12 下午2:29 version: 1.0
 */
@Slf4j
public class ConsoleAgent implements SmartLifecycle {

  private MetricRegistry metricRegistry;

  private int period;

  private boolean running = false;

  private ConsoleReporter reporter;

  public ConsoleAgent(MetricRegistry metricRegistry) {
    this(metricRegistry, 1);
  }

  public ConsoleAgent(MetricRegistry metricRegistry, int period) {
    this.metricRegistry = metricRegistry;
    this.period = period;
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
      log.info("######################start running ConsoleAgent monitor#####################");
      running = true;
      reporter = ConsoleReporter.forRegistry(metricRegistry)
          .convertRatesTo(TimeUnit.SECONDS)
          .convertDurationsTo(TimeUnit.MILLISECONDS)
          .build();
      reporter.start(period, TimeUnit.SECONDS);
    }
  }

  @Override
  public void stop() {
    if (running) {
      log.info("######################stop running ConsoleAgent monitor#####################");
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
