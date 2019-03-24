package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.DayMoneyDmo;

public interface DayMoneyDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(DayMoneyDmo record);

    int insertSelective(DayMoneyDmo record);

    DayMoneyDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DayMoneyDmo record);

    int updateByPrimaryKey(DayMoneyDmo record);
}