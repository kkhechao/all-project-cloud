package com.zqkh.wallet.context.appservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/11/25 0025 10:49
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class WalletDto {

    private BigDecimal money;

    private String accountId;

    private String comment;

    private String billNo;
}
