package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import com.jovezhao.nest.ddd.ValueObject;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 子账户
 */
@Getter
public abstract class AbstractSubAccount extends ValueObject{

    private BigDecimal availableAmount = BigDecimal.ZERO;

    private BigDecimal freezeAmount = BigDecimal.ZERO;

    public static final String AMOUNT_NOT_ENOUGH = "金额不能为零";
    public static final String AVAILABLE_AMOUNT_NOT_ENOUGH = "可用余额不足";


    /**
     * 增加可用余额
     * @param money
     * @throws BusinessException
     */
    protected void addAvailableAmount(BigDecimal money) throws BusinessException {
        checkMoney(money);
        this.availableAmount = this.availableAmount.add(money);
    }

    /**
     * 减少可用余额
     * @param money
     * @throws BusinessException
     */
    protected void subAvailableAmount(BigDecimal money) throws BusinessException {
        checkMoney(money);
        if(availableAmount.compareTo(money) == -1){
            throw new BusinessException(AVAILABLE_AMOUNT_NOT_ENOUGH, money);
        }
        this.availableAmount = this.availableAmount.subtract(money);
    }

    /**
     * 减少可用余额
     * @param money
     * @throws BusinessException
     */
    protected void subFrezzAmount(BigDecimal money) throws BusinessException {
        checkMoney(money);
        if(freezeAmount.compareTo(money) == -1){
            throw new BusinessException(AVAILABLE_AMOUNT_NOT_ENOUGH, money);
        }
        this.freezeAmount = this.freezeAmount.subtract(money);
    }

    /**
     * 冻结金额
     * @param money
     * @throws BusinessException
     */
    public void freeze(BigDecimal money) throws BusinessException {
        checkMoney(money);
        this.freezeAmount = this.freezeAmount.add(money);
    }

    /**
     * 解冻金额
     * @param money
     * @throws BusinessException
     */
    public void unFreeze(BigDecimal money) throws BusinessException {
        this.freezeAmount = this.freezeAmount.subtract(money);
    }


    protected void checkMoney(BigDecimal money) throws BusinessException {
        if(money.compareTo(BigDecimal.ZERO) != 1){
            throw new BusinessException(AMOUNT_NOT_ENOUGH, money);
        }
    }
}
