package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.FundingDetailDmo;
import org.apache.ibatis.annotations.Param;

import java.util.List; /**
 * @author hty
 * @create 2018-01-01 12:47
 **/

public interface FundingDetailDmoDmlMapper {

    void deleteByBillId(String billId);

    void batchInsert(@Param("billId") String billId, @Param("fundingDetailDmos") List<FundingDetailDmo> fundingDetailDmos);
}
