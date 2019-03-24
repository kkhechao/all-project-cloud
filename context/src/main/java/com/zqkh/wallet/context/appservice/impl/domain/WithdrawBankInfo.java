package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.ValueObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hty
 * @create 2018-03-30 16:05
 **/
@Getter
@Setter
public class WithdrawBankInfo extends ValueObject{

    private String bankName;

    private String type;

    private String taxNumber;

    private String account;

    private String cardNumber;

    private String bankAddress;

    private String name;

    public void init(String bankName,String type,String taxNumber,String account,String cardNumber,String bankAddress,String name){
        this.bankName = bankName;
        this.type = type;
        this.taxNumber = taxNumber;
        this.account = account;
        this.cardNumber = cardNumber;
        this.bankAddress = bankAddress;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WithdrawBankInfo that = (WithdrawBankInfo) o;

        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (taxNumber != null ? !taxNumber.equals(that.taxNumber) : that.taxNumber != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (bankAddress != null ? !bankAddress.equals(that.bankAddress) : that.bankAddress != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = bankName != null ? bankName.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (taxNumber != null ? taxNumber.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (bankAddress != null ? bankAddress.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
