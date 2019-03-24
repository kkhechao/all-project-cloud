package com.zqkh.wallet.context.appservice.impl.domain;


import com.zqkh.wallet.event.dto.ConsumptionBillEventDto;
import com.zqkh.wallet.event.dto.FundingDetailEventDto;
import com.zqkh.wallet.event.dto.MemberAccountDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-01 10:32
 **/
@Getter
public class VipBill extends ConsumptionBill {

    private String orderId;

    public void initOrderId(String orderId) {
        this.orderId = orderId;
    }

}
