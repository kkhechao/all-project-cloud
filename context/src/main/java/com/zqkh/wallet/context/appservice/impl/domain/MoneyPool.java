package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.BaseEntityObject;
import com.zqkh.wallet.context.appservice.impl.domain.identifier.SourceIdentifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2018/1/2 0002 18:16
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MoneyPool extends BaseEntityObject<SourceIdentifier> {

    public enum Source{
        BANK,
        WECHAT,
        ALIPAY
    }

    private Source source;

    private BigDecimal money;

    private String comment;

    public void addMoney(BigDecimal money){
        this.money = this.money.add(money);
    }

    public void subMoney(BigDecimal money){
        this.money = this.money.subtract(money);
    }
}
