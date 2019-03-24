package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 流水日志
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SerialLog extends EntityObject {

    public enum Direction{
        IN,
        OUT
    }

    public enum Type{
        WITHDRAW,
        CONSUMPTION,
        RECHARGE,
        COLLECT,
        TRANSFER,
        INTEGRAL,
        FEE,
        REWARD
    }

    public String source;

    private Type type;

    /**
     * 账单编号
     */
    private String billNo;

    private BigDecimal money;

    private String account;

    private Direction direction;

    private String comment;

    private LocalDateTime createTime;

    private BigDecimal balance;

    public void init(BigDecimal balance, String billNo, BigDecimal money, String account, Direction direction, String comment, LocalDateTime payTime, Type type, String source) {
        this.balance = balance;
        this.billNo = billNo;
        this.money = money;
        this.account = account;
        this.direction = direction;
        this.comment = comment;
        this.createTime = payTime;
        this.type = type;
        this.source = source;
    }
}
