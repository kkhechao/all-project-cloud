package com.zqkh.wallet.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-01-18 14:53
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAccountDto implements Serializable{

    private String id;
    private BigDecimal money;
    private String role;
}
