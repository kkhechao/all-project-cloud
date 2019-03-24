package com.zqkh.wallet.context.configuration;


import com.zqkh.wallet.context.appservice.inter.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by wenjie on 2016/4/15.
 * Quartz Config
 */
@Component
@Configurable
@EnableScheduling
@Slf4j
public class QuartzConfiguration {

    @Autowired
    private AccountService accountService;

    /**
     * 每天23:59:59 计算前一天总金额
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void closeOrder() {
        log.debug("执行计划任务【每天23:59:59 计算前一天总金额】");
        accountService.saveTodayMoneyTotal();
    }
}