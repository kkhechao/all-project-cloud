package com.zqkh.wallet.context.controller;

import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.context.appservice.inter.BillService;
import com.zqkh.wallet.feign.BillClient;
import com.zqkh.wallet.feign.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author hty
 * @create 2017-12-27 15:16
 **/
@RestController
public class BillController implements BillClient {

    @Autowired
    BillService billService;

    @Override
    public List<WithdrawBillExportDto> withdrawBillExport( @RequestParam(value = "cardNumber",required = false) String cardNumber,
                                                           @RequestParam(value = "billId",required = false) String billId,
                                                           @RequestParam(value = "startTime",required = false) String startTime,
                                                           @RequestParam(value = "endTime",required = false) String endTime,
                                                           @RequestParam(value = "status",required = false) String status,
                                                           @RequestParam(value = "userId",required = false) String userId,
                                                           @RequestParam(value = "billIds",required = false) List<String> billIds) {
        return billService.withdrawBillExport(cardNumber,billId,startTime,endTime,status,userId,billIds);
    }

    @Override
    public List<PublicWithdrawBillExportDto> publicWithdrawBillExport(@RequestParam(value = "cardNumber",required = false) String cardNumber,
                                                                      @RequestParam(value = "billId",required = false) String billId,
                                                                      @RequestParam(value = "startTime",required = false) String startTime,
                                                                      @RequestParam(value = "endTime",required = false) String endTime,
                                                                      @RequestParam(value = "status",required = false) String status,
                                                                      @RequestParam(value = "companyName",required = false) String companyName,
                                                                      @RequestParam(value = "billIds",required = false) List<String> billIds) {
        return  billService.publicWithdrawBillExport(cardNumber,billId,startTime,endTime,status,companyName,billIds);
    }

    @Override
    public WithdrawResultDto createWithDrawBill(@RequestBody WithdrawBillDto withdrawBillDto) {
        return billService.createWithDrawBill(withdrawBillDto);
    }


    @Override
    public void createRechargeBill(@RequestBody RechargeBillDto rechargeBillDto) {
        billService.createRechargeBill(rechargeBillDto);
    }


    @Override
    public PageResult<BillDto> getBillList(@RequestParam("userId") String userId, @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize) {
        return billService.getBillList(userId, pageIndex, pageSize);
    }

    @Override
    public PageResult<WithdrawBillDetailDto> searchWithdrawBill(
                                                                @RequestParam(value = "cardNumber",required = false) String cardNumber,
                                                                @RequestParam(value = "billId",required = false) String billId,
                                                                @RequestParam(value = "startTime",required = false) String startTime,
                                                                @RequestParam(value = "endTime",required = false) String endTime,
                                                                @RequestParam(value = "status") String status,
                                                                @RequestParam(value = "userId",required = false) String userId,
                                                                @RequestParam(value = "withdrawBankCardUserName",required = false) String withdrawBankCardUserName,
                                                                @RequestParam(value = "withdrawType") String withdrawType,
                                                                @RequestParam("pageIndex") int pageIndex,
                                                                @RequestParam("pageSize") int pageSize) {
        return billService.searchWithdrawBill(cardNumber,billId,startTime,endTime,status,userId,withdrawBankCardUserName,withdrawType,pageIndex,pageSize);
    }

    @Override
    public RechargeBillDetailDto getRechargeBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId) {
        return billService.getRechargeBillDetail(userId, billId);
    }

    @Override
    public RewardBillDetailDto getRewardBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId) {
        return billService.getRewardBillDetail(userId, billId);
    }

    @Override
    public WithdrawBillDetailDto getWithdrawBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId) {
        return billService.getWithdrawBillDetail(userId, billId);
    }

    @Override
    public ConsumptionBillDetailDto getConsumptionBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId) {
        return billService.getConsumptionBillDetail(userId, billId);
    }

    @Override
    public void processRechargeBillSuccessCallBack(String billNumber, String responseParam) {
        billService.processRechargeBillSuccessCallBack(billNumber, responseParam);
    }

    @Override
    public void createBalanceConsumptionBill(@RequestBody @Valid RechargeBillDto rechargeBillDto) {
        billService.createBalanceConsumptionBill(rechargeBillDto);
    }

    @Override
    public Map<String, String> getBillStatus(@RequestParam("billNumber") String billNumber) {
        return billService.getBillStatus(billNumber);
    }

    @Override
    public String generateBillId() {
        return billService.generateBillId();
    }

    @Override
    public void withdrawBillImport(@RequestBody WithdrawBillImportDto withdrawBillImportDto) {
        billService.withdrawBillImport(withdrawBillImportDto);
    }

    @Override
    public void changePublicWithdrawBillInvoiceStatus(@RequestParam("billId") String billId) {
        billService.changePublicWithdrawBillInvoiceStatus(billId);
    }

    @Override
    public void checkWithdrawBill(@RequestBody WithdrawBillDetailDto withdrawBillDetailDto) {
        billService.checkWithdrawBill(withdrawBillDetailDto);
    }

    @Override
    public void finishWithdrawBill(@RequestBody WithdrawBillDetailDto withdrawBillDetailDto) {
        billService.finishWithdrawBill(withdrawBillDetailDto);
    }

}
