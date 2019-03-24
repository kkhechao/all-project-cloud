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
 * @create 2018-01-02 17:21
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RechargeBillDetailDto {

    private String title;

    private BigDecimal amount;

    public enum PayWay {
        WECHAT,
        ALIPAY,
        BALANCE
    }

    private PayWay PayWay;

    public enum Status {
        PAID,
        UNPAID
    }

    private Status status;

    private Date createTime;

    private Date paidTime;

    private String billNumber;

    private String remark;

    private List<FundingDetailDto> fundingDetails;

}
