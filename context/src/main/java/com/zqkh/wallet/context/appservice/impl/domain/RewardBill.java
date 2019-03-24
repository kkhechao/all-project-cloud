package com.zqkh.wallet.context.appservice.impl.domain;


import com.zqkh.wallet.event.dto.RewardBillEventDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-08 17:04
 **/
@Getter
public class RewardBill extends Bill {

    public void init(String userId) {
        this.userId = userId;
        this.createTime = LocalDateTime.now();
        this.type = Type.REWARD;
    }

    public void createFundingDetail(RewardBillEventDto rewardBillEventDto) {
        List<FundingDetail> fundingDetailList = new ArrayList<>(2);
        rewardBillEventDto.getFundingDetailEventDtos().forEach(p -> {
            getBillFunding(fundingDetailList, p);
        });
        this.fundingDetails = fundingDetailList;
    }
}
