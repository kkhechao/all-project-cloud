package com.zqkh.wallet.context.appservice.inter;


import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.context.appservice.dto.WalletDto;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.feign.dto.*;

import java.util.List;

/**
 * @author wenjie
 * @date 2017/11/25 0025 10:47
 */
public interface AccountService {

    void addBankCard(BankCardDto bankCardDto, String userId) throws BusinessException;

    void removeBankCard(String number, String userId) throws BusinessException;

    WithdrawInfoDto withdraw(String userId,String accountId) throws BusinessException;

    void addIntegral(WalletDto walletDto) throws BusinessException;

    List<BankCardDto> getBanks(String userId);

    void createWalletAccount(String accountId);

    WalletInfoDto getWalletInfo(String userId);

    PageResult<SerialLogDto> getIntegralDetail(SerialLogRequest serialLogRequest);

    void saveTodayMoneyTotal();
}
