package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import java.math.BigDecimal;

public class DayMoneyDmo {
    private String id;

    private String day;

    private String source;

    private BigDecimal money;

    private String yesterday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

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

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday == null ? null : yesterday.trim();
    }
}