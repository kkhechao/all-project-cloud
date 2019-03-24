package com.zqkh.wallet.context.appservice.impl.domain;

import com.zqkh.wallet.context.appservice.exception.BusinessException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wenjie
 * 积分子账户
 * @date 2017/12/25 0025 17:29
 */
@AllArgsConstructor
public class IntegralAccount extends AbstractSubAccount {

    /**
     * 增加
     */
    public void add(BigDecimal money) throws BusinessException {
        this.addAvailableAmount(money);
    }

    /**
     * 减少
     */
    public void sub(BigDecimal money) throws BusinessException {
        this.subAvailableAmount(money);
    }

}
