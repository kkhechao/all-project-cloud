package com.zqkh.wallet.context.appservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hty
 * @create 2017-12-27 15:39
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectInfoDto {

    private String accountId;

    private BigDecimal amount;
}
