package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.FundingDetailDmo;

public interface FundingDetailDmoMapper {
    int insert(FundingDetailDmo record);

    int insertSelective(FundingDetailDmo record);
}