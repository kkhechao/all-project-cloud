package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.context.appservice.impl.domain.AbstractSubAccount;
import com.zqkh.wallet.context.appservice.impl.domain.Account;
import com.zqkh.wallet.context.appservice.impl.domain.ConsumptionAccount;
import com.zqkh.wallet.context.appservice.impl.domain.WithdrawAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wenjie
 * 冻结领域服务
 * @date 2018/1/16 0016 14:16
 */
@Service
public class FreezeDomainService {

    private EntityLoader<Account> accountLoader = new RepositoryLoader<>(Account.class);

    public void freeze(String accountId, BigDecimal money){
        Account account = accountLoader.create(new StringIdentifier(accountId));

        ConsumptionAccount consumptionAccount = account.getConsumptionAccount();
        BigDecimal conMoney = consumptionAccount.getAvailableAmount();

        if (conMoney.compareTo(money) == -1) {
            throw new BusinessException(AbstractSubAccount.AVAILABLE_AMOUNT_NOT_ENOUGH, money, account);
        }

        if(BigDecimal.ZERO.compareTo(money) == -1) {
            consumptionAccount.consumption(money);
            consumptionAccount.freeze(money);
        }

        account.updateConsumptionAccount(consumptionAccount);
    }

    public void withdrawFreeze(String accountId, BigDecimal money){
        Account account = accountLoader.create(new StringIdentifier(accountId));
        WithdrawAccount withdrawAccount = account.getWithdrawAccount();
        BigDecimal availableAmount = withdrawAccount.getAvailableAmount();
        if (availableAmount.compareTo(money) == -1) {
            throw new BusinessException(AbstractSubAccount.AVAILABLE_AMOUNT_NOT_ENOUGH, money, account);
        }else{
            withdrawAccount.withdraw(money);
            withdrawAccount.freeze(money);
        }
        account.updateWithdrawAccount(withdrawAccount);
    }
}
