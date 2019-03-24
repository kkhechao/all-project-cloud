package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.RechargeBillDmo;

public interface RechargeBillDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(RechargeBillDmo record);

    int insertSelective(RechargeBillDmo record);

    RechargeBillDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RechargeBillDmo record);

    int updateByPrimaryKey(RechargeBillDmo record);
}