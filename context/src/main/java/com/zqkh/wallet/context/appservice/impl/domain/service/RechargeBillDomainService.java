package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.zqkh.wallet.context.appservice.common.Constants;
import com.zqkh.wallet.context.appservice.impl.domain.RechargeBill;
import com.zqkh.wallet.context.appservice.impl.domain.service.dto.FundingDetailDto;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author hty
 * @create 2018-01-16 10:56
 **/
@Service
public class RechargeBillDomainService {

    @Autowired
    RechargeDomainService rechargeDomainService;

    @Autowired
    DozerBeanMapper dozerBeanMapper;

    public void RechargeBillCallBack(RechargeBill rechargeBill,String responseParam){
        rechargeBill.changePaidTime();
        rechargeBill.changeResponseParam(responseParam);
        rechargeBill.changeStatus();
        rechargeDomainService.recharge(Constants.Source.valueOf(rechargeBill.getRechargeChannel().toString()), rechargeBill.getId().toValue(), rechargeBill.getFundingDetails().
                stream().map(p -> dozerBeanMapper.map(p, FundingDetailDto.class)).collect(Collectors.toList()));
    }

}
