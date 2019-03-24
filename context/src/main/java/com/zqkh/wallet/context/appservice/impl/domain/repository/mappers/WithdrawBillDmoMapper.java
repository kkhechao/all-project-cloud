package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.WithdrawBillDmo;

public interface WithdrawBillDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WithdrawBillDmo record);

    int insertSelective(WithdrawBillDmo record);

    WithdrawBillDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WithdrawBillDmo record);

    int updateByPrimaryKey(WithdrawBillDmo record);
}