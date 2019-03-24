package com.zqkh.wallet.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-22 9:45
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionBillEventDto implements Serializable{

    public static final String EVENT_NAME = "CREATE_CONSUMPTION_BILL_EVENT";

    public enum billType{
        USER_ACTIVE,
        UP_TO_VIP,
        UP_TO_AGENT,
        MALL_CONSUMPTION,
        PURCHASE
    }

    private String userId;

    private billType billType;

    private String orderId;

    private String businessOrderId;

    private String rechargeBillNumber;

    private List<FundingDetailEventDto> fundingDetailEventDtos;
}
