package com.rkylin.risk.core.utils;

import com.rkylin.risk.core.dto.MailBean;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/23 version: 1.0
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/risk-spring-test/spring-mail-test.xml")
public class MailUtilTest {
  @Resource
  private MailUtil mailUtil;

  @Test
  public void getMessageTest() {
    String template = "mail.ftl";
    Map data = Maps.newConcurrentMap();
    String imageUrl = "http://mirror.bit.edu.cn/web/logos/debian.png";
    data.put("image", imageUrl);
    MailBean mailBean = MailBean.builder().template(template).data(data).build();
    String text = mailUtil.getMessage(mailBean);
    assertThat(text).contains(imageUrl);
  }

  @Test
  public void sendMailTest() throws MessagingException {
    Map data = Maps.newConcurrentMap();
    String imageUrl = "http://mirror.bit.edu.cn/web/logos/debian.png";
    data.put("image", imageUrl);
    String template = "mail.ftl";
    MailBean mailBean = MailBean.builder()
        .subject("hello")
        .toEmails(new String[] {""})
        .template(template)
        .data(data)
        .build();
    boolean s = mailUtil.send(mailBean);
    assertThat(s).isEqualTo(true);
  }

  @Test
  public void validateTest() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Map data = Maps.newConcurrentMap();
    data.put("age", 12);
    MailBean mailBean = MailBean.builder()
        .template("1")
        .toEmails(new String[] {"12"})
        .fromName("1")
        .subject("1")
        .data(data)
        .build();
    Set<ConstraintViolation<MailBean>> constraintViolations = validator.validate(mailBean);
    assertThat(constraintViolations.size()).isEqualTo(0);
  }
}
