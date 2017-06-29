package com.rkylin.risk.service.utils;

import java.lang.reflect.Field;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring/spring-service-service-test.xml"})
public class RopUtilTest {

  @Resource
  private ROPUtil commonROP;

  @Resource
  private ROPUtil fileROP;

  @Resource
  private ROPUtil accountROP;
  @Test
  public void testRopUtil(){
    assertThat(commonROP).isNotNull();
    assertThat(fileROP).isNotNull();
    assertThat(accountROP).isNotNull();
    validateFiled(commonROP);
    validateFiled(fileROP);
    validateFiled(accountROP);
  }

  private void validateFiled(ROPUtil ropUtil){
    String[] fields={"appKey","appSecret","ropUrl","mapper"};
    try {
      for (String fieldName:fields){
        Field field = ropUtil.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object obj = field.get(ropUtil);
        System.out.println(fieldName+"=="+obj);
        assertThat(obj).isNotNull();
      }

    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}
