package com.zqkh.wallet.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-19 9:18
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardBillEventDto implements Serializable {

    public static final String EVENT_NAME = "REWARD_BILL_EVENT";

    private List<FundingDetailEventDto> fundingDetailEventDtos;
}
