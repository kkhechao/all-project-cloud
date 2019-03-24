package com.zqkh.wallet.feign;

import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.feign.dto.SerialLogDto;
import com.zqkh.wallet.feign.dto.SerialLogRequest;
import com.zqkh.wallet.feign.dto.WalletInfoDto;
import com.zqkh.wallet.feign.dto.WithdrawInfoDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("microservice-wallet-context")
public interface WalletClient {

    /**
     * 提现
     * @param accountId
     */
    @GetMapping("/wallet/withdraw")
    WithdrawInfoDto withdraw(@RequestParam("userId") String userId,@RequestParam("accountId") String accountId);


    /**
     * 我的钱包
     * @param accountId
     * @return
     */
    @GetMapping("/wallet/")
    WalletInfoDto wallet(@RequestParam("accountId") String accountId);

    @GetMapping("/wallet/integral")
    PageResult<SerialLogDto> integral(@RequestBody SerialLogRequest serialLogRequest);
}