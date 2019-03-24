package com.zqkh.wallet.context.appservice.impl.domain;

import com.zqkh.wallet.context.appservice.exception.BusinessException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 提现子账户
 * @author kok
 */
@AllArgsConstructor
public class WithdrawAccount extends AbstractSubAccount {

    /**
     * 提现
     * @param money
     * @throws BusinessException
     */
    public void withdraw(BigDecimal money) throws BusinessException {
        this.subAvailableAmount(money);
    }

    /**
     * 增加可提现金额
     * @param money
     * @throws BusinessException
     */
    public void addWithdrawMoney(BigDecimal money) throws BusinessException {
        this.addAvailableAmount(money);
    }

    /**
     * 转账减少
     * @param money
     * @throws BusinessException
     */
    public void transfer(BigDecimal money) throws BusinessException {
        this.subAvailableAmount(money);
    }

    public void withdrawFromFrezz(BigDecimal money) {
        this.subFrezzAmount(money);
    }
}
