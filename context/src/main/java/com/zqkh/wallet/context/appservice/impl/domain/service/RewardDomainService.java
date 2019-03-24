package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.ConstructLoader;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.zqkh.wallet.context.appservice.impl.domain.Account;
import com.zqkh.wallet.context.appservice.impl.domain.SerialLog;
import com.zqkh.wallet.context.appservice.impl.domain.WithdrawAccount;
import com.zqkh.wallet.context.appservice.impl.domain.service.dto.FundingDetailDto;
import com.zqkh.wallet.context.appservice.impl.domain.service.util.CheckMoneyUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wenjie
 * @date 2018/1/8 0008 17:29
 */
@Service
public class RewardDomainService {

    private EntityLoader<SerialLog> serialLogLoader = new ConstructLoader<>(SerialLog.class);
    private EntityLoader<Account> accountLoader = new RepositoryLoader<>(Account.class);


    public void reward(String billNo, List<FundingDetailDto> fundingDetailList) {
        for (FundingDetailDto fundingDetail : fundingDetailList) {
            BigDecimal money = fundingDetail.getAmount();
            String comment = fundingDetail.getTitle();
            FundingDetailDto.Direction direction = fundingDetail.getDirection();
            Account account = accountLoader.create(new StringIdentifier(fundingDetail.getAccountId()));

            //校验金额
            CheckMoneyUtil.checkMoney(money);

            //保存之前的总金额
            BigDecimal balance = account.getConsumptionAccount().getAvailableAmount().add(account.getWithdrawAccount().getAvailableAmount());

            FundingDetailDto.Role role = fundingDetail.getRole();
            if(FundingDetailDto.Role.USER == role) {
                WithdrawAccount withdrawAccount = account.getWithdrawAccount();
                withdrawAccount.addWithdrawMoney(money);
                account.updateWithdrawAccount(withdrawAccount);
            }else if(FundingDetailDto.Role.PLATFORM == role){
                WithdrawAccount withdrawAccount = account.getWithdrawAccount();
                withdrawAccount.addWithdrawMoney(money);
                account.updateWithdrawAccount(withdrawAccount);
            }

            //serial log
            SerialLog rechargeSerialLog = serialLogLoader.create(IdGenerator.getInstance().generate(SerialLog.class));
            rechargeSerialLog.init(balance, billNo, money, account.getId().toValue(), SerialLog.Direction.valueOf(direction.toString()), comment, LocalDateTime.now(), SerialLog.Type.REWARD, "");
        }
    }

}
