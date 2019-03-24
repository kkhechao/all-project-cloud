package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.ConsumptionBillDmo;

public interface ConsumptionBillDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ConsumptionBillDmo record);

    int insertSelective(ConsumptionBillDmo record);

    ConsumptionBillDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ConsumptionBillDmo record);

    int updateByPrimaryKey(ConsumptionBillDmo record);
}