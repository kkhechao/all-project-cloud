package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.FreezeRecordDmo;

public interface FreezeRecordDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(FreezeRecordDmo record);

    int insertSelective(FreezeRecordDmo record);

    FreezeRecordDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeRecordDmo record);

    int updateByPrimaryKey(FreezeRecordDmo record);
}