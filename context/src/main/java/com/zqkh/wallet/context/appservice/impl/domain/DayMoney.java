package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author wenjie
 * @date 2018/1/2 0002 18:26
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DayMoney extends EntityObject {

    public enum Source{
        BANK,
        WECHAT,
        ALIPAY
    }

    private LocalDateTime day;

    private Source source;

    private String money;

    private String yesterday;

    public void init(LocalDateTime day, String source, String money, String yesterday){
        this.day = day;
        this.source = Source.valueOf(source);
        this.money = money;
        this.yesterday = yesterday;
    }
}
