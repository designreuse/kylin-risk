package com.rkylin.risk.boss.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by cuixiaofang on 2016-8-12.
 */
@Slf4j
public class SchedulerShutDelay extends SchedulerFactoryBean {

    /**
     * 关于Quartz内存泄漏的不太美观的解决方案：
     * 在调用scheduler.shutdown(true)后增加延时，等待worker线程结束。
     */
    @Override
    public void destroy() throws SchedulerException{
        super.destroy();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
