package com.zqkh.wallet.feign.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author hty
 * @create 2018-03-31 15:23
 **/
@Getter
@Setter
public class WithdrawBillSMSDto {
    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "验证码不能为空")
    private String captcha;
}
