package com.zqkh.wallet.context.appservice.impl.domain.repository.model;

import com.jovezhao.nest.ddd.EntityObject;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.EntityObjectUtils;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.wallet.context.appservice.impl.domain.*;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.*;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.*;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext.BankCardDmoDmlMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext.FundingDetailDmoDmlMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.ext.WithdrawBankInfoDmoDmlMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.query.FundingDetailQuery;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hty
 * @create 2017-12-25 17:27
 **/
@Repository("Bill_Repository")
public class BillRepository implements IRepository<Bill> {

    @Autowired
    BillDmoMapper billDmoMapper;

    @Autowired
    RechargeBillDmoMapper rechargeBillDmoMapper;

    @Autowired
    WithdrawBillDmoMapper withdrawBillDmoMapper;

    @Autowired
    WithdrawBankInfoDmoMapper withdrawBankInfoDmoMapper;

    @Autowired
    WithdrawBankInfoDmoDmlMapper withdrawBankInfoDmoDmlMapper;

    @Autowired
    ConsumptionBillDmoMapper consumptionBillDmoMapper;

    @Autowired
    FundingDetailQuery fundingDetailQuery;

    @Autowired
    FundingDetailDmoDmlMapper fundingDetailDmoDmlMapper;

    @Autowired
    DozerBeanMapper dozerBeanMapper;

    @Override
    public Bill getEntityById(Identifier identifier, EntityLoader<Bill> entityLoader) {
        BillDmo billDmo = billDmoMapper.selectByPrimaryKey(identifier.toValue());
        if (billDmo == null) {
            return null;
        }
        Bill bill = null;
        String idValue = identifier.toValue();
        String billType = billDmo.getType();
        //充值
        if (Bill.Type.RECHARGE.toString().equals(billType)) {
            RechargeBill rechargeBill = entityLoader.create(RechargeBill.class, identifier);
            RechargeBillDmo rechargeBillDmo = rechargeBillDmoMapper.selectByPrimaryKey(idValue);
            if (rechargeBillDmo != null) {
                dozerBeanMapper.map(rechargeBillDmo, rechargeBill);
                bill = rechargeBill;
            }
            //提现
        } else if (Bill.Type.WITHDRAW.toString().equals(billType)) {
            WithdrawBill withdrawBill = entityLoader.create(WithdrawBill.class, identifier);
            WithdrawBillDmo withdrawBillDmo = withdrawBillDmoMapper.selectByPrimaryKey(idValue);
            if (withdrawBillDmo != null) {
                dozerBeanMapper.map(withdrawBillDmo, withdrawBill);
                WithdrawBankInfoDmo withdrawBankInfoDmo = withdrawBankInfoDmoDmlMapper.selectByBillId(withdrawBill.getId().toValue());
                EntityObjectUtils.setValue(WithdrawBill.class,withdrawBill,"withdrawBankInfo",dozerBeanMapper.map(withdrawBankInfoDmo,WithdrawBankInfo.class));
                bill = withdrawBill;
            }
            //消费
        } else if (Bill.Type.CONSUMPTION.toString().equals(billType)) {
            ConsumptionBill consumptionBill = null;
            ConsumptionBillDmo consumptionBillDmo = consumptionBillDmoMapper.selectByPrimaryKey(idValue);
            if (consumptionBillDmo != null) {
                if(ConsumptionBill.ConsumptionType.UP_TO_VIP.toString().equals(consumptionBillDmo.getConsumptionType())){
                    consumptionBill=entityLoader.create(VipBill.class, identifier);
                }else if(ConsumptionBill.ConsumptionType.MALL_CONSUMPTION.toString().equals(consumptionBillDmo.getConsumptionType())){
                    consumptionBill=entityLoader.create(MallConsumptionBill.class, identifier);
                }else if(ConsumptionBill.ConsumptionType.UP_TO_AGENT.toString().equals(consumptionBillDmo.getConsumptionType())){
                    consumptionBill=entityLoader.create(AgentBill.class, identifier);
                }else if(ConsumptionBill.ConsumptionType.PURCHASE.toString().equals(consumptionBillDmo.getConsumptionType())){
                    consumptionBill = entityLoader.create(PurchaseBill.class,identifier);
                }else{
                    consumptionBill=entityLoader.create(UserActiveBill.class, identifier);
                }
                dozerBeanMapper.map(consumptionBillDmo, consumptionBill);
                bill = consumptionBill;
            }
        }else {
            RewardBill rewardBill = entityLoader.create(RewardBill.class,identifier);
            bill = rewardBill;
        }
        dozerBeanMapper.map(billDmo, bill);
        List<FundingDetailDmo> fundingDetailDmos = fundingDetailQuery.selectByBillId(idValue);
        EntityObjectUtils.setValue(Bill.class, bill, "fundingDetails", fundingDetailDmos.stream().map(p -> dozerBeanMapper.map(p, FundingDetail.class)).collect(Collectors.toList()));
        return bill;

    }

