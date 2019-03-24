package com.zqkh.wallet.feign.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hty
 * @create 2018-03-30 16:26
 **/
@Getter
@Setter
public class WithdrawBankInfoDto {

    private String bankName;

    private String type;

    private String taxNumber;

    private String account;

    private String cardNumber;

    private String bankAddress;

    private String name;
}
