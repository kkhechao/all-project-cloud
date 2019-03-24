package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.google.common.collect.Maps;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.ConstructLoader;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.zqkh.wallet.context.appservice.common.Constants;
import com.zqkh.wallet.context.appservice.impl.domain.*;
import com.zqkh.wallet.context.appservice.impl.domain.identifier.SourceIdentifier;
import com.zqkh.wallet.context.appservice.impl.domain.service.dto.FundingDetailDto;
import com.zqkh.wallet.context.appservice.impl.domain.service.util.CheckMoneyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author wenjie
 * @date 2017/12/28 0028 18:01
 */
@Service
public class WithDrawDomainService {

    public static final int MAX_CONTROL_ACCOUNT_SIZE = 3;

    private EntityLoader<SerialLog> serialLogLoader = new ConstructLoader<>(SerialLog.class);
    private EntityLoader<MoneyPool> moneyPoolLoader = new RepositoryLoader<>(MoneyPool.class);
    private EntityLoader<Account> accountLoader = new RepositoryLoader<>(Account.class);


    public void withdraw(Constants.Source source, String billNo, List<FundingDetailDto> fundingDetailList){
        //资金池增减
        MoneyPool moneyPool = moneyPoolLoader.create(new SourceIdentifier(source.toString()));

        //同一个账户操作多次，如果下方每次create，择只会有最后一个操作的仓储保存，所以用户保存
        HashMap<String, Account> accountMap = Maps.newHashMapWithExpectedSize(MAX_CONTROL_ACCOUNT_SIZE);

        for (FundingDetailDto fundingDetail : fundingDetailList) {
            BigDecimal money = fundingDetail.getAmount();
            String comment = fundingDetail.getTitle();
            FundingDetailDto.Direction direction = fundingDetail.getDirection();
            String sourceStr = source.toString();

            String accountId = fundingDetail.getAccountId();
            Account account = accountMap.get(accountId);
            if (account == null) {
                account = accountLoader.create(new StringIdentifier(accountId));
                accountMap.put(accountId, account);
            }
            BigDecimal balance ;
            //校验金额
            CheckMoneyUtil.checkMoney(money);
            FundingDetailDto.Role role = fundingDetail.getRole();
            WithdrawAccount withdrawAccount = account.getWithdrawAccount();
            SerialLog.Type serialLogType = null;
            if(FundingDetailDto.Role.USER == role) {
                serialLogType = SerialLog.Type.WITHDRAW;
                //保存之前的总金额
                balance = withdrawAccount.getFreezeAmount();
                withdrawAccount.withdrawFromFrezz(money);
                account.updateWithdrawAccount(withdrawAccount);
            }else{
                serialLogType = SerialLog.Type.FEE;
                //保存之前的总金额
                balance = withdrawAccount.getAvailableAmount();
                withdrawAccount.addWithdrawMoney(money);
                account.updateWithdrawAccount(withdrawAccount);
            }
            //serial log
            SerialLog rechargeSerialLog = serialLogLoader.create(IdGenerator.getInstance().generate(SerialLog.class));
            rechargeSerialLog.init(balance, billNo, money, account.getId().toValue(), SerialLog.Direction.valueOf(direction.toString()), comment, LocalDateTime.now(), serialLogType, sourceStr);

            //资金池
            if(FundingDetailDto.Direction.IN == direction) {
                moneyPool.addMoney(money);
            }else if(FundingDetailDto.Direction.OUT == direction){
                moneyPool.subMoney(money);
            }
        }
    }

}
