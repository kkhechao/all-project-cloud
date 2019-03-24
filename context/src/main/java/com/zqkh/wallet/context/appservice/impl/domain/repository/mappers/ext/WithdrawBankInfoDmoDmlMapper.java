package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.WithdrawBankInfoDmo;

/**
 * @author hty
 * @create 2018-03-29 15:20
 **/

public interface WithdrawBankInfoDmoDmlMapper {
    void deleteByWithdrawBillId(String billId);

    WithdrawBankInfoDmo selectByBillId(String billId);

}
