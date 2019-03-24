package com.zqkh.wallet.feign.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hty
 * @create 2018-04-03 16:58
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicWithdrawBillExportDto {

    @Excel(name = "账单编号", orderNum = "0")
    private String billId;

    @Excel(name = "公司名", orderNum = "1")
    private String companyName;

    @Excel(name = "姓名", orderNum = "2")
    private String name;

    @Excel(name = "手机号码", orderNum = "3")
    private String phoneNumber;

    @Excel(name = "身份证号", orderNum = "4")
    private String idCardNumber;

    @Excel(name = "用户ID", orderNum = "5")
    private String userId;

    @Excel(name = "银行名称", orderNum = "6")
    private String bankName;

    @Excel(name = "开户行", orderNum = "7")
    private String bankAddress;

    @Excel(name = "银行卡号", orderNum = "8")
    private String cardNumber;

    @Excel(name = "税号", orderNum = "9")
    private String taxNumber;

    @Excel(name = "实际提现", orderNum = "10")
    private BigDecimal remittanceAmount;

    @Excel(name = "状态（成功:PROCESS_SUCCESS 失败:PROCESS_FAIL）", orderNum = "11")
    private String status;

    @Excel(name = "发票状态（收到:RECEIVED 未收到:UNRECEIVED）", orderNum = "12")
    private String invoiceStatus;

    @Excel(name = "备注", orderNum = "13")
    private String remark;

}
