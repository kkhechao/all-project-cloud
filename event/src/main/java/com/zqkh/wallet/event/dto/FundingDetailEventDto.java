package com.zqkh.wallet.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-01-22 9:39
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingDetailEventDto implements Serializable{

    //角色编号  用户ID 组织ID  平台ID
    private String roleId;

    public enum RoleType{
        USER,
        SUPPLIER,
        PLATFORM
    }

    public enum  Direction{
        IN,
        OUT
    }

    public enum Type{
        RMB,
        INTEGRAL
    }

    private Type type;

    private Direction direction;

    private RoleType roleType;

    private String accountId;

    private BigDecimal amount;

    private String title;

}
