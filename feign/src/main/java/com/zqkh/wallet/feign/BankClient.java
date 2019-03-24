package com.zqkh.wallet.feign;

import com.zqkh.wallet.feign.dto.BankCardDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wenjie
 * @date 2017/12/26 0026 10:58
 */
@FeignClient("microservice-wallet-context")
public interface BankClient {

    /**
     * 添加银行卡
     * @param bankCardDto
     * @param accountId
     */
    @PostMapping("/wallet/addBank")
    void addBank(@RequestBody BankCardDto bankCardDto, @RequestParam("accountId") String accountId);

    /**
     * 删除银行卡
     * @param number
     */
    @PostMapping("/wallet/delBank")
    void delBank(@RequestParam("number") String number, @RequestParam("accountId") String accountId);

    /**
     * 获取银行卡列表
     * @param accountId
     */
    @GetMapping("/wallet/getBank")
    List<BankCardDto> getBanks(@RequestParam("accountId") String accountId);
}

