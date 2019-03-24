package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/11/27 0027 17:07
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class FreezeDto {

    public enum ACTION{
        FREEZE,
        UNFREEZE
    }

    public enum ACCOUNT{
        CONSUMPTION,
        WITHDRAW
    }

    @NotNull
    ACTION action;

    @NotNull
    ACCOUNT account;

    /**
     * 账户ID
     */
    @NotBlank(message = "账户ID不能为空")
    String accountId;

    /**
     * 金额
     */
    @NotNull
    BigDecimal money;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户不能为空")
    String userId;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空")
    String comment;
}
