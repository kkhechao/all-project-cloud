package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-01-02 11:13
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RechargeBillDto {

    private String userId;

    @NotNull(message = "充值金额不能为空")
    private BigDecimal rechargeAmount;

    public enum RechargeChannel {
        WECHAT,
        ALIPAY,
        BALANCE,
        BANK
    }

    @NotNull(message = "充值渠道不能为空")
    private RechargeChannel rechargeChannel;

    public enum RechargeType{
        RECHARGE,
        VIP_RECHARGE,
        USER_ACTIVE_RECHARGE,
        AGENT_RECHARGE,
        MALL_CONSUMPTION_RECHARGE,
        PURCHASE
    }

    @NotNull(message = "充值类型不能为空")
    private RechargeType rechargeType;

    private String orderNumber;

    private String requestParam;

    private String channelSerial;

    private String billId;

    public enum Level{
        GOLD,
        DIAMOND,
        CROWN
    }

    private Level level;


}
