package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/12/27 0027 14:20
 */
@Getter
@Setter
@AllArgsConstructor
public final class WithdrawInfoDto {

    private BankCardDto bankCardDto;

    private BigDecimal availableAmount;

    private BigDecimal percent;

    private Integer usableWithdrawCount;

}
