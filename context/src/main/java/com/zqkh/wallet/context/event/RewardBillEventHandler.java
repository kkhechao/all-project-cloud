package com.zqkh.wallet.context.event;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.zqkh.wallet.context.appservice.inter.BillService;
import com.zqkh.wallet.event.dto.RewardBillEventDto;

/**
 * @author hty
 * @create 2018-01-19 9:28
 **/

public class RewardBillEventHandler implements EventHandler<RewardBillEventDto> {

    private BillService billService;

    public RewardBillEventHandler(BillService billService){
        this.billService = billService;
    }

    @Override
    public String getEventName() {
        return RewardBillEventDto.EVENT_NAME;
    }

    @Override
    public Class<RewardBillEventDto> getTClass() {
        return RewardBillEventDto.class;
    }

    @Override
    public void handle(RewardBillEventDto rewardBillEventDto) throws Exception {
        billService.createRewardBill(rewardBillEventDto);
    }
}
