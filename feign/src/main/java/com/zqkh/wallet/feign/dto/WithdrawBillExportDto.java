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
public class WithdrawBillExportDto {

    @Excel(name = "账单编号", orderNum = "0")
    private String billId;

    @Excel(name = "姓名", orderNum = "1")
    private String name;

    @Excel(name = "身份证号", orderNum = "2")
    private String idCardNumber;

    @Excel(name = "用户ID", orderNum = "3")
    private String userId;

    @Excel(name = "银行名称", orderNum = "4")
    private String bankName;

    @Excel(name = "开户行", orderNum = "5")
    private String bankAddress;

    @Excel(name = "银行卡号", orderNum = "6")
    private String cardNumber;

    @Excel(name = "应付劳务报酬", orderNum = "7")
    private BigDecimal payableAmount;

    private BigDecimal withdrawAmount;

    private BigDecimal fee;

    @Excel(name = "个税", orderNum = "8")
    private BigDecimal tax;

    @Excel(name = "实付工资", orderNum = "9")
    private BigDecimal remittanceAmount;

    @Excel(name = "状态（成功:PROCESS_SUCCESS 失败:PROCESS_FAIL）", orderNum = "10")
    private String status;

    @Excel(name = "备注", orderNum = "11")
    private String remark;

}
