package com.zqkh.wallet.context.appservice.impl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 充值账单
 *
 * @author hty
 * @create 2017-12-25 11:46
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RechargeBill extends Bill {

    public void createFundingDetail(String userAccountId, BigDecimal amount, String platAccount,BigDecimal bankPercent) {
         FundingDetail userAccountRechargeFundingDetail = new FundingDetail();
        userAccountRechargeFundingDetail.setTitle("充值");
        userAccountRechargeFundingDetail.setAmount(amount);
        userAccountRechargeFundingDetail.setType(FundingDetail.Type.RMB);
        userAccountRechargeFundingDetail.setRole(FundingDetail.Role.USER);
        userAccountRechargeFundingDetail.setAccountId(userAccountId);
        userAccountRechargeFundingDetail.setDirection(FundingDetail.Direction.IN);

        FundingDetail platAccountBearFeeFundingDetail = new FundingDetail();
        platAccountBearFeeFundingDetail.setTitle("平台承担用户充值手续费");
        platAccountBearFeeFundingDetail.setAmount(amount.multiply(bankPercent));
        platAccountBearFeeFundingDetail.setType(FundingDetail.Type.RMB);
        platAccountBearFeeFundingDetail.setRole(FundingDetail.Role.PLATFORM);
        platAccountBearFeeFundingDetail.setAccountId(platAccount);
        platAccountBearFeeFundingDetail.setDirection(FundingDetail.Direction.OUT);

        this.fundingDetails = Arrays.asList(userAccountRechargeFundingDetail,platAccountBearFeeFundingDetail);
    }

    public void changePaidTime() {
        this.paidTime = LocalDateTime.now();
    }

    public void changeResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public void changeStatus() {
        this.status = Status.PAID;
    }

    public enum RechargeChannel {
        WECHAT,
        ALIPAY,
        BALANCE,
        BANK
    }

    public enum Status {
        PAID,
        UNPAID
    }

    /**
     * 充值渠道
     */
    private RechargeChannel rechargeChannel;

    /**
     * 渠道流水号
     */
    private String channelSerial;

    /**
     * 充值状态
     */
    private Status status;

    /**
     * 金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 参数备注
     */
    private String requestParam;

    private String responseParam;

    private LocalDateTime paidTime;

    private BigDecimal bearFee;

    private String orderNumber;

    public enum RechargeType{
        RECHARGE,
        VIP_RECHARGE,
        USER_ACTIVE_RECHARGE,
        AGENT_RECHARGE,
        MALL_CONSUMPTION_RECHARGE
    }

    private RechargeType rechargeType;

    private BigDecimal orderAmount;

    public void init(String userId, LocalDateTime
            createTime, String rechargeChannel, BigDecimal rechargeAmount, String requestParam,
                     String channelSerial,String rechargeType,String orderNumber,BigDecimal orderAmount) {
        this.userId = userId;
        this.createTime = createTime;
        this.rechargeChannel = RechargeChannel.valueOf(rechargeChannel);
        this.status = Status.UNPAID;
        this.rechargeAmount = rechargeAmount;
        this.requestParam = requestParam;
        this.channelSerial = channelSerial;
        this.type = Type.RECHARGE;
        this.rechargeType = RechargeType.valueOf(rechargeType);
        this.orderNumber = orderNumber;
        this.orderAmount = orderAmount;
    }

}
