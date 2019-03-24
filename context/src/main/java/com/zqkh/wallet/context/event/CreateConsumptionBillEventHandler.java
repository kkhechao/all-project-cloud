package com.zqkh.wallet.context.event;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.zqkh.wallet.context.appservice.inter.BillService;
import com.zqkh.wallet.event.dto.ConsumptionBillEventDto;

/**
 * @author hty
 * @create 2018-01-22 11:18
 **/

public class CreateConsumptionBillEventHandler implements EventHandler<ConsumptionBillEventDto>{

    private BillService billService;

    public CreateConsumptionBillEventHandler(BillService billService){
        this.billService = billService;
    }

    @Override
    public String getEventName() {
        return ConsumptionBillEventDto.EVENT_NAME;
    }

    @Override
    public Class<ConsumptionBillEventDto> getTClass() {
        return ConsumptionBillEventDto.class;
    }

    @Override
    public void handle(ConsumptionBillEventDto consumptionBillEventDto) throws Exception {
        billService.createConsumptionBill(consumptionBillEventDto);
    }
}
