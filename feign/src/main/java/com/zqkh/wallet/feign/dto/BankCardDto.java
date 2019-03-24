package com.zqkh.wallet.feign.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 银行卡信息
 * @author kok
 */
@NoArgsConstructor
@Getter
@Setter
public final class BankCardDto {

    private String smallImg;

    /**
     * 银行代码
     */
    @NotNull(message = "银行卡编号不能为空")
    private String code;


    @NotNull(message = "银行名称不能为空")
    private String bankName;

    @NotNull(message = "卡类型不能为空")
    private String cardType;

    /**
     * 银行卡类型
     */
    public enum Type{
        PUBLIC,
        PRIVATE
    }

    @NotNull(message = "开户行地址不能为空")
    private String bankAddress;

    private Type type = Type.PRIVATE;

    /**
     * 卡号
     */
    @NotNull(message = "银行卡号为空")
    private String number;

    /**
     * 默认标记
     */
    private Boolean defaultFlag = false;

    /**
     * 持卡人姓名
     */
   @NotNull(message = "持卡人姓名不能为空")
    private String name;
}
