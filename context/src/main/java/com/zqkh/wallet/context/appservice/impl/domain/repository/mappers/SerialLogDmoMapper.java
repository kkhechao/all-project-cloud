package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.SerialLogDmo;

public interface SerialLogDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SerialLogDmo record);

    int insertSelective(SerialLogDmo record);

    SerialLogDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SerialLogDmo record);

    int updateByPrimaryKey(SerialLogDmo record);
}