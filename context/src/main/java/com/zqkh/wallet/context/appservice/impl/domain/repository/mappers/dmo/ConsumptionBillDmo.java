package com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo;

public class ConsumptionBillDmo {
    private String id;

    private String status;

    private String businessOrderId;

    private String orderId;

    private String rechargeBillNumber;

    private String consumptionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBusinessOrderId() {
        return businessOrderId;
    }

    public void setBusinessOrderId(String businessOrderId) {
        this.businessOrderId = businessOrderId == null ? null : businessOrderId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getRechargeBillNumber() {
        return rechargeBillNumber;
    }

    public void setRechargeBillNumber(String rechargeBillNumber) {
        this.rechargeBillNumber = rechargeBillNumber == null ? null : rechargeBillNumber.trim();
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType == null ? null : consumptionType.trim();
    }
}