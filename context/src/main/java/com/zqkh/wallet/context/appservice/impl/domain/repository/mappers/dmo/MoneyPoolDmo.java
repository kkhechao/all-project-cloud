package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import java.math.BigDecimal;

public class MoneyPoolDmo {
    private String source;

    private BigDecimal money;

    private String comment;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}