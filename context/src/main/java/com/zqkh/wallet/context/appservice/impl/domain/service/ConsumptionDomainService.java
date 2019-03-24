package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.ConstructLoader;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.context.appservice.impl.domain.*;
import com.zqkh.wallet.context.appservice.impl.domain.service.dto.FundingDetailDto;
import com.zqkh.wallet.context.appservice.impl.domain.service.util.CheckMoneyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wenjie
 * @date 2018/1/16 0016 17:06
 */
@Service
@Slf4j
public class ConsumptionDomainService {

    public static final String BALANCE = "BALANCE";

    private EntityLoader<SerialLog> serialLogLoader = new ConstructLoader<>(SerialLog.class);
    private EntityLoader<Account> accountLoader = new RepositoryLoader<>(Account.class);

    public void consumption(String billNo, List<FundingDetailDto> fundingDetailList) {
        for (FundingDetailDto fundingDetailDto : fundingDetailList) {
            System.out.println(fundingDetailDto.toString());
            BigDecimal money = fundingDetailDto.getAmount();
            String comment = fundingDetailDto.getTitle();
            FundingDetailDto.Direction direction = fundingDetailDto.getDirection();
            FundingDetailDto.Role role = fundingDetailDto.getRole();
            FundingDetailDto.Type type = fundingDetailDto.getType();
            //交易时间
            LocalDateTime now = LocalDateTime.now();

            //校验金额
            CheckMoneyUtil.checkMoney(money);

            String accountId = fundingDetailDto.getAccountId();
            Account account = accountLoader.create(new StringIdentifier(accountId));

            //保存之前总金额
            BigDecimal balance = account.getConsumptionAccount().getAvailableAmount().add(account.getWithdrawAccount().getAvailableAmount());

            //serial log
            SerialLog serialLog = serialLogLoader.create(IdGenerator.getInstance().generate(SerialLog.class));

            //用户逻辑
            if (FundingDetailDto.Role.USER == role) {
                //用户积分账户
                if (FundingDetailDto.Type.INTEGRAL == type) {
                    IntegralAccount integralAccount = account.getIntegralAccount();
                    BigDecimal beforeTotalMoney = integralAccount.getAvailableAmount();
                    if (FundingDetailDto.Direction.IN == direction) {
                        integralAccount.add(money);
                    } else {
                        integralAccount.sub(money);
                    }
                    account.updateIntegralAccount(integralAccount);

                    serialLog.init(beforeTotalMoney, billNo, money, accountId, SerialLog.Direction.valueOf(direction.toString()), comment, now, SerialLog.Type.CONSUMPTION, BALANCE);
                } else { //用户人民币冻结消费操作
                    WithdrawAccount withdrawAccount = account.getWithdrawAccount();
                    ConsumptionAccount consumptionAccount = account.getConsumptionAccount();
                    BigDecimal totalMoney = withdrawAccount.getAvailableAmount().add(consumptionAccount.getAvailableAmount());

                    if (FundingDetailDto.Direction.OUT == direction) {
                        BigDecimal conMoney = consumptionAccount.getFreezeAmount();
                        System.out.println("con=" + conMoney);
                        if (conMoney.compareTo(money) == -1) {
                            throw new BusinessException(AbstractSubAccount.AVAILABLE_AMOUNT_NOT_ENOUGH, money, account);
                        }

                        consumptionAccount.unFreeze(money);

                        account.updateConsumptionAccount(consumptionAccount);

                        serialLog.init(totalMoney, billNo, money, accountId, SerialLog.Direction.OUT, comment, LocalDateTime.now(), SerialLog.Type.CONSUMPTION, BALANCE);
                    } else {
                        withdrawAccount.addWithdrawMoney(money);
                        account.updateWithdrawAccount(withdrawAccount);

                        serialLog.init(totalMoney, billNo, money, accountId, SerialLog.Direction.IN, comment, LocalDateTime.now(), SerialLog.Type.REWARD, BALANCE);
                    }

                }
            } else if (FundingDetailDto.Role.PLATFORM == role) {
                WithdrawAccount withdrawAccount = account.getWithdrawAccount();
                if (FundingDetailDto.Direction.IN == direction) {
                    withdrawAccount.addWithdrawMoney(money);
                } else {
                    withdrawAccount.withdraw(money);
                }

                //判断记录流水类型
                SerialLog.Type serialLogType;
                if (FundingDetailDto.Type.RMB == type) {
                    serialLogType = SerialLog.Type.COLLECT;
                } else {
                    serialLogType = SerialLog.Type.FEE;
                }
                account.updateWithdrawAccount(withdrawAccount);

                serialLog.init(balance, billNo, money, accountId, SerialLog.Direction.valueOf(direction.toString()), comment, now, serialLogType, BALANCE);
            } else if (FundingDetailDto.Role.SUPPLIER == role) {
                WithdrawAccount withdrawAccount = account.getWithdrawAccount();

                SerialLog.Type serialLogType;
                if (FundingDetailDto.Direction.IN == direction) {
                    withdrawAccount.addWithdrawMoney(money);
                    serialLogType = SerialLog.Type.COLLECT;
                } else {
                    withdrawAccount.withdraw(money);
                    serialLogType = SerialLog.Type.FEE;
                }

                //serial log
                serialLog.init(balance, billNo, money, accountId, SerialLog.Direction.valueOf(direction.toString()), comment, now, serialLogType, BALANCE);

                account.updateWithdrawAccount(withdrawAccount);
            }

            if (StringUtils.isEmpty(serialLog.getAccount())) {
                serialLog.init(null, billNo, money, accountId, null, "没有对应的类型" + fundingDetailDto.toString(), now, null, null);
            }
        }
    }

}
