package com.zqkh.wallet.context.appservice.impl.domain.repository.resolver;

import com.zqkh.wallet.context.appservice.impl.domain.*;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.BillDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.ConsumptionBillDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.RechargeBillDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.WithdrawBillDmo;
import com.zqkh.wallet.feign.dto.*;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 * @date 2017/11/27 0027 9:52
 */
@Component
public class BillBeanMappingBuilder extends BeanMappingBuilder {

    @Override
    protected void configure() {
        mapping(type(RechargeBill.class).accessible(true), RechargeBillDmo.class)
                .fields("id", "id")
                .fields("rechargeChannel", "rechargeChannel")
                .fields("rechargeAmount", "rechargeAmount")
                .fields("requestParam", "requestParam")
                .fields("responseParam", "responseParam")
                .fields("channelSerial", "channelSerial")
                .fields("status", "status")
                .fields("paidTime", "paidTime")
                .fields("bearFee", "bearFee")
                .fields("orderNumber", "orderNumber")
                .fields("rechargeType", "rechargeType")
                .fields("orderAmount", "orderAmount")
        ;

        mapping(type(Bill.class).accessible(true), BillDmo.class)
                .fields("userId", "userId")
                .fields("createTime", "createTime")
                .fields("type", "type");

        mapping(type(ConsumptionBill.class).accessible(true), ConsumptionBillDmo.class)
                .fields("id", "id")
                .fields("status", "status")
                .fields("businessOrderId", "businessOrderId")
                .fields("consumptionType", "consumptionType")
        ;
        mapping(type(WithdrawBill.class).accessible(true), WithdrawBillDmo.class)
                .fields("processTime", "processTime")
                .fields("finishTime", "finishTime")
                .fields("status", "status")
                .fields("fee", "fee")
                .fields("tax", "tax")
                .fields("remark", "remark")
                .fields("withdrawAmount", "withdrawAmount")
                .fields("remittanceAmount", "remittanceAmount")
                .fields("invoiceStatus", "invoiceStatus")
        ;

        mapping(type(VipBill.class).accessible(true), ConsumptionBillDmo.class)
                .fields("orderId", "orderId")
        ;

        mapping(type(ConsumptionBill.class).accessible(true), ConsumptionBillDetailDto.class)
                .fields("businessOrderId", "orderNumber")
                .fields("id", "billNumber")
        ;mapping(type(RechargeBill.class).accessible(true), RechargeBillDetailDto.class)
                .fields("id", "billNumber")
                .fields("rechargeChannel", "payWay")
        ;

        ;mapping(type(RewardBill.class).accessible(true), RewardBillDetailDto.class)
                .fields("id", "billNumber")
        ;

        mapping(type(WithdrawBill.class).accessible(true), WithdrawBillDetailDto.class)
                .fields("id", "billId")
        ;

        mapping(type(WithdrawBill.class).accessible(true), WithdrawBillExportDto.class)
                .fields("id", "billId")
                .fields("withdrawBankInfo.name", "name")
                .fields("withdrawBankInfo.bankName", "bankName")
                .fields("withdrawBankInfo.bankAddress", "bankAddress")
                .fields("withdrawBankInfo.cardNumber", "cardNumber")
        ;

        mapping(type(WithdrawBill.class).accessible(true), PublicWithdrawBillExportDto.class)
                .fields("id", "billId")
                .fields("withdrawBankInfo.name", "companyName")
                .fields("withdrawBankInfo.bankName", "bankName")
                .fields("withdrawBankInfo.bankAddress", "bankAddress")
                .fields("withdrawBankInfo.cardNumber", "cardNumber")
                .fields("withdrawBankInfo.taxNumber", "taxNumber")
        ;
    }
}
