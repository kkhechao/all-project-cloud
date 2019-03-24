package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-01-02 11:40
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MallConsumptionBillDto {

    private String userId;

    @NotNull(message = "订单号不能为空")
    private String orderId;

    @NotNull(message = "消费金额")
    private BigDecimal amount;

    /**
     * 消费积分
     */
    private BigDecimal consumptionIntegral;


    /**
     * 代理商id
     */
    private String agentId;


}
