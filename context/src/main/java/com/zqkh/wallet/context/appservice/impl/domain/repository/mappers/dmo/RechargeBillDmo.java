package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeBillDmo {
    private String id;

    private String rechargeChannel;

    private BigDecimal rechargeAmount;

    private String requestParam;

    private String responseParam;

    private String channelSerial;

    private String status;

    private Date paidTime;

    private BigDecimal bearFee;

    private String orderNumber;

    private String rechargeType;

    private BigDecimal orderAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRechargeChannel() {
        return rechargeChannel;
    }

    public void setRechargeChannel(String rechargeChannel) {
        this.rechargeChannel = rechargeChannel == null ? null : rechargeChannel.trim();
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam == null ? null : requestParam.trim();
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam == null ? null : responseParam.trim();
    }

    public String getChannelSerial() {
        return channelSerial;
    }

    public void setChannelSerial(String channelSerial) {
        this.channelSerial = channelSerial == null ? null : channelSerial.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public BigDecimal getBearFee() {
        return bearFee;
    }

    public void setBearFee(BigDecimal bearFee) {
        this.bearFee = bearFee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType == null ? null : rechargeType.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}