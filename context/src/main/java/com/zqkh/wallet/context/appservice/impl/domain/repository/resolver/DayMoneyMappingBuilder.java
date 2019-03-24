package com.zqkh.wallet.context.appservice.impl.domain.repository.resolver;

import com.zqkh.wallet.context.appservice.impl.domain.DayMoney;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.DayMoneyDmo;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2018/1/2 0002 18:51
 */
@Component
public class DayMoneyMappingBuilder extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(type(DayMoney.class).accessible(true), DayMoneyDmo.class)
                .fields("day", "day")
                .fields("source", "source")
                .fields("money", "money")
                .fields("yesterday", "yesterday")
        ;
    }
}
