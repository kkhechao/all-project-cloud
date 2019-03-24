package com.zqkh.wallet.context.appservice.impl.domain;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.zqkh.wallet.feign.dto.WithdrawBillDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提现账单
 *
 * @author hty
 * @create 2017-12-25 11:57
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawBill extends Bill {

    private WithdrawBankInfo withdrawBankInfo;

    private LocalDateTime processTime;

    private LocalDateTime finishTime;

    public void changeProcessTime() {
        this.processTime = LocalDateTime.now();
    }

    public void changeStatusToSuccess() {
        this.status = Status.PROCESS_SUCCESS;
    }

    public void changeStatusToFail(String remark) {
        this.status = Status.PROCESS_FAIL;
        this.remark = remark;
    }

    public void changeFinishTime() {
        this.finishTime = LocalDateTime.now();
    }


    public void changeInvoiceStatus() {
        if (InvoiceStatus.RECEIVED == this.invoiceStatus) {
            this.invoiceStatus = InvoiceStatus.UNRECEIVED;
        } else {
            this.invoiceStatus = InvoiceStatus.RECEIVED;
        }

    }

    public void changeBillStatus(WithdrawBillDetailDto withdrawBillDetailDto) {
        this.status = Status.valueOf(withdrawBillDetailDto.getStatus().toString());
        this.remark = withdrawBillDetailDto.getRemark();
    }

    public void changeInvoiceStatusToUnreceived() {
        this.invoiceStatus = InvoiceStatus.UNRECEIVED;
    }

    public void changeInvoiceStatusToReceived() {
        this.invoiceStatus = InvoiceStatus.RECEIVED;
    }

    public enum Status {
        WAIT_PROCESS,
        CHECK_SUCCESS,
        CHECK_FAIL,
        PROCESS_SUCCESS,
        PROCESS_FAIL
    }

    private Status status;

    private BigDecimal fee;

    private BigDecimal tax;

    private String remark;

    private BigDecimal withdrawAmount;

    private BigDecimal remittanceAmount;

    public enum InvoiceStatus {
        RECEIVED,
        UNRECEIVED
    }

    private InvoiceStatus invoiceStatus;

    public void init(String userId, LocalDateTime
            createTime, WithdrawBankInfo withdrawBankInfo, BigDecimal fee, BigDecimal tax, BigDecimal withdrawAmount, BigDecimal remittanceAmount) {
        this.userId = userId;
        this.createTime = createTime;
        this.type = Type.WITHDRAW;
        this.withdrawBankInfo = withdrawBankInfo;
        this.status = Status.WAIT_PROCESS;
        this.fee = fee;
        this.tax = tax;
        this.withdrawAmount = withdrawAmount;
        this.remittanceAmount = remittanceAmount;
        this.invoiceStatus = InvoiceStatus.UNRECEIVED;
    }

    public void createFundingDetail(String userAccountId, BigDecimal fee, BigDecimal tax, BigDecimal remittanceAmount, String platAccount) {
        FundingDetail userWithdrawFundingDetail = new FundingDetail();
        userWithdrawFundingDetail.setTitle("提现支出");
        userWithdrawFundingDetail.setAmount(remittanceAmount);
        userWithdrawFundingDetail.setType(FundingDetail.Type.RMB);
        userWithdrawFundingDetail.setRole(FundingDetail.Role.USER);
        userWithdrawFundingDetail.setAccountId(userAccountId);
        userWithdrawFundingDetail.setDirection(FundingDetail.Direction.OUT);

        FundingDetail userFeeFundingDetail = new FundingDetail();
        userFeeFundingDetail.setTitle("手续费支出");
        userFeeFundingDetail.setAmount(fee);
        userFeeFundingDetail.setType(FundingDetail.Type.RMB);
        userFeeFundingDetail.setRole(FundingDetail.Role.USER);
        userFeeFundingDetail.setAccountId(userAccountId);
        userFeeFundingDetail.setDirection(FundingDetail.Direction.OUT);

        FundingDetail userTaxFundingDetail = new FundingDetail();
        userTaxFundingDetail.setTitle("税费支出");
        userTaxFundingDetail.setAmount(tax);
        userTaxFundingDetail.setType(FundingDetail.Type.RMB);
        userTaxFundingDetail.setRole(FundingDetail.Role.USER);
        userTaxFundingDetail.setAccountId(userAccountId);
        userTaxFundingDetail.setDirection(FundingDetail.Direction.OUT);

        FundingDetail platAccountBearFeeFundingDetail = new FundingDetail();
        platAccountBearFeeFundingDetail.setTitle("平台收取手续费");
        platAccountBearFeeFundingDetail.setAmount(fee);
        platAccountBearFeeFundingDetail.setType(FundingDetail.Type.RMB);
        platAccountBearFeeFundingDetail.setRole(FundingDetail.Role.PLATFORM);
        platAccountBearFeeFundingDetail.setAccountId(platAccount);
        platAccountBearFeeFundingDetail.setDirection(FundingDetail.Direction.IN);

        this.fundingDetails = Arrays.asList(userWithdrawFundingDetail, userFeeFundingDetail, userTaxFundingDetail, platAccountBearFeeFundingDetail);
    }
}
