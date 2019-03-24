package com.zqkh.wallet.context.appservice.impl.domain.repository.query;

import org.apache.ibatis.annotations.Param;

/**
 * @author hty
 * @create 2018-01-02 16:29
 **/

public interface BankCardQuery {

    String selectBankCard(@Param("accountId")String accountId, @Param("number")String number);
}
