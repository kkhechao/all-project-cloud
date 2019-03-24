package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-03 15:09
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionBillDetailDto {

    public enum ShowType{
        REWARD,
        MALL_CONSUMPTION,
        VIP_CONSUMPTION,
        ACTIVE_AGENT
    }

    private ShowType showType;

    private String title;

    private String status = "SUCCESS";

    private BigDecimal amount;

    private String payWay = "BALANCE";

    private String goodsName;

    private String orderNumber;

    private Date createTime;

    private String billNumber;

    private List<FundingDetailDto> fundingDetails;

}
