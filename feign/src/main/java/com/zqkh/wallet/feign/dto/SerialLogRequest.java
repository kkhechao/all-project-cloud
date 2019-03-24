package com.zqkh.wallet.feign.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author wenjie
 * @date 2018/1/3 0003 14:12
 */
@Getter
@Setter
public class SerialLogRequest {

    private String accountId;

    private Integer pageSize = 1;

    private Integer pageIndex = 10;

}
