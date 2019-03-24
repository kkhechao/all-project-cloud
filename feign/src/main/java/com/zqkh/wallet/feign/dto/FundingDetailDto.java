package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hty
 * @create 2018-01-01 13:20
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingDetailDto {

    private String accountId;

    private String title;

    private BigDecimal amount;

    private Date createTime;

    public enum  Direction{
        IN,
        OUT
    }

    private Direction direction;

}
