package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-8-20.
 */
public class CourseServiceTest extends BaseTest {
  @Resource
  private CourseService courseService;

  @Test
  public void queryCourseNameByUserrelatedidTest() {
    List<String> names = courseService.queryCourseNameByUserrelatedid("12893");
    for (String str : names) {
      System.out.println(str);
    }
  }
}
