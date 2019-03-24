//package com.zqkh.wallet.context.appservice.impl;
//
//import com.zqkh.wallet.context.Application;
//import com.zqkh.wallet.context.appservice.inter.AccountService;
//import com.zqkh.wallet.feign.dto.BankCardDto;
//import com.zqkh.wallet.context.appservice.dto.WalletDto;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.math.BigDecimal;
//
///**
// * @author wenjie
// * @date 2017/12/5 0005 15:07
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class)
//public class AccountServiceImplTest {
//
//    @Autowired
//    private AccountService accountService;
//
////    @Test
////    public void recharge() throws Exception {
////        WalletDto walletDto = new WalletDto();
////        walletDto.setComment("测试充值");
////        walletDto.setId("1");
////        walletDto.setMoney(BigDecimal.valueOf(5L));
////        walletDto.setUserId("1");
////    }
////
////    @Test
////    public void consumption() throws Exception {
////        WalletDto walletDto = new WalletDto();
////        walletDto.setComment("消费测试");
////        walletDto.setId("1");
////        walletDto.setMoney(BigDecimal.valueOf(22));
////        walletDto.setUserId("1");
////    }
////
////    @Test
////    public void withdraw() throws Exception {
////    }
////
////    @Test
////    public void addWithdraw() throws Exception {
////    }
////
////    @Test
////    public void addBankCard() throws Exception {
////        BankCardDto bankCardDto = new BankCardDto();
////        bankCardDto.setBank("中国工商银行");
////        bankCardDto.setDefaultFlag(true);
////        bankCardDto.setName("文杰");
////        bankCardDto.setNumber("1234567890");
////        accountService.addBankCard(bankCardDto, "1");
////    }
////
////    @Test
////    public void removeBankCard() throws Exception {
////    }
////
////    @Test
////    public void freeze() throws Exception {
////    }
////
////    @Test
////    public void addIntegral() throws Exception{
////        WalletDto walletDto = new WalletDto();
////        walletDto.setComment("积分增加测试");
////        walletDto.setId("1");
////        walletDto.setMoney(BigDecimal.valueOf(5L));
////        walletDto.setUserId("1");
////        accountService.addIntegral(walletDto);
////    }
//}