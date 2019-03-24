package com.zqkh.wallet.context.appservice.impl.domain.service;

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
import java.util.List;

/**
 * @author wenjie
 * @date 2017/12/28 0028 18:03
 */
@Service
public class RechargeDomainService {

    private EntityLoader<SerialLog> serialLogLoader = new ConstructLoader<>(SerialLog.class);
    private EntityLoader<MoneyPool> moneyPoolLoader = new RepositoryLoader<>(MoneyPool.class);
    private EntityLoader<Account> accountLoader = new RepositoryLoader<>(Account.class);

    public void recharge(Constants.Source source, String billNo, List<FundingDetailDto> fundingDetailList) {
        MoneyPool moneyPool = moneyPoolLoader.create(new SourceIdentifier(source.toString()));
        for (FundingDetailDto fundingDetail : fundingDetailList) {
            System.err.println("funding=" + fundingDetail);
            BigDecimal money = fundingDetail.getAmount();
            String comment = fundingDetail.getTitle();
            FundingDetailDto.Direction direction = fundingDetail.getDirection();
            String sourceStr = source.toString();
            Account account = accountLoader.create(new StringIdentifier(fundingDetail.getAccountId()));

            //校验金额
            CheckMoneyUtil.checkMoney(money);

            //保存之前的总金额
            BigDecimal balance = account.getConsumptionAccount().getAvailableAmount().add(account.getWithdrawAccount().getAvailableAmount());

            FundingDetailDto.Role role = fundingDetail.getRole();
            SerialLog.Type serialLogType = null;
            if(FundingDetailDto.Role.USER == role) {
                ConsumptionAccount consumptionAccount = account.getConsumptionAccount();
                consumptionAccount.recharge(money);
                account.updateConsumptionAccount(consumptionAccount);

                serialLogType = SerialLog.Type.RECHARGE;
            }else if(FundingDetailDto.Role.PLATFORM == role){
                WithdrawAccount withdrawAccount = account.getWithdrawAccount();
                withdrawAccount.withdraw(money);
                account.updateWithdrawAccount(withdrawAccount);

                serialLogType = SerialLog.Type.FEE;
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
