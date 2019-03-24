package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-02 19:12
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawBillDetailDto {

    private UserInfoDto userInfoDto;

    private String userId;

    private String billId;

    private String title;

    private Date expectTime;

    private Date createTime;

    private WithdrawBankInfoDto withdrawBankInfo;

    private Date processTime;

    private Date finishTime;

    public enum Status{
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

    public enum  InvoiceStatus{
        RECEIVED,
        UNRECEIVED
    }

    private InvoiceStatus invoiceStatus;

    private List<FundingDetailDto> fundingDetails;

}
