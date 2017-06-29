package com.rkylin.risk.spring.web;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.ContextLoaderListener;

/**
 * Created by tomalloc on 16-8-15.
 */
@Slf4j
public class CleanupContextListener extends ContextLoaderListener {

  @Override public void contextDestroyed(ServletContextEvent event) {
    super.contextDestroyed(event);
    // MySQL driver leaves around a thread. This static method cleans it up.
    try {
      Class<?> threadClazz = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
      Method method = threadClazz.getMethod("shutdown");
      method.invoke(null);
    } catch (final Exception e) {
      log.error("Error calling MySQL AbandonedConnectionCleanupThread shutdown {}", e);
    }

    ClassLoader cl = Thread.currentThread().getContextClassLoader();

    Enumeration<Driver> drivers = DriverManager.getDrivers();

    // clear drivers
    while (drivers.hasMoreElements()) {
      Driver driver = drivers.nextElement();
      if (driver.getClass().getClassLoader() == cl) {

        try {
          log.info("Deregistering JDBC driver {}", driver);
          DriverManager.deregisterDriver(driver);
        } catch (SQLException ex) {
          log.error("Error deregistering JDBC driver {}", driver, ex);
        }
      } else {
        log.trace(
            "Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader",
            driver);
      }
    }
  }
}
