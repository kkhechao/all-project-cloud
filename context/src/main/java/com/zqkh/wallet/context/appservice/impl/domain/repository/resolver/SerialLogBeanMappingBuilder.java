package com.zqkh.wallet.context.appservice.impl.domain.repository.resolver;

import com.zqkh.wallet.context.appservice.impl.domain.SerialLog;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.SerialLogDmo;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2017/11/27 0027 15:15
 */
@Component
public class SerialLogBeanMappingBuilder extends BeanMappingBuilder {


    @Override
    protected void configure() {
        mapping(type(SerialLog.class).accessible(true), SerialLogDmo.class)
                .fields("billNo", "billNo")
                .fields("money", "money")
                .fields("direction", "direction")
                .fields("account", "accountId")
                .fields("createTime", "createTime")
                .fields("comment", "comment")
                .fields("type", "type")
        ;
    }
}
