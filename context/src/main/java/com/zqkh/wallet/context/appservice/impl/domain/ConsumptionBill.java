package com.zqkh.wallet.context.appservice.impl.domain;

import com.zqkh.wallet.event.dto.ConsumptionBillEventDto;
import com.zqkh.wallet.event.dto.FundingDetailEventDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 消费账单
 *
 * @author hty
 * @create 2017-12-25 14:36
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ConsumptionBill extends Bill {

    public enum Status {
        SUCCESS,
        FAIL,
        UNPROCESS
    }

    protected Status status;

    protected String businessOrderId;

    protected String rechargeBillNumber;

    public enum ConsumptionType{
        USER_ACTIVE,
        UP_TO_VIP,
        UP_TO_AGENT,
        MALL_CONSUMPTION,
        PURCHASE
    }

    protected ConsumptionType consumptionType;

    public void init(String userId, LocalDateTime createTime, ConsumptionBill.Status
            status, String businessOrderId, String rechargeBillNumber,String consumptionType) {
        this.userId = userId;
        this.createTime = createTime;
        this.status = status;
        this.businessOrderId = businessOrderId;
        this.type = Type.CONSUMPTION;
        this.rechargeBillNumber = rechargeBillNumber;
        this.consumptionType = ConsumptionType.valueOf(consumptionType);
    }

    public void createFundingDetail(ConsumptionBillEventDto consumptionBillEventDto) {
        List<FundingDetail> fundingDetailList = new ArrayList<>(4);
        consumptionBillEventDto.getFundingDetailEventDtos().forEach(p -> {
            getBillFunding(fundingDetailList, p);
        });
        this.fundingDetails = fundingDetailList;
    }
}
