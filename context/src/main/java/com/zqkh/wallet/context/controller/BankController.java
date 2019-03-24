package com.zqkh.wallet.context.controller;

import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.context.appservice.inter.AccountService;
import com.zqkh.wallet.feign.BankClient;
import com.zqkh.wallet.feign.dto.BankCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenjie
 * @date 2017/12/26 0026 10:58
 */
@RestController
public class BankController implements BankClient{

    @Autowired
    private AccountService accountService;

    @Override
    public void addBank(@RequestBody BankCardDto bankCardDto, @RequestParam("accountId") String accountId) throws BusinessException {
        accountService.addBankCard(bankCardDto, accountId);
    }

    @Override
    public void delBank(@RequestParam("number") String number, @RequestParam("accountId") String accountId) throws BusinessException {
        accountService.removeBankCard(number, accountId);
    }

    @Override
    public List<BankCardDto> getBanks(@RequestParam("accountId") String accountId){
        return accountService.getBanks(accountId);
    }

}
