package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-01-01 10:04
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundingDetail extends ValueObject{

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


}
