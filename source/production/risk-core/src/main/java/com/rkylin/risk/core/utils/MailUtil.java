package com.rkylin.risk.core.utils;

import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.exception.RiskException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@Slf4j
public class MailUtil implements InitializingBean, ApplicationContextAware {

    @Setter
    private JavaMailSender javaMailSender;

    @Setter
    private VelocityEngine velocityEngine;

    @Setter
    private Validator validator;

    private ApplicationContext applicationContext;

    @Value("${mail.from}")
    private String mailFrom;

    /**
     * @throws MessagingException
     */
    public boolean send(MailBean mailBean) throws MessagingException {
        mailBean.setFrom(mailFrom);
        if (StringUtils.isEmpty(mailBean.getFromName())) {
            mailBean.setFromName(mailBean.getFrom());
        }
        Set<ConstraintViolation<MailBean>> constraintViolations = validator.validate(mailBean);
        if (!constraintViolations.isEmpty()) {
            throw new RiskException(constraintViolations.iterator().next().getMessage());
        }
        MimeMessage msg = createMimeMessage(mailBean);
        if (msg == null) {
            return false;
        }

        this.sendMail(msg, mailBean);

        return true;
    }

    private void sendMail(MimeMessage msg, MailBean mailBean) {
        javaMailSender.send(msg);
        log.info("$$$ Send mail Subject:" + mailBean.getSubject()
                + ", TO:" + arrayToStr(mailBean.getToEmails()));
    }

    /*
     * 记日记用的
     */
    private String arrayToStr(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : array) {
            sb.append(str + " , ");
        }
        return sb.toString();
    }

    /*
     * 根据 mailBean 创建 MimeMessage
     */
    private MimeMessage createMimeMessage(MailBean mailBean) throws MessagingException {
        if (!checkMailBean(mailBean)) {
            return null;
        }
        String text = getMessage(mailBean);
        if (text == null) {
            log.warn("@@@ warn mail text is null (Thread name="
                    + Thread.currentThread().getName() + ") @@@ " + mailBean.getSubject());
            return null;
        }
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        //messageHelper.setFrom(mailBean.getFrom());
        try {
            messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        messageHelper.setSubject(mailBean.getSubject());
        messageHelper.setTo(mailBean.getToEmails());
        messageHelper.setText(text, true);

        return msg;
    }

    /*
     * 模板解析
     * @param mailBean
     * @return
     */
    public String getMessage(MailBean mailBean) {
        try {
            return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    mailBean.getTemplate(), "UTF-8", mailBean.getData());
        } catch (Exception e) {
            log.error("template render error", e);
        }
        return null;
    }

    /*
     * check 邮件
     */
    private boolean checkMailBean(MailBean mailBean) {
        if (mailBean == null) {
            log.warn("@@@ warn mailBean is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (mailBean.getSubject() == null) {
            log.warn("@@@ warn mailBean.getSubject() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (mailBean.getToEmails() == null) {
            log.warn("@@@ warn mailBean.getToEmails() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (mailBean.getTemplate() == null) {
            log.warn("@@@ warn mailBean.getTemplate() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (velocityEngine == null || javaMailSender == null) {
            throw new RiskException("邮件客户端初始化失败");
        }
        if (validator == null) {
            try {
                validator = applicationContext.getBean("validator", Validator.class);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }

        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

