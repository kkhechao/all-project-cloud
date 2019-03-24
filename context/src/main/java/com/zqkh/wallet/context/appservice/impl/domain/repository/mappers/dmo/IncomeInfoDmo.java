package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import java.math.BigDecimal;

public class IncomeInfoDmo {
    private String consumptionInvoiceId;

    private String accountId;

    private BigDecimal amount;

    public String getConsumptionInvoiceId() {
        return consumptionInvoiceId;
    }

    public void setConsumptionInvoiceId(String consumptionInvoiceId) {
        this.consumptionInvoiceId = consumptionInvoiceId == null ? null : consumptionInvoiceId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}