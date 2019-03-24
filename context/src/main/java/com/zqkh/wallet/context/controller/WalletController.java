package com.zqkh.wallet.context.controller;

import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.context.appservice.inter.AccountService;
import com.zqkh.wallet.feign.WalletClient;
import com.zqkh.wallet.feign.dto.SerialLogDto;
import com.zqkh.wallet.feign.dto.SerialLogRequest;
import com.zqkh.wallet.feign.dto.WalletInfoDto;
import com.zqkh.wallet.feign.dto.WithdrawInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenjie
 * @date 2017/11/25 0025 10:41
 */
@RestController
public class WalletController implements WalletClient{

    @Autowired
    private AccountService accountService;

    @Override
    public WithdrawInfoDto withdraw(@RequestParam("userId") String userId, @RequestParam("accountId") String accountId) {
        return accountService.withdraw(userId,accountId);
    }

    @Override
    public WalletInfoDto wallet(String accountId) {
        return accountService.getWalletInfo(accountId);
    }

    @Override
    public PageResult<SerialLogDto> integral(@RequestBody SerialLogRequest serialLogRequest) {
        return accountService.getIntegralDetail(serialLogRequest);
    }
}
