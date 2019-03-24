package com.zqkh.wallet.context.appservice.impl.domain;

import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.event.dto.ConsumptionBillEventDto;
import com.zqkh.wallet.event.dto.FundingDetailEventDto;
import com.zqkh.wallet.event.dto.MemberAccountDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hty
 * @create 2018-01-01 10:34
 **/
@Getter
public class AgentBill extends ConsumptionBill {

    public static final String USER = "USER";
    public static final String INTRODUCER = "INTRODUCER";

}
