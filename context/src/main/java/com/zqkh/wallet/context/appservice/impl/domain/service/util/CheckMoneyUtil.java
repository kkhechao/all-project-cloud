package com.zqkh.wallet.context.appservice.impl.domain.service.util;

import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.context.appservice.impl.domain.AbstractSubAccount;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/12/28 0028 18:03
 */
public class CheckMoneyUtil {
    public static void checkMoney(BigDecimal money) throws BusinessException {
        if(money.compareTo(BigDecimal.ZERO) != 1){
            throw new BusinessException(AbstractSubAccount.AMOUNT_NOT_ENOUGH, money);
        }
    }
}
