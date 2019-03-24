package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author hty
 * @create 2018-04-02 11:53
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawResultDto {
    private BigDecimal withdrawAmount;

    private String account;

    private Date createTime;

    private BigDecimal fee;

    private BigDecimal tax;

    private BigDecimal remittanceAmount;

}
