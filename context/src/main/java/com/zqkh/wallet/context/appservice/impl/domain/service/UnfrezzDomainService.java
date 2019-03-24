package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.zqkh.wallet.context.appservice.impl.domain.Account;
import com.zqkh.wallet.context.appservice.impl.domain.WithdrawAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-04-04 11:05
 **/
@Service
public class UnfrezzDomainService {

    private EntityLoader<Account> accountLoader = new RepositoryLoader<>(Account.class);

    public void withdrawUnFreeze(String accountId, BigDecimal money) {
        Account account = accountLoader.create(new StringIdentifier(accountId));
        WithdrawAccount withdrawAccount = account.getWithdrawAccount();
        withdrawAccount.unFreeze(money);
        withdrawAccount.addWithdrawMoney(money);
        account.updateWithdrawAccount(withdrawAccount);
    }
}
