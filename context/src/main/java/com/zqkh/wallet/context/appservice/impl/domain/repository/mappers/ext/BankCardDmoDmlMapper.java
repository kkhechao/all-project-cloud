package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext;

import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.BankCardDmo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hty
 * @create 2018-03-29 15:20
 **/

public interface BankCardDmoDmlMapper {
    void deleteByAccountId(String id);

    int batchInsert(@Param("accountId") String accountId, @Param("bankCards") List<BankCardDmo> bankCardDmos);

    List<BankCardDmo> selectByAccountId(String id);

}
