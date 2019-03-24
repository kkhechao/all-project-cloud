package com.zqkh.wallet.context.appservice.impl.domain.service.dto;

import com.zqkh.wallet.context.appservice.impl.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/12/27 0027 15:25
 */
@Getter
@Setter
@AllArgsConstructor
public class WalletDomainDto {

    public enum Source{
        BANK,
        WECHAT,
        ALIPAY
    }

    private Account account;

    private BigDecimal money;

    private String comment;

    private String billNo;

    private Source source;
}
