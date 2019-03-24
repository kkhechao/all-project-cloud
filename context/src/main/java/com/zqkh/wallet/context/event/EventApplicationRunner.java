package com.zqkh.wallet.context.event;

import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.starter.AppService;
import com.zqkh.wallet.context.appservice.inter.AccountService;
import com.zqkh.wallet.context.appservice.inter.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2017/11/27 0027 16:54
 */
@Component
@AppService
public class EventApplicationRunner implements ApplicationRunner {


    @Autowired
    private AccountService accountService;

    @Autowired
    private BillService billService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        EventBus.registerHandler(new CreateWalletAccountEventHandler(accountService));
        EventBus.registerHandler(new CreateShopWalletAccountEventHandler(accountService));
        EventBus.registerHandler(new RewardBillEventHandler(billService));

        //创建消费类账单
        EventBus.registerHandler(new CreateConsumptionBillEventHandler(billService));
    }
}
