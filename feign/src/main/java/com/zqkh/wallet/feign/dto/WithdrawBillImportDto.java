package com.zqkh.wallet.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author hty
 * @create 2018-04-04 11:51
 **/
@Getter
@Setter
@NoArgsConstructor
public class WithdrawBillImportDto {
    List<WithdrawBillExportDto> withdrawBillExportDtos;

    List<PublicWithdrawBillExportDto> publicWithdrawBillImportDto;

    public WithdrawBillImportDto(List<WithdrawBillExportDto> withdrawBillExportDtos, List<PublicWithdrawBillExportDto> publicWithdrawBillImportDto) {
        this.withdrawBillExportDtos = withdrawBillExportDtos;
        this.publicWithdrawBillImportDto = publicWithdrawBillImportDto;
    }
}
