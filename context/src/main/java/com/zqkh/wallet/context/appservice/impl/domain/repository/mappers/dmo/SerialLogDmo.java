package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class SerialLogDmo {
    private String id;

    private String billNo;

    private BigDecimal money;

    private String direction;

    private Date createTime;

    private String comment;

    private String accountId;

    private String type;

    private BigDecimal balance;

    private String source;
}