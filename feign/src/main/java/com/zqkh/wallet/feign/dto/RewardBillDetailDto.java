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
 * @create 2018-01-24 17:41
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardBillDetailDto {

    private String title;

    private String status = "SUCCESS";

    private BigDecimal amount;

    private Date createTime;

    private String billNumber;

    private List<FundingDetailDto> fundingDetails;
}
