package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/12/29 0029 14:28
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class WalletInfoDto  {

    private BigDecimal totalMoney;

    private BigDecimal availableMoney;

    private BigDecimal integral;

    private BigDecimal withdrawFreezeMoney;

}
