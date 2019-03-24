package com.zqkh.wallet.context.appservice.impl.domain.repository.query;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.FundingDetailDmo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-01 12:39
 **/

public interface FundingDetailQuery {

    List<FundingDetailDmo> selectByBillId(String billId);

    BigDecimal selectBillAmountByAccountIdAndBillId(@Param("userAccountId") String userAccountId, @Param("billId") String billId);

    String selectTitleByAccountIdAndBillId(@Param("userAccountId") String userAccountId, @Param("billId") String billId);
}
