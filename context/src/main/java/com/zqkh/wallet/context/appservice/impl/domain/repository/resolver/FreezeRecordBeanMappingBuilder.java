package com.zqkh.wallet.context.appservice.impl.domain.repository.resolver;

import com.zqkh.wallet.context.appservice.impl.domain.FreezeRecord;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.FreezeRecordDmo;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2017/11/27 0027 15:15
 */
@Component
public class FreezeRecordBeanMappingBuilder extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(type(FreezeRecord.class).accessible(true), FreezeRecordDmo.class)
                .fields("type", "type")
                .fields("money", "money")
                .fields("user", "userId")
                .fields("createTime", "createTime")
                .fields("comment", "comment")
        ;
    }
}
