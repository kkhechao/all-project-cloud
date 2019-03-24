package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

public class WithdrawBankInfoDmo {
    private String bankName;

    private String type;

    private String taxNumber;

    private String account;

    private String cardNumber;

    private String bankAddress;

    private String name;

    private String withdrawBillId;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress == null ? null : bankAddress.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWithdrawBillId() {
        return withdrawBillId;
    }

    public void setWithdrawBillId(String withdrawBillId) {
        this.withdrawBillId = withdrawBillId == null ? null : withdrawBillId.trim();
    }
}