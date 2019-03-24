package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.MoneyPoolDmo;

public interface MoneyPoolDmoMapper {
    int deleteByPrimaryKey(String source);

    int insert(MoneyPoolDmo record);

    int insertSelective(MoneyPoolDmo record);

    MoneyPoolDmo selectByPrimaryKey(String source);

    int updateByPrimaryKeySelective(MoneyPoolDmo record);

    int updateByPrimaryKey(MoneyPoolDmo record);
}