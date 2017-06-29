package com.rkylin.risk.spring.web;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;

/**
 * Created by tomalloc on 16-8-15.
 */
public class CleanupContextListener extends ContextLoaderListener {


  @Override public void contextDestroyed(ServletContextEvent event) {
    super.contextDestroyed(event);
    Enumeration<Driver> drivers = DriverManager.getDrivers();

    Driver driver = null;

    // clear drivers
    while (drivers.hasMoreElements()) {
      try {
        driver = drivers.nextElement();
        DriverManager.deregisterDriver(driver);
      } catch (SQLException ex) {
      }
    }

    // MySQL driver leaves around a thread. This static method cleans it up.
    try {
      Class<?> threadClazz = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
      Method method = threadClazz.getMethod("shutdown");
      method.invoke(null);
    }catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
