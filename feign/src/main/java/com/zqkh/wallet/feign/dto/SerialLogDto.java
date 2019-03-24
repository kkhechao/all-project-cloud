package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wenjie
 * @date 2018/1/2 0002 16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class SerialLogDto {

    private String type;

    private Date createTime;

    private BigDecimal money;

    private BigDecimal balance;

    private String direction;
}
