package com.rkylin.risk.core.dto;

import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 201508031790 on 2015/9/9.
 */
@Getter
@Setter
public class MailBean implements Serializable {
  private String from;
  private String fromName;
  @NotNull(message = "收件人不能为空")
  private String[] toEmails;
  @NotNull(message = "邮件标题不能为空")
  private String subject;
  private Map<String, Object> data;          //邮件数据
  @NotNull(message = "邮件模板名称不能为空")
  private String template;

  public static MailBeanBuilder builder() {
    return new MailBeanBuilder();
  }

  public static class MailBeanBuilder {
    private String fromName;
    private String[] toEmails;
    private String subject;
    private Map<String, Object> data;          //邮件数据
    private String template;

    private MailBeanBuilder() {
    }

    public MailBeanBuilder fromName(String fromName) {
      this.fromName = fromName;
      return this;
    }

    public MailBeanBuilder toEmails(String[] toEmails) {
      this.toEmails = toEmails;
      return this;
    }

    public MailBeanBuilder subject(String subject) {
      this.subject = subject;
      return this;
    }

    public MailBeanBuilder data(Map<String, Object> data) {
      this.data = data;
      return this;
    }

    public MailBeanBuilder template(String template) {
      this.template = template;
      return this;
    }

    public MailBean build() {
      MailBean mailBean = new MailBean();
      mailBean.setFromName(fromName);
      mailBean.setToEmails(toEmails);
      mailBean.setSubject(subject);
      mailBean.setData(data);
      mailBean.setTemplate(template);
      return mailBean;
    }
  }
}
