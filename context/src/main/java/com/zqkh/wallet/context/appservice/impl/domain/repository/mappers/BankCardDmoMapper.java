package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.BankCardDmo;

public interface BankCardDmoMapper {
    int insert(BankCardDmo record);

    int insertSelective(BankCardDmo record);
}