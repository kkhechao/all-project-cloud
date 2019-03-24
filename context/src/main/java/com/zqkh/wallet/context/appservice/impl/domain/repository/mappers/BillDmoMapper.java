package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.BillDmo;

public interface BillDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(BillDmo record);

    int insertSelective(BillDmo record);

    BillDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BillDmo record);

    int updateByPrimaryKey(BillDmo record);
}