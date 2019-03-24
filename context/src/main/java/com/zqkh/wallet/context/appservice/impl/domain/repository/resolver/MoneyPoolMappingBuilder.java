package com.zqkh.wallet.context.appservice.impl.domain.repository.resolver;

import com.zqkh.wallet.context.appservice.impl.domain.MoneyPool;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.MoneyPoolDmo;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2018/1/2 0002 18:51
 */
@Component
public class MoneyPoolMappingBuilder extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(type(MoneyPool.class).accessible(true), MoneyPoolDmo.class)
                .fields("source", "source")
                .fields("money", "money")
                .fields("comment", "comment")
        ;
    }
}