    @Override
    public void save(Bill bill) {
        if (bill != null) {
            BillDmo billDmo = dozerBeanMapper.map(bill, BillDmo.class);
            if (billDmoMapper.updateByPrimaryKey(billDmo) == 0) {
                billDmoMapper.insert(billDmo);
            }

            String billId = bill.getId().toValue();
            List<FundingDetail> fundingDetails = bill.getFundingDetails();
            if (fundingDetails != null && !fundingDetails.isEmpty()) {
                fundingDetailDmoDmlMapper.deleteByBillId(billId);
                List<FundingDetailDmo> fundingDetailDmos = fundingDetails.stream().map(p -> dozerBeanMapper.map(p, FundingDetailDmo.class)).collect(Collectors.toList());
                fundingDetailDmoDmlMapper.batchInsert(billId, fundingDetailDmos);
            }
            String billType = bill.getType().toString();
            //充值账单
            if (Bill.Type.RECHARGE.toString().equals(billType)) {
                RechargeBillDmo rechargeBillDmo = dozerBeanMapper.map(bill, RechargeBillDmo.class);
                if (rechargeBillDmoMapper.updateByPrimaryKey(rechargeBillDmo) == 0) {
                    rechargeBillDmoMapper.insert(rechargeBillDmo);
                }
                //提现账单
            } else if (Bill.Type.WITHDRAW.toString().equals(billType)) {
                WithdrawBill withdrawBill = (WithdrawBill)bill;
                WithdrawBillDmo withdrawBillDmo = dozerBeanMapper.map(bill, WithdrawBillDmo.class);
                if (withdrawBillDmoMapper.updateByPrimaryKey(withdrawBillDmo) == 0) {
                    withdrawBillDmoMapper.insert(withdrawBillDmo);
                }
                WithdrawBankInfo withdrawBankInfo = withdrawBill.getWithdrawBankInfo();
                if(withdrawBankInfo!=null){
                    withdrawBankInfoDmoDmlMapper.deleteByWithdrawBillId(withdrawBill.getId().toString());
                    WithdrawBankInfoDmo withdrawBankInfoDmo = dozerBeanMapper.map(withdrawBill.getWithdrawBankInfo(), WithdrawBankInfoDmo.class);
                    withdrawBankInfoDmo.setWithdrawBillId(withdrawBill.getId().toValue());
                    withdrawBankInfoDmoMapper.insert(withdrawBankInfoDmo);
                }
            } else if (Bill.Type.CONSUMPTION.toString().equals(billType)) {
                ConsumptionBillDmo consumptionBillDmo = dozerBeanMapper.map(bill, ConsumptionBillDmo.class);
                if (consumptionBillDmoMapper.updateByPrimaryKey(consumptionBillDmo) == 0) {
                    consumptionBillDmoMapper.insert(consumptionBillDmo);
                }
            }

        }

    }

    @Override
    public void remove(Bill bill) {

    }
}
