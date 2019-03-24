package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.ValueObject;
import com.sun.org.apache.bcel.internal.generic.NamedAndTyped;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 银行卡信息
 * @author kok
 */
@NoArgsConstructor
@Getter
public class BankCard extends ValueObject {

    /**
     * 银行代码
     */
    private String code;

    /**
     * 银行卡类型
     */
    public enum Type{
        PUBLIC,
        PRIVATE
    }

    private String bankName;

    private String cardType;

    private String bankAddress;

    private Type type;

    /**
     * 卡号
     */
    private String number;

    /**
     * 默认标记
     */
    private Boolean defaultFlag = false;

    /**
     * 持卡人姓名
     */
    private String name;

    public void init(String code, String number, Boolean defaultFlag, String name, Type type,String bankAddress, String bankName,String cardType) {
        this.code = code;
        this.number = number;
        this.defaultFlag = defaultFlag;
        this.name = name;
        this.type = type;
        this.bankAddress = bankAddress;
        this.bankName = bankName;
        this.cardType = cardType;
    }

    public void closeDefaultFlag() {
        this.defaultFlag = false;
    }

    /**
     * value object compare
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj != null && this.getClass() == obj.getClass()){
            BankCard bankCard = (BankCard) obj;
            result = this.code.equals(bankCard.getCode()) && this.name.equals(bankCard.getName()) && this.number.equals(bankCard.getName()) && this.defaultFlag.equals(bankCard.getDefaultFlag());
        }
        return result;
    }
}
