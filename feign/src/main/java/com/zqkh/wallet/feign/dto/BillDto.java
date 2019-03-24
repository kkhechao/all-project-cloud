package com.zqkh.wallet.feign.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hty
 * @create 2018-01-02 16:23
 **/
@NoArgsConstructor
@Getter
@Setter
public class BillDto {

    private String id;

    private String title;

    private Date createTime;

    private String type;

    private BigDecimal amount;
}
