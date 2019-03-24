package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author hty
 * @create 2017-12-28 10:21
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawBillDto {

    private String userId;

    private WithdrawBankInfoDto withdrawBankInfoDto;

    private BigDecimal fee;

    private BigDecimal tax;

    private BigDecimal withdrawAmount;

    private BigDecimal remittanceAmount;

    private WithdrawBillSMSDto withdrawBillSMSDto;

}
