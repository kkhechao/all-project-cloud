package com.zqkh.wallet.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author hty
 * @create 2018-01-24 21:43
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreezeEventDto implements Serializable{

    public static final String EVENT_NAME = "FREEZE_EVENT";

    public enum ConsumptionType{
        VIP_CONSUMPTION,
        USER_ACTIVE_CONSUMPTION,
        AGENT_CONSUMPTION,
        MALL_CONSUMPTION,
        PURCHASE
    }

    private String userId;

    private String rechargeBillId;

    private ConsumptionType consumptionType;

    private String businessOrderId;

    public enum  PayWay{
        WECHAT,
        ALIPAY,
        BALANCE
    }

    private PayWay payWay;

}
