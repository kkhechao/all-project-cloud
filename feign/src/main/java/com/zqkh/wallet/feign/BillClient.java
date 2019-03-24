package com.zqkh.wallet.feign;

import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.feign.dto.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author hty
 * @create 2017-12-27 14:15
 **/
@FeignClient("microservice-wallet-context")
public interface BillClient {

    @GetMapping("/bill/withdrawBillExport")
    List<WithdrawBillExportDto> withdrawBillExport(@RequestParam(value = "cardNumber", required = false) String cardNumber,
                                                   @RequestParam(value = "billId", required = false) String billId,
                                                   @RequestParam(value = "startTime", required = false) String startTime,
                                                   @RequestParam(value = "endTime", required = false) String endTime,
                                                   @RequestParam(value = "status", required = false) String status,
                                                   @RequestParam(value = "userId", required = false) String userId,
                                                   @RequestParam(value = "billIds",required = false) List<String> billIds);

    @GetMapping("/bill/publicWithdrawBillExport")
    List<PublicWithdrawBillExportDto> publicWithdrawBillExport(@RequestParam(value = "cardNumber", required = false) String cardNumber,
                                                   @RequestParam(value = "billId", required = false) String billId,
                                                   @RequestParam(value = "startTime", required = false) String startTime,
                                                   @RequestParam(value = "endTime", required = false) String endTime,
                                                   @RequestParam(value = "status", required = false) String status,
                                                   @RequestParam(value = "companyName", required = false) String companyName,
                                                   @RequestParam(value = "billIds",required = false) List<String> billIds);


    @PostMapping("/bill/createWithdrawBill")
    WithdrawResultDto createWithDrawBill(@RequestBody WithdrawBillDto withdrawBillDto);

    @PostMapping("/bill/createRechargeBill")
    void createRechargeBill(@RequestBody RechargeBillDto rechargeBillDto);

    @GetMapping("/bill/getBillList")
    PageResult<BillDto> getBillList(@RequestParam("userId") String userId, @RequestParam("pageIndex") int pageIndex,
                                    @RequestParam("pageSize") int pageSize);

    @GetMapping("/bill/searchWithdrawBill")
    PageResult<WithdrawBillDetailDto> searchWithdrawBill(
            @RequestParam(value = "cardNumber", required = false) String cardNumber,
            @RequestParam(value = "billId", required = false) String billId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "withdrawBankCardUserName", required = false) String withdrawBankCardUserName,
            @RequestParam(value = "withdrawType") String withdrawType,
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize);

    @GetMapping("/bill/getRechargeBillDetail")
    RechargeBillDetailDto getRechargeBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId);

    @GetMapping("/bill/getRewardBillDetail")
    RewardBillDetailDto getRewardBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId);

    @GetMapping("/bill/getWithdrawBillDetail")
    WithdrawBillDetailDto getWithdrawBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId);

    @GetMapping("/bill/getConsumptionBillDetail")
    ConsumptionBillDetailDto getConsumptionBillDetail(@RequestParam("userId") String userId, @RequestParam("billId") String billId);

    @GetMapping("/bill/ProcessRechargeBillSuccessCallBack")
    void processRechargeBillSuccessCallBack(@RequestParam("billNumber") String billNumber, @RequestParam("responseParam") String responseParam);

    @PostMapping("/bill/createBalanceConsumptionBill")
    void createBalanceConsumptionBill(@RequestBody @Valid RechargeBillDto rechargeBillDto);

    @GetMapping("/bill/getBillStatus")
    Map<String, String> getBillStatus(@RequestParam("billNumber") String billNumber);

    @GetMapping("/bill/generateBillId")
    String generateBillId();

    @RequestMapping(value = "/bill/withdrawBillImport",method = RequestMethod.POST)
    void withdrawBillImport(@RequestBody WithdrawBillImportDto withdrawBillImportDto);

    @GetMapping("/bill/changePublicWithdrawBillInvoiceStatus")
    void changePublicWithdrawBillInvoiceStatus(@RequestParam("billId") String billId);

    @PostMapping("/bill/checkWithdrawBill")
    void checkWithdrawBill(@RequestBody WithdrawBillDetailDto withdrawBillDetailDto);

    @PostMapping("/bill/finishWithdrawBill")
    void finishWithdrawBill(@RequestBody WithdrawBillDetailDto withdrawBillDetailDto);
}
