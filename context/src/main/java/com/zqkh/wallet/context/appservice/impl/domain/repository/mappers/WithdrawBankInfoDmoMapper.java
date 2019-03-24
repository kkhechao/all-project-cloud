package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.WithdrawBankInfoDmo;

public interface WithdrawBankInfoDmoMapper {
    int insert(WithdrawBankInfoDmo record);

    int insertSelective(WithdrawBankInfoDmo record);
}