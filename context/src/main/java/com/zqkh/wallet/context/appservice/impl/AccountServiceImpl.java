package com.zqkh.wallet.context.appservice.impl;

import com.jovezhao.nest.PageList;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.ConstructLoader;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.jovezhao.nest.mybatis.PageParames;
import com.jovezhao.nest.starter.AppService;
import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.context.appservice.dto.WalletDto;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.context.appservice.impl.domain.*;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.SerialLogDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.TotalMoneyDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.query.BankCardQuery;
import com.zqkh.wallet.context.appservice.impl.domain.repository.query.SerialLogQuery;
import com.zqkh.wallet.context.appservice.impl.domain.service.QueryUsableWithdrawCountDomainService;
import com.zqkh.wallet.context.appservice.inter.AccountService;
import com.zqkh.wallet.context.configuration.CloudConfigProperties;
import com.zqkh.wallet.feign.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenjie
 * @date 2017/11/25 0025 10:47
 */
@AppService
public class AccountServiceImpl implements AccountService {

    public static final String ACCOUNT_NOT_EXITIS = "未找到钱包账户";
    EntityLoader<Account> builder = new RepositoryLoader<>(Account.class);
    EntityLoader<DayMoney> dayMoneyBuilder = new ConstructLoader<>(DayMoney.class);

    @Autowired
    private CloudConfigProperties cloudConfigProperties;


    @Autowired
    private SerialLogQuery serialLogQuery;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private BankCardQuery bankCardQuery;

    @Autowired
    private QueryUsableWithdrawCountDomainService queryUsableWithdrawCountDomainService;

    @Override
    public WithdrawInfoDto withdraw(String userId,String accountId) throws BusinessException {
        Account account = builder.create(new StringIdentifier(accountId));
        if (account == null) {
            throw new BusinessException(ACCOUNT_NOT_EXITIS, accountId);
        }
        BankCard defaultBankCard = account.getDefaultBankCard();
        BankCardDto bankCardDto = null;
        if (defaultBankCard != null) {
            bankCardDto = dozerBeanMapper.map(defaultBankCard, BankCardDto.class);
        }
        BigDecimal availableAmount = account.getWithdrawAccount().getAvailableAmount();
        CloudConfigProperties.Fee fee = cloudConfigProperties.getFee();

        //返回手续费比例及可用余额
        BigDecimal withdrawPercent = fee.getWithdrawPercent();

        //返回可用提现次数
        int usableWithdrawCount = queryUsableWithdrawCountDomainService.QueryUsableWithdrawCountDomainService(userId);
        return new WithdrawInfoDto(bankCardDto, availableAmount, withdrawPercent,usableWithdrawCount);
    }


    @Override
    public void addIntegral(WalletDto walletDto) throws BusinessException {
        Account account = builder.create(new StringIdentifier(walletDto.getAccountId()));

        if (account == null) {
            throw new BusinessException(ACCOUNT_NOT_EXITIS, walletDto.getAccountId());
        }

        IntegralAccount integralAccount = account.getIntegralAccount();
        integralAccount.add(walletDto.getMoney());
        account.updateIntegralAccount(integralAccount);
    }

    @Override
    public void createWalletAccount(String accountId) {
        EntityLoader<Account> builder = new ConstructLoader<>(Account.class);
        builder.create(new StringIdentifier(accountId));
    }

    @Override
    public WalletInfoDto getWalletInfo(String accountId) {
        Account account = builder.create(new StringIdentifier(accountId));

        if (account == null) {
            throw new BusinessException(ACCOUNT_NOT_EXITIS, accountId);
        }

        WithdrawAccount withdrawAccount = account.getWithdrawAccount();
        ConsumptionAccount consumptionAccount = account.getConsumptionAccount();

        //可提现余额
        BigDecimal availableAmount = withdrawAccount.getAvailableAmount();
        //总金额等于可用余额
        BigDecimal totalMoney = consumptionAccount.getAvailableAmount();


        BigDecimal integralAmount = account.getIntegralAccount().getAvailableAmount();
        BigDecimal withdrawFreezeMoney = account.getWithdrawAccount().getFreezeAmount();

        return new WalletInfoDto(totalMoney, availableAmount, integralAmount,withdrawFreezeMoney);
    }

    @Override
    public PageResult<SerialLogDto> getIntegralDetail(SerialLogRequest serialLogRequest) {
        PageParames pageParames = PageParames.create(serialLogRequest.getPageIndex(), serialLogRequest.getPageSize());
        PageList<SerialLogDmo> serialLogDmoList = serialLogQuery.findByAccountId(serialLogRequest.getAccountId(), pageParames);
        List<SerialLogDto> resultList = serialLogDmoList.getList().stream().map(log -> dozerBeanMapper.map(log, SerialLogDto.class)).collect(Collectors.toList());

        PageResult<SerialLogDto> pageResult = new PageResult<>();
        pageResult.setPageSize(serialLogDmoList.getPageSize());
        pageResult.setTotalCount(serialLogDmoList.getTotalCount());
        pageResult.setList(resultList);

        return pageResult;
    }

    @Override
    public void saveTodayMoneyTotal() {
        List<TotalMoneyDmo> todayMoney = serialLogQuery.findTodayMoney();
        List<TotalMoneyDmo> yesterdayTotalMoney = serialLogQuery.findYesterdayTotalMoney();
        for (TotalMoneyDmo today : todayMoney) {
            DayMoney dayMoney = dayMoneyBuilder.create(IdGenerator.getInstance().generate(DayMoney.class));
            //昨天金额列表查询金额
            for (TotalMoneyDmo totalMoneyDmo : yesterdayTotalMoney) {
                if (today.getSource().equals(totalMoneyDmo.getSource())) {
                    dayMoney.init(LocalDateTime.now(), today.getSource(), today.getMoney(), totalMoneyDmo.getMoney());
                    break;
                }
            }
        }
    }

    @Override
    public List<BankCardDto> getBanks(String accountId) {
        Account account = builder.create(new StringIdentifier(accountId));
        if (account == null) {
            throw new BusinessException(ACCOUNT_NOT_EXITIS, accountId);
        }
        if(account.getBankCardList()!=null){
            return account.getBankCardList().stream().map(card -> dozerBeanMapper.map(card, BankCardDto.class)).collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public void addBankCard(BankCardDto bankCardDto, String accountId) throws BusinessException {
        String cardNumber = bankCardQuery.selectBankCard(accountId,bankCardDto.getNumber());
        if(StringUtils.isNotEmpty(cardNumber)){
            throw  new BusinessException("该银行卡已被绑定");
        }
        Account account = builder.create(new StringIdentifier(accountId));
        if (account == null) {
            throw new BusinessException(ACCOUNT_NOT_EXITIS, accountId);
        }
        BankCard bankCard = new BankCard();
        bankCard.init(bankCardDto.getCode(), bankCardDto.getNumber(), bankCardDto.getDefaultFlag(), bankCardDto.getName(), BankCard.Type.valueOf(bankCardDto.getType().toString()),bankCardDto.getBankAddress(), bankCardDto.getBankName(),bankCardDto.getCardType());

        account.addBankCard(bankCard);
    }

    @Override
    public void removeBankCard(String number, String accountId) throws BusinessException {
        Account account = builder.create(new StringIdentifier(accountId));
        if (account == null) {
            throw new BusinessException(ACCOUNT_NOT_EXITIS, accountId);
        }
        account.removeBankCard(number);
    }
}
