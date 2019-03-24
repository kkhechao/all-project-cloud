package com.zqkh.wallet.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author hty
 * @create 2018-01-05 10:09
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawBillSuccessEventDto  implements Serializable {

    public static final String EVENT_NAME = "WITHDRAW_BILL_SUCCESS";

    private String billId;
}
