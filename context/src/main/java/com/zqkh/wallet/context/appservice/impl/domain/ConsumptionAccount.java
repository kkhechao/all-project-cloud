package com.zqkh.wallet.context.appservice.impl.domain;

import com.zqkh.wallet.context.appservice.exception.BusinessException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;


/**
 * 消费子账户
 *
 * @author kok
 */
@AllArgsConstructor
public class ConsumptionAccount extends AbstractSubAccount {

    /**
     * 消费
     */
    public void consumption(BigDecimal money) throws BusinessException {
        this.subAvailableAmount(money);
    }

    /**
     * 充值
     */
    public void recharge(BigDecimal money) throws BusinessException {
        this.addAvailableAmount(money);
    }

    /**
     * 转账增加
     * @param money
     * @throws BusinessException
     */
    public void transfer(BigDecimal money) throws BusinessException {
        this.addAvailableAmount(money);
    }
}
