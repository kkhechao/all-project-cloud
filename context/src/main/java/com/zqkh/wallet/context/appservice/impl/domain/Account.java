package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户
 *
 * @author kok
 */
@Getter
@NoArgsConstructor
public class Account extends EntityObject {

    /**
     * 银行卡
     */
    private List<BankCard> bankCardList;

    /**
     * 子账户
     */
    private ConsumptionAccount consumptionAccount = new ConsumptionAccount();

    private WithdrawAccount withdrawAccount = new WithdrawAccount();

    private IntegralAccount integralAccount = new IntegralAccount();
    
    /**
     * 更新消费子账户
     *
     * @param consumptionAccount
     */
    public void updateConsumptionAccount(ConsumptionAccount consumptionAccount) {
        this.consumptionAccount = consumptionAccount;
    }

    /**
     * 更新提现子账户
     *
     * @param withdrawAccount
     */
    public void updateWithdrawAccount(WithdrawAccount withdrawAccount) {
        this.withdrawAccount = withdrawAccount;
    }

    /**
     * 更新积分子账户
     *
     * @param integralAccount
     */
    public void updateIntegralAccount(IntegralAccount integralAccount) {
        this.integralAccount = integralAccount;
    }

    /**
     * 添加银行卡
     *
     * @param bankCard
     */
    public void addBankCard(BankCard bankCard) throws BusinessException {
        if (bankCardList == null) {
            bankCardList = new ArrayList<>();
        }
        if (StringUtils.isEmpty(bankCard.getCode())) {
            throw new BusinessException("银行编码为空", bankCard);
        }
        if (StringUtils.isEmpty(bankCard.getName())) {
            throw new BusinessException("开户人姓名为空", bankCard);
        }
        if (StringUtils.isEmpty(bankCard.getNumber())) {
            throw new BusinessException("银行卡号为空", bankCard);
        }
        if (bankCard.getDefaultFlag()) {
            bankCardList.forEach(p -> p.closeDefaultFlag());
        }
        bankCardList.add(bankCard);
    }

    /**
     * 删除银行卡
     *
     * @param number
     * @throws BusinessException
     */
    public void removeBankCard(String number) throws BusinessException {
        if (bankCardList == null) {
            bankCardList = new ArrayList<>();
            return;
        }
        if (StringUtils.isEmpty(number)) {
            throw new BusinessException("银行卡号为空", number);
        }
        bankCardList = bankCardList.stream().filter(bank -> !number.equals(bank.getNumber())).collect(Collectors.toList());
    }

    public BankCard getDefaultBankCard() {
        if (bankCardList == null) {
            bankCardList = new ArrayList<>();
            return null;
        }
        List<BankCard> bankCards = bankCardList.stream().filter(bank -> bank.getDefaultFlag()).collect(Collectors.toList());
        if(bankCards.size() == 1){
            return bankCards.get(0);
        }else if(bankCardList.size() >= 1){
            //没有默认的随机用第一个
            return bankCardList.get(0);
        }else{
            return null;
        }
    }

}
