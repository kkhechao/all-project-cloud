package com.zqkh.wallet.context.appservice.impl.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-01-01 13:20
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingDetailDto {
    private String accountId;

    private String title;

    private BigDecimal amount;

    public enum  Direction{
        IN,
        OUT
    }

    private Direction direction;

    public enum Type{
        RMB,
        INTEGRAL
    }

    private Type type;

    public enum Role{
        USER,
        PLATFORM,
        SUPPLIER
    }

    private Role role;

    @Override
    public String toString() {
        return "FundingDetailDto{" +
                "accountId='" + accountId + '\'' +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", direction=" + direction +
                ", type=" + type +
                ", role=" + role +
                '}';
    }
}
