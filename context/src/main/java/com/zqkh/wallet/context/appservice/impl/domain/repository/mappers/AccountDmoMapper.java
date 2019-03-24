package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.AccountDmo;

public interface AccountDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AccountDmo record);

    int insertSelective(AccountDmo record);

    AccountDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccountDmo record);

    int updateByPrimaryKey(AccountDmo record);
}