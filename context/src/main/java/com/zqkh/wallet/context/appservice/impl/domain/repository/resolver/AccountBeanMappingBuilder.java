package com.zqkh.wallet.context.appservice.impl.domain.repository.resolver;

import com.zqkh.wallet.context.appservice.impl.domain.Account;
import com.zqkh.wallet.context.appservice.impl.domain.BankCard;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.AccountDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.BankCardDmo;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2017/11/27 0027 9:52
 */
@Component
public class AccountBeanMappingBuilder extends BeanMappingBuilder {

    @Override
    protected void configure() {
        mapping(type(Account.class).accessible(true), AccountDmo.class)
                .fields("consumptionAccount.availableAmount", "consumptionAvailableMoney")
                .fields("consumptionAccount.freezeAmount", "consumptionFreezeMoney")
                .fields("withdrawAccount.availableAmount", "withdrawAvailableMoney")
                .fields("withdrawAccount.freezeAmount", "withdrawFreezeMoney")
                .fields("integralAccount.availableAmount", "integralAvailableMoney")
                .fields("integralAccount.freezeAmount", "integralFreezeMoney")
        ;

        mapping(type(BankCard.class).accessible(true), BankCardDmo.class)
                .fields("code", "code")
                .fields("number", "cardNo")
                .fields("defaultFlag", "defaultFlag")
                .fields("name", "userName")
                .fields("type", "type")
                .fields("bankName", "bankName")
                .fields("cardType", "cardType")
                .fields("bankAddress", "bankAddress")
        ;
    }
}
