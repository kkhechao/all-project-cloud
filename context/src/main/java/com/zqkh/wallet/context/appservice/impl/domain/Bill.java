package com.zqkh.wallet.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import com.zqkh.wallet.event.dto.FundingDetailEventDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/** 账单实体
 * @author hty
 * @create 2017-12-25 10:55
 **/
@Getter
@ToString
public abstract class Bill extends EntityObject {

    /**
     * 账单发起人
     */
    protected String userId;


    /**
     * 明细列表
     */
    protected List<FundingDetail> fundingDetails;

    /**
     * 创建时间
     */
    protected LocalDateTime createTime;

    public enum Type{
        RECHARGE,
        CONSUMPTION,
        WITHDRAW,
        REWARD
    }

    protected Type type;

    protected void getBillFunding(List<FundingDetail> fundingDetailList, FundingDetailEventDto p) {
        FundingDetail fundingDetail = new FundingDetail();
        fundingDetail.setRole(FundingDetail.Role.valueOf(p.getRoleType().toString()));
        fundingDetail.setType(FundingDetail.Type.valueOf(p.getType().toString()));
        fundingDetail.setDirection(FundingDetail.Direction.valueOf(p.getDirection().toString()));
        fundingDetail.setAccountId(p.getAccountId());
        fundingDetail.setAmount(p.getAmount());
        fundingDetail.setTitle(p.getTitle());
        fundingDetailList.add(fundingDetail);
    }
}
