package com.zqkh.wallet.context.appservice.inter;

import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.event.dto.*;
import com.zqkh.wallet.feign.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @author hty
 * @create 2017-12-26 14:54
 **/

public interface BillService {

    WithdrawResultDto createWithDrawBill(WithdrawBillDto withdrawBillDto);

    void createRechargeBill(RechargeBillDto rechargeBillDto);

    PageResult<BillDto> getBillList(String userId, int pageIndex, int pageSize);

    RechargeBillDetailDto getRechargeBillDetail(String userId, String billId);

    WithdrawBillDetailDto getWithdrawBillDetail(String userId, String billId);

    ConsumptionBillDetailDto getConsumptionBillDetail(String userId, String billId);

    void createRewardBill(RewardBillEventDto rewardBillEventDto);

    void processRechargeBillSuccessCallBack(String attach, String responseParam);

    Map<String, String> getBillStatus(String billNumber);

    String generateBillId();

    void createBalanceConsumptionBill(RechargeBillDto rechargeBillDto);

    void createConsumptionBill(ConsumptionBillEventDto consumptionBillEventDto);

    RewardBillDetailDto getRewardBillDetail(String userId, String billId);

    PageResult<WithdrawBillDetailDto> searchWithdrawBill(String cardNumber, String billId, String startTime,String endTime, String status,String userId,String withdrawBankCardUserName,String withdrawType, int pageIndex, int pageSize);

    List<WithdrawBillExportDto> withdrawBillExport(String cardNumber, String billId, String startTime, String endTime, String status,String userId,List<String> billIds);

    void withdrawBillImport(WithdrawBillImportDto withdrawBillImportDto);

    List<PublicWithdrawBillExportDto> publicWithdrawBillExport(String cardNumber, String billId, String startTime, String endTime, String status, String companyName, List<String> billIds);

    void changePublicWithdrawBillInvoiceStatus(String billId);

    void checkWithdrawBill(WithdrawBillDetailDto withdrawBillDetailDto);

    void finishWithdrawBill(WithdrawBillDetailDto withdrawBillDetailDto);
}
