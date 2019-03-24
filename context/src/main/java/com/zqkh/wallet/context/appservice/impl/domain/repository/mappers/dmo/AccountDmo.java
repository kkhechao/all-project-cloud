package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDmo {
    private String id;

    private BigDecimal withdrawAvailableMoney;

    private BigDecimal consumptionAvailableMoney;

    private BigDecimal withdrawFreezeMoney;

    private BigDecimal consumptionFreezeMoney;

    private BigDecimal integralAvailableMoney;

    private BigDecimal integralFreezeMoney;
}