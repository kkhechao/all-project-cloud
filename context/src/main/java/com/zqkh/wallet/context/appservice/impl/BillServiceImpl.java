package com.zqkh.wallet.context.appservice.impl;

import com.jovezhao.nest.PageList;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.ConstructLoader;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.jovezhao.nest.mybatis.PageParames;
import com.jovezhao.nest.starter.AppService;
import com.jovezhao.nest.utils.JsonUtils;
import com.zqkh.common.result.PageResult;
import com.zqkh.im.event.dto.MessageType;
import com.zqkh.im.event.dto.receiver.MessageEventDto;
import com.zqkh.member.feign.MemberClient;
import com.zqkh.member.feign.PurchaseClient;
import com.zqkh.order.feign.OrderClient;
import com.zqkh.shop.feign.ShopApplicationClient;
import com.zqkh.user.feign.OperatorClient;
import com.zqkh.user.feign.UserClient;
import com.zqkh.wallet.context.appservice.common.Constants;
import com.zqkh.wallet.context.appservice.exception.BusinessException;
import com.zqkh.wallet.context.appservice.impl.domain.*;
import com.zqkh.wallet.context.appservice.impl.domain.repository.query.BillQuery;
import com.zqkh.wallet.context.appservice.impl.domain.repository.query.FundingDetailQuery;
import com.zqkh.wallet.context.appservice.impl.domain.service.*;
import com.zqkh.wallet.context.appservice.impl.domain.service.dto.FundingDetailDto;
import com.zqkh.wallet.context.appservice.impl.domain.service.util.CheckMoneyUtil;
import com.zqkh.wallet.context.appservice.inter.BillService;
import com.zqkh.wallet.context.configuration.CloudConfigProperties;
import com.zqkh.wallet.event.dto.ConsumptionBillEventDto;
import com.zqkh.wallet.event.dto.FreezeEventDto;
import com.zqkh.wallet.event.dto.FundingDetailEventDto;
import com.zqkh.wallet.event.dto.RewardBillEventDto;
import com.zqkh.wallet.feign.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hty
 * @create 2017-12-26 14:57
 **/
@Slf4j
@AppService
public class BillServiceImpl implements BillService {

    public static final String DATA_ERROR = "数据异常";
    EntityLoader<Bill> billConstructLoader = new ConstructLoader<>(Bill.class);
    EntityLoader<Bill> billRepositoryLoader = new RepositoryLoader<Bill>(Bill.class);

    @Autowired
    DozerBeanMapper dozerBeanMapper;

    @Autowired
    PurchaseClient purchaseClient;

    @Autowired
    CloudConfigProperties cloudConfigProperties;

    @Autowired
    BillQuery billQuery;

    @Autowired
    MemberClient memberClient;

    @Autowired
    FundingDetailQuery fundingDetailQuery;

    private static final BigDecimal VIP_PRICE = new BigDecimal("699.00");

    private static final BigDecimal USER_ACTIVE_PRICE = new BigDecimal("3.99");

    @Autowired
    WithDrawDomainService withDrawDomainService;

    @Autowired
    ConsumptionDomainService consumptionDomainService;

    @Autowired
    RechargeDomainService rechargeDomainService;

    @Autowired
    RewardDomainService rewardDomainService;

    @Autowired
    UserClient userClient;

    @Autowired
    OperatorClient operatorClient;

    @Autowired
    RechargeBillDomainService rechargeBillDomainService;

    @Autowired
    FreezeDomainService freezeDomainService;

    @Autowired
    UnfrezzDomainService unfrezzDomainService;

    @Autowired
    OrderClient orderClient;

    @Autowired
    ShopApplicationClient shopApplicationClient;

    @Autowired
    QueryUsableWithdrawCountDomainService queryUsableWithdrawCountDomainService;

    @Override
    public WithdrawResultDto createWithDrawBill(WithdrawBillDto withdrawBillDto) {
        String userId = withdrawBillDto.getUserId();
        int usableWithdrawCount = queryUsableWithdrawCountDomainService.QueryUsableWithdrawCountDomainService(userId);
        if (usableWithdrawCount < 1) {
            throw new BusinessException("您已经使用完本月提现次数");
        }
        WithdrawBill withdrawBill = billConstructLoader.create(WithdrawBill.class, IdGenerator.getInstance("billIdGenerator").generate(Bill.class));
        BigDecimal fee = withdrawBillDto.getWithdrawAmount().multiply(cloudConfigProperties.getFee().getWithdrawPercent()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal tax = BigDecimal.ZERO;
        if (BankCard.Type.PRIVATE.toString().equals(withdrawBillDto.getWithdrawBankInfoDto().getType())) {
            tax = getTax(withdrawBillDto.getWithdrawAmount().subtract(fee));
        }
        BigDecimal remittanceAmount = withdrawBillDto.getWithdrawAmount().subtract(fee).subtract(tax).setScale(2, RoundingMode.HALF_EVEN);
        withdrawBill.init(userId, LocalDateTime.now(), dozerBeanMapper.map(withdrawBillDto.getWithdrawBankInfoDto(),
                WithdrawBankInfo.class), fee, tax, withdrawBillDto.getWithdrawAmount(), remittanceAmount);
        withdrawBill.createFundingDetail(userClient.getAccountIdByPeopleId(userId), fee, tax, remittanceAmount,
                cloudConfigProperties.getAccount().getAccountId());
        String accountId = userClient.getAccountIdByPeopleId(userId);
        freezeDomainService.withdrawFreeze(accountId, withdrawBill.getWithdrawAmount());
        return new WithdrawResultDto(withdrawBillDto.getWithdrawAmount().setScale(2, RoundingMode.HALF_EVEN), withdrawBillDto.getWithdrawBankInfoDto().getAccount(), new Date(), fee, tax, remittanceAmount);
    }

    private BigDecimal getTax(BigDecimal withdrawAmount) {
        BigDecimal tax;
        if (withdrawAmount.compareTo(BigDecimal.valueOf(800L)) != 1) {
            tax = BigDecimal.ZERO;
        } else if (withdrawAmount.compareTo(BigDecimal.valueOf(4000L)) != 1) {
            tax = withdrawAmount.subtract(BigDecimal.valueOf(800L)).multiply(BigDecimal.valueOf(0.2F));
        } else if (withdrawAmount.compareTo(BigDecimal.valueOf(25000L)) != 1) {
            tax = withdrawAmount.multiply(BigDecimal.valueOf(0.8F)).multiply(BigDecimal.valueOf(0.2F));
        } else if (withdrawAmount.compareTo(BigDecimal.valueOf(62500L)) != 1) {
            tax = withdrawAmount.multiply(BigDecimal.valueOf(0.8F)).multiply(BigDecimal.valueOf(0.3F)).subtract(BigDecimal.valueOf(2000L));
        } else {
            tax = withdrawAmount.multiply(BigDecimal.valueOf(0.8F)).multiply(BigDecimal.valueOf(0.4F)).subtract(BigDecimal.valueOf(7000L));
        }
        return tax.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public void createRewardBill(RewardBillEventDto rewardBillEventDto) {
        RewardBill rewardBill = billConstructLoader.create(RewardBill.class, IdGenerator.getInstance("billIdGenerator").generate(Bill.class));
        rewardBill.init(cloudConfigProperties.getAccount().getAccountId());
        rewardBillEventDto.getFundingDetailEventDtos().forEach(p -> {
            getAccountIdByRole(rewardBill.getId().toValue(),p);
        });
        rewardBill.createFundingDetail(rewardBillEventDto);
        rewardDomainService.reward(rewardBill.getId().toValue(), rewardBill.getFundingDetails().
                stream().map(p -> dozerBeanMapper.map(p, FundingDetailDto.class)).collect(Collectors.toList()));
    }

    private void getAccountIdByRole(String billId,FundingDetailEventDto p) {
        if (FundingDetailEventDto.RoleType.PLATFORM.equals(p.getRoleType())) {
            p.setAccountId(cloudConfigProperties.getAccount().getAccountId());
        } else if (FundingDetailEventDto.RoleType.SUPPLIER.equals(p.getRoleType())) {
            p.setAccountId(shopApplicationClient.getShopAccountById(p.getRoleId()));
        } else {
            p.setAccountId(userClient.getAccountIdByPeopleId(p.getRoleId()));
            if (FundingDetailEventDto.Direction.IN.equals(p.getDirection())) {
                sendImMessageEvent(billId, "达标奖励", p.getRoleId(), MessageType.REWARD_STANDARD, p.getAmount() + "元推广达标奖励已经到帐了，去看帐单");
            }
        }
    }


    private void getAccountIdByRole(String billId, ConsumptionBillEventDto consumptionBillEventDto, FundingDetailEventDto p) {
        if (FundingDetailEventDto.RoleType.PLATFORM.equals(p.getRoleType())) {
            p.setAccountId(cloudConfigProperties.getAccount().getAccountId());
        } else if (FundingDetailEventDto.RoleType.SUPPLIER.equals(p.getRoleType())) {
            p.setAccountId(shopApplicationClient.getShopAccountById(p.getRoleId()));
        } else {
            p.setAccountId(userClient.getAccountIdByPeopleId(p.getRoleId()));
            System.out.println("***************获取消费类型**************"+consumptionBillEventDto.getBillType());
            if (ConsumptionBillEventDto.billType.UP_TO_VIP.equals(consumptionBillEventDto.getBillType()) && FundingDetailEventDto.Direction.IN.equals(p.getDirection())) {
                System.out.println("***************准备发送消息**************"+consumptionBillEventDto.getBillType());
                sendImMessageEvent(billId, "邀请奖励", p.getRoleId(), MessageType.REWARD_INVITATION, p.getAmount() + "元推广奖励已经到帐了，去看帐单");
            }else if(ConsumptionBillEventDto.billType.UP_TO_AGENT.equals(consumptionBillEventDto.getBillType()) && FundingDetailEventDto.Direction.IN.equals(p.getDirection())){
                sendImMessageEvent(billId, "升级奖励", p.getRoleId(), MessageType.REWARD_UPGRADE, p.getAmount() + "元推广奖励已经到帐了，去看帐单");
            }
        }
    }

    @Override
    public void processRechargeBillSuccessCallBack(String billId, String responseParam) {
        RechargeBill rechargeBill = billRepositoryLoader.create(RechargeBill.class, StringIdentifier.valueOf(billId));
        RechargeBillDto rechargeBillDto = JsonUtils.toObj(rechargeBill.getRequestParam(), RechargeBillDto.class);
        String userId = rechargeBillDto.getUserId();
        if (RechargeBill.Status.UNPAID.equals(rechargeBill.getStatus())) {
            rechargeBillDomainService.RechargeBillCallBack(rechargeBill, responseParam);
            String accountId = userClient.getAccountIdByPeopleId(userId);
            processConsumptionBill(rechargeBillDto, accountId);
        }
    }

    @Override
    public void createBalanceConsumptionBill(RechargeBillDto rechargeBillDto) {
        String accountId = userClient.getAccountIdByPeopleId(rechargeBillDto.getUserId());
        processConsumptionBill(rechargeBillDto, accountId);
    }

    @Override
    public void createConsumptionBill(ConsumptionBillEventDto consumptionBillEventDto) {
        ConsumptionBillEventDto.billType billtype = consumptionBillEventDto.getBillType();
        if (ConsumptionBillEventDto.billType.UP_TO_VIP.equals(billtype) || ConsumptionBillEventDto.billType.PURCHASE.equals(billtype)) {
            createVipConsumptionBill(consumptionBillEventDto);
        } else {
            ConsumptionBill consumptionBill = billConstructLoader.create(PurchaseBill.class,
                    IdGenerator.getInstance("billIdGenerator").generate(Bill.class));
            String userId = consumptionBillEventDto.getUserId();
            consumptionBill.init(userId, LocalDateTime.now(), VipBill.Status.
                            SUCCESS, consumptionBillEventDto.getOrderId(), consumptionBillEventDto.getRechargeBillNumber(),
                    consumptionBillEventDto.getBillType().toString());
            consumptionBillEventDto.getFundingDetailEventDtos().forEach(p -> {
                getAccountIdByRole(consumptionBill.getId().toValue(), consumptionBillEventDto, p);
            });
            consumptionBill.createFundingDetail(consumptionBillEventDto);
            consumptionDomainService.consumption(consumptionBill.getId().toValue(), consumptionBill.getFundingDetails().
                    stream().map(p -> dozerBeanMapper.map(p, FundingDetailDto.class)).collect(Collectors.toList()));
        }
    }

    private void getAccountIdByRole(FundingDetailEventDto p) {
        if (FundingDetailEventDto.RoleType.PLATFORM.equals(p.getRoleType())) {
            p.setAccountId(cloudConfigProperties.getAccount().getAccountId());
        } else if (FundingDetailEventDto.RoleType.SUPPLIER.equals(p.getRoleType())) {
            p.setAccountId(shopApplicationClient.getShopAccountById(p.getRoleId()));
        } else {
            p.setAccountId(userClient.getAccountIdByPeopleId(p.getRoleId()));
        }
    }

    private void createVipConsumptionBill(ConsumptionBillEventDto consumptionBillEventDto) {
        VipBill vipBill = billConstructLoader.create(VipBill.class, IdGenerator.getInstance("billIdGenerator").generate(Bill.class));
        String userId = consumptionBillEventDto.getUserId();
        vipBill.init(userId, LocalDateTime.now(), VipBill.Status.
                        SUCCESS, consumptionBillEventDto.getBusinessOrderId(), consumptionBillEventDto.getRechargeBillNumber(),
                consumptionBillEventDto.getBillType().toString());
        vipBill.initOrderId(consumptionBillEventDto.getOrderId());
        consumptionBillEventDto.getFundingDetailEventDtos().forEach(p -> {
            getAccountIdByRole(vipBill.getId().toValue(), consumptionBillEventDto, p);
        });
        vipBill.createFundingDetail(consumptionBillEventDto);
        consumptionDomainService.consumption(vipBill.getId().toValue(), vipBill.getFundingDetails().
                stream().map(p -> dozerBeanMapper.map(p, FundingDetailDto.class)).collect(Collectors.toList()));
        FreezeEventDto freezeEventDto = new FreezeEventDto();
        freezeEventDto.setBusinessOrderId(consumptionBillEventDto.getOrderId());
        freezeEventDto.setConsumptionType(FreezeEventDto.ConsumptionType.MALL_CONSUMPTION);
        freezeEventDto.setUserId(consumptionBillEventDto.getUserId());
        freezeEventDto.setRechargeBillId(consumptionBillEventDto.getRechargeBillNumber());
        freezeEventDto.setPayWay(FreezeEventDto.PayWay.BALANCE);
        EventBus.publish(FreezeEventDto.EVENT_NAME, freezeEventDto);
    }

    private void processConsumptionBill(RechargeBillDto rechargeBillDto, String accountId) {
        FreezeEventDto freezeEventDto = new FreezeEventDto();
        freezeEventDto.setBusinessOrderId(rechargeBillDto.getOrderNumber());
        freezeEventDto.setPayWay(FreezeEventDto.PayWay.valueOf(rechargeBillDto.getRechargeChannel().toString()));
        freezeEventDto.setRechargeBillId(rechargeBillDto.getBillId());
        freezeEventDto.setUserId(rechargeBillDto.getUserId());
        if (RechargeBillDto.RechargeType.USER_ACTIVE_RECHARGE.equals(rechargeBillDto.getRechargeType())) {
            //冻结金额
            freezeDomainService.freeze(accountId, USER_ACTIVE_PRICE);
            freezeEventDto.setConsumptionType(FreezeEventDto.ConsumptionType.USER_ACTIVE_CONSUMPTION);
            EventBus.publish(FreezeEventDto.EVENT_NAME, freezeEventDto);
        } else if (RechargeBillDto.RechargeType.VIP_RECHARGE.equals(rechargeBillDto.getRechargeType())) {
            if (StringUtils.isEmpty(rechargeBillDto.getOrderNumber())) {
                throw new BusinessException("699消费订单编号为空");
            }
            freezeDomainService.freeze(accountId, memberClient.getMemberOrderDto(rechargeBillDto.getOrderNumber()).getAmount());
            freezeEventDto.setConsumptionType(FreezeEventDto.ConsumptionType.VIP_CONSUMPTION);
            EventBus.publish(FreezeEventDto.EVENT_NAME, freezeEventDto);
        } else if (RechargeBillDto.RechargeType.AGENT_RECHARGE.equals(rechargeBillDto.getRechargeType())) {
            if (StringUtils.isEmpty(rechargeBillDto.getOrderNumber())) {
                throw new BusinessException("升级代理订单编号为空");
            }
            freezeDomainService.freeze(accountId, memberClient.getAgentOrderPrice(rechargeBillDto.getOrderNumber()));
            freezeEventDto.setConsumptionType(FreezeEventDto.ConsumptionType.AGENT_CONSUMPTION);
            EventBus.publish(FreezeEventDto.EVENT_NAME, freezeEventDto);
        } else if (RechargeBillDto.RechargeType.MALL_CONSUMPTION_RECHARGE.equals(rechargeBillDto.getRechargeType())) {
            if (StringUtils.isEmpty(rechargeBillDto.getOrderNumber())) {
                throw new BusinessException("商城购物订单编号为空");
            }
            freezeDomainService.freeze(accountId, orderClient.getOrderPrice(rechargeBillDto.getOrderNumber()));
            freezeEventDto.setConsumptionType(FreezeEventDto.ConsumptionType.MALL_CONSUMPTION);
            EventBus.publish(FreezeEventDto.EVENT_NAME, freezeEventDto);
        } else if (RechargeBillDto.RechargeType.PURCHASE.equals(rechargeBillDto.getRechargeType())) {
            if (StringUtils.isEmpty(rechargeBillDto.getOrderNumber())) {
                throw new BusinessException("采购单号为空");
            }
            freezeDomainService.freeze(accountId, purchaseClient.getAmount(rechargeBillDto.getOrderNumber()));
            freezeEventDto.setConsumptionType(FreezeEventDto.ConsumptionType.PURCHASE);
            EventBus.publish(FreezeEventDto.EVENT_NAME, freezeEventDto);
        }
        // 单纯充值无需发送冻结事件
    }

    @Override
    public Map<String, String> getBillStatus(String billId) {
        if (StringUtils.isNotEmpty(billId)) {
            RechargeBill rechargeBill = billRepositoryLoader.create(RechargeBill.class, StringIdentifier.valueOf(billId));
            if (rechargeBill != null) {
                Map<String, String> resultMap = new HashMap<>(1);
                resultMap.put("payStatus", rechargeBill.getStatus().toString());
                return resultMap;
            }
        }
        return null;
    }

    /**
     * IM推送消息
     *
     * @param billNo
     * @param introducerId
     */
    private void sendImMessageEvent(String billNo, String title, String introducerId, MessageType messageType, String content) {
        //推送推荐消息
        try {
            MessageEventDto messageEventDto = new MessageEventDto();
            messageEventDto.setMessageType(messageType);
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("objectId", billNo);
            map.put("title", title);
            map.put("content", content);
            if (MessageType.REWARD_INVITATION.equals(messageType) || MessageType.REWARD_UPGRADE.equals(messageType)) {
                map.put("type", "CONSUMPTION");
            } else if (MessageType.REWARD_STANDARD.equals(messageType)) {
                map.put("type", "REWARD");
            }
            map.put("url","https://file.ccuol.com/image/ic_xiaofei%403x.png");
            messageEventDto.setContent(JsonUtils.toJsonString(map));
            messageEventDto.setReceiveUser(introducerId);
            System.out.println("********开始发送消息**********"+JsonUtils.toJsonString(messageEventDto));
            EventBus.publish(MessageEventDto.EVENT_NAME, messageEventDto);
        } catch (Exception e) {
            log.error("推送IM错误",e.getMessage());
        }
    }

    @Override
    public String generateBillId() {
        return IdGenerator.getInstance("billIdGenerator").generate(Bill.class).toValue();
    }

    @Override
    public void createRechargeBill(RechargeBillDto rechargeBillDto) {
        String orderNumber = rechargeBillDto.getOrderNumber();
        if (StringUtils.isNotEmpty(orderNumber)) {
            int paidCount = billQuery.selectPaidCountByOrderNumber(orderNumber);
            if (paidCount > 0) {
                throw new BusinessException("该订单已支付", orderNumber);
            }
        }
        RechargeBill rechargeBill = billConstructLoader.create(RechargeBill.class, StringIdentifier.valueOf(rechargeBillDto.getBillId()));
        String userId = rechargeBillDto.getUserId();
        rechargeBill.init(userId, LocalDateTime.now(), rechargeBillDto.
                        getRechargeChannel().toString(), rechargeBillDto.getRechargeAmount(), rechargeBillDto.getRequestParam(),
                rechargeBillDto.getChannelSerial(), rechargeBillDto.getRechargeType().toString(), rechargeBillDto.getOrderNumber(), rechargeBillDto.getRechargeAmount());
        rechargeBill.createFundingDetail(userClient.getAccountIdByPeopleId(userId), rechargeBillDto.getRechargeAmount(),
                cloudConfigProperties.getAccount().getAccountId(), cloudConfigProperties.getFee().getBankPercent());
    }

    @Override
    public PageResult<BillDto> getBillList(String userId, int pageIndex, int pageSize) {
        String userAccountId = userClient.getAccountIdByPeopleId(userId);
        PageList<String> pageList = billQuery.selectBillIdsByAccountId(userAccountId, PageParames.create(pageIndex, pageSize == 0 ? 10 : pageSize));
        List<Bill> billList = pageList.getList().stream().map(p -> {
            System.out.println(p);
            return billRepositoryLoader.
                    create(StringIdentifier.valueOf(p));
        }).collect(Collectors.toList());
        List<BillDto> billDtos = billList.stream().map(p -> dozerBeanMapper.map(p, BillDto.class)).collect(Collectors.toList());
        billDtos.forEach(p -> {
            if (Bill.Type.WITHDRAW.toString().equals(p.getType())) {
                WithdrawBill withdrawBill = billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(p.getId()));
                p.setTitle("提现");
                p.setAmount(withdrawBill.getWithdrawAmount());
            } else {
                p.setTitle(fundingDetailQuery.selectTitleByAccountIdAndBillId(userAccountId, p.getId()));
                p.setAmount(fundingDetailQuery.selectBillAmountByAccountIdAndBillId(userAccountId, p.getId()));
            }
        });
        return new PageResult<>(pageList.getTotalCount(), pageList.getPageSize(), billDtos);
    }

    @Override
    public PageResult<WithdrawBillDetailDto> searchWithdrawBill(String cardNumber, String billId, String startTime, String endTime, String status, String userId, String withdrawBankCardUserName, String withdrawType, int pageIndex, int pageSize) {
        PageList<String> pageList = billQuery.searchWithdrawBill(cardNumber, billId, startTime, endTime, status, userId, withdrawBankCardUserName, withdrawType, PageParames.create(pageIndex, pageSize == 0 ? 10 : pageSize));
        List<WithdrawBill> withdrawBills = pageList.getList().stream().map(p -> billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(p))).collect(Collectors.toList());
        List<WithdrawBillDetailDto> withdrawBillDetailDtos = withdrawBills.stream().map(p ->
                dozerBeanMapper.map(p, WithdrawBillDetailDto.class)).collect(Collectors.toList());
        return new PageResult<>(pageList.getTotalCount(), pageList.getPageSize(), withdrawBillDetailDtos);
    }

    @Override
    public List<WithdrawBillExportDto> withdrawBillExport(String cardNumber, String billId, String startTime, String endTime, String status, String userId, List<String> billIds) {
        List<String> billIdStrs;
        if (billIds != null && !billIds.isEmpty()) {
            billIdStrs = billQuery.searchWithdrawBillByBillIds(billIds);
        } else {
            billIdStrs = billQuery.searchWithdrawBillBySize(cardNumber, billId, startTime, endTime, status, userId, null, "PRIVATE");
        }
        List<WithdrawBill> withdrawBills = billIdStrs.stream().map(p -> billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(p))).collect(Collectors.toList());
        withdrawBills.stream().forEach(p -> {
            p.changeProcessTime();
        });
        List<WithdrawBillExportDto> withdrawBillExportDtos = withdrawBills.stream().map(p -> dozerBeanMapper.map(p, WithdrawBillExportDto.class)).collect(Collectors.toList());
        withdrawBillExportDtos.stream().forEach(p -> {
            p.setPayableAmount(p.getWithdrawAmount().subtract(p.getFee()));
        });
        return withdrawBillExportDtos;
    }

    @Override
    public List<PublicWithdrawBillExportDto> publicWithdrawBillExport(String cardNumber, String billId, String startTime, String endTime, String status, String companyName, List<String> billIds) {
        List<String> billIdStrs;
        if (billIds != null && !billIds.isEmpty()) {
            billIdStrs = billQuery.searchPublicWithdrawBillByBillIds(billIds);
        } else {
            billIdStrs = billQuery.searchWithdrawBillBySize(cardNumber, billId, startTime, endTime, status, null, companyName, "PUBLIC");
        }
        List<WithdrawBill> withdrawBills = billIdStrs.stream().map(p -> billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(p))).collect(Collectors.toList());
        withdrawBills.stream().forEach(p -> {
            p.changeProcessTime();
        });
        List<PublicWithdrawBillExportDto> publicWithdrawBillExportDtos = withdrawBills.stream().map(p -> dozerBeanMapper.map(p, PublicWithdrawBillExportDto.class)).collect(Collectors.toList());
        return publicWithdrawBillExportDtos;
    }

    @Override
    public void changePublicWithdrawBillInvoiceStatus(String billId) {
        WithdrawBill withdrawBill = billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(billId));
        withdrawBill.changeInvoiceStatus();
    }

    @Override
    public void checkWithdrawBill(WithdrawBillDetailDto withdrawBillDetailDto) {
        WithdrawBill withdrawBill = billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(withdrawBillDetailDto.getBillId()));
        String accountId = userClient.getAccountIdByPeopleId(withdrawBill.getUserId());
        withdrawBill.changeBillStatus(withdrawBillDetailDto);
        if (WithdrawBillDetailDto.Status.CHECK_FAIL == withdrawBillDetailDto.getStatus()) {
            unfrezzDomainService.withdrawUnFreeze(accountId, withdrawBill.getWithdrawAmount());
            withdrawBill.changeProcessTime();
            withdrawBill.changeFinishTime();
            withdrawBill.changeInvoiceStatusToUnreceived();
        } else if (WithdrawBillDetailDto.Status.CHECK_SUCCESS == withdrawBillDetailDto.getStatus()) {
            withdrawBill.changeInvoiceStatusToReceived();
            withdrawBill.changeProcessTime();
        }
    }

    @Override
    public void finishWithdrawBill(WithdrawBillDetailDto withdrawBillDetailDto) {
        WithdrawBill withdrawBill = billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(withdrawBillDetailDto.getBillId()));
        String accountId = userClient.getAccountIdByPeopleId(withdrawBill.getUserId());
        withdrawBill.changeBillStatus(withdrawBillDetailDto);
        if (WithdrawBillDetailDto.Status.PROCESS_FAIL == withdrawBillDetailDto.getStatus()) {
            unfrezzDomainService.withdrawUnFreeze(accountId, withdrawBill.getWithdrawAmount());
        } else if (WithdrawBillDetailDto.Status.PROCESS_SUCCESS == withdrawBillDetailDto.getStatus()) {
            withDrawDomainService.withdraw(Constants.Source.BANK, withdrawBill.getId().toValue(), withdrawBill.getFundingDetails().stream().filter(j -> j.getAmount().
                    compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList()).
                    stream().map(w -> dozerBeanMapper.map(w, FundingDetailDto.class)).collect(Collectors.toList()));
        }
        withdrawBill.changeFinishTime();
    }

    @Override
    public void withdrawBillImport(WithdrawBillImportDto withdrawBillImportDto) {
        List<WithdrawBillExportDto> list = new ArrayList<>();
        if (withdrawBillImportDto.getWithdrawBillExportDtos() != null) {
            list.addAll(withdrawBillImportDto.getWithdrawBillExportDtos());
        } else if (withdrawBillImportDto.getPublicWithdrawBillImportDto() != null) {
            withdrawBillImportDto.getPublicWithdrawBillImportDto().forEach(p -> {
                WithdrawBillExportDto withdrawBillExportDto = dozerBeanMapper.map(p, WithdrawBillExportDto.class);
                list.add(withdrawBillExportDto);
            });
        }
        list.stream().forEach(p -> {
            WithdrawBill withdrawBill = billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(p.getBillId()));
            String accountId = userClient.getAccountIdByPeopleId(withdrawBill.getUserId());
            if (WithdrawBill.Status.CHECK_SUCCESS.equals(withdrawBill.getStatus()) && WithdrawBill.Status.PROCESS_SUCCESS.toString().equals(p.getStatus())) {
                withdrawBill.changeStatusToSuccess();
                withDrawDomainService.withdraw(Constants.Source.BANK, withdrawBill.getId().toValue(), withdrawBill.getFundingDetails().stream().filter(j -> j.getAmount().
                        compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList()).
                        stream().map(w -> dozerBeanMapper.map(w, FundingDetailDto.class)).collect(Collectors.toList()));
                withdrawBill.changeFinishTime();
            } else if (WithdrawBill.Status.CHECK_SUCCESS.equals(withdrawBill.getStatus()) && WithdrawBill.Status.PROCESS_FAIL.toString().equals(p.getStatus())) {
                withdrawBill.changeStatusToFail(p.getRemark());
                unfrezzDomainService.withdrawUnFreeze(accountId, withdrawBill.getWithdrawAmount());
                withdrawBill.changeFinishTime();
            } else {
                throw new BusinessException("重复导入或数据异常");
            }
        });
    }

    @Override
    public WithdrawBillDetailDto getWithdrawBillDetail(String userId, String billId) {
        WithdrawBill withdrawBill = billRepositoryLoader.create(WithdrawBill.class, StringIdentifier.valueOf(billId));
        WithdrawBillDetailDto withdrawBillDetailDto = dozerBeanMapper.map(withdrawBill, WithdrawBillDetailDto.class);
        return withdrawBillDetailDto;
    }

    @Override
    public ConsumptionBillDetailDto getConsumptionBillDetail(String userId, String billId) {
        String userAccountId = userClient.getAccountIdByPeopleId(userId);
        ConsumptionBill consumptionBill = billRepositoryLoader.create(ConsumptionBill.class, StringIdentifier.valueOf(billId));
        ConsumptionBillDetailDto consumptionBillDetailDto = dozerBeanMapper.map(consumptionBill, ConsumptionBillDetailDto.class);
        consumptionBillDetailDto.setAmount(fundingDetailQuery.selectBillAmountByAccountIdAndBillId(userAccountId, billId));
        consumptionBillDetailDto.setTitle(fundingDetailQuery.selectTitleByAccountIdAndBillId(userAccountId, billId));
        List<com.zqkh.wallet.feign.dto.FundingDetailDto> fundingDetailDtos = consumptionBillDetailDto.getFundingDetails().stream().filter(p -> p.getAccountId().equals(userAccountId)).collect
                (Collectors.toList());
        fundingDetailDtos.forEach(j -> j.setCreateTime(consumptionBillDetailDto.getCreateTime()));
        consumptionBillDetailDto.setFundingDetails(fundingDetailDtos);
        if (!userId.equals(consumptionBill.getUserId())) {
            consumptionBillDetailDto.setShowType(ConsumptionBillDetailDto.ShowType.REWARD);
        } else if (ConsumptionBill.ConsumptionType.MALL_CONSUMPTION.equals(consumptionBill.getConsumptionType())) {
            consumptionBillDetailDto.setGoodsName(orderClient.getItemsName(consumptionBill.getBusinessOrderId()).get(0));
            consumptionBillDetailDto.setShowType(ConsumptionBillDetailDto.ShowType.MALL_CONSUMPTION);
        } else if (ConsumptionBill.ConsumptionType.UP_TO_VIP.equals(consumptionBill.getConsumptionType())) {
            consumptionBillDetailDto.setShowType(ConsumptionBillDetailDto.ShowType.VIP_CONSUMPTION);
            consumptionBillDetailDto.setGoodsName(memberClient.getGoodNameBymemberOrderId(consumptionBill.getBusinessOrderId()).get(0));
        } else if (ConsumptionBill.ConsumptionType.PURCHASE.equals(consumptionBill.getConsumptionType())) {
            consumptionBillDetailDto.setGoodsName("会员名额");
            consumptionBillDetailDto.setShowType(ConsumptionBillDetailDto.ShowType.MALL_CONSUMPTION);
        } else {
            consumptionBillDetailDto.setShowType(ConsumptionBillDetailDto.ShowType.ACTIVE_AGENT);
        }
        return consumptionBillDetailDto;
    }

    @Override
    public RechargeBillDetailDto getRechargeBillDetail(String userId, String billId) {
        String userAccountId = userClient.getAccountIdByPeopleId(userId);
        RechargeBill rechargeBill = billRepositoryLoader.create(RechargeBill.class, StringIdentifier.valueOf(billId));
        RechargeBillDetailDto rechargeBillDetailDto = dozerBeanMapper.map(rechargeBill, RechargeBillDetailDto.class);
        rechargeBillDetailDto.setAmount(fundingDetailQuery.selectBillAmountByAccountIdAndBillId(userAccountId, billId));
        rechargeBillDetailDto.setTitle(fundingDetailQuery.selectTitleByAccountIdAndBillId(userAccountId, billId));
        List<com.zqkh.wallet.feign.dto.FundingDetailDto> fundingDetailDtos = rechargeBillDetailDto.getFundingDetails().stream().filter(p -> p.getAccountId().equals(userAccountId)).collect
                (Collectors.toList());
        fundingDetailDtos.forEach(j -> j.setCreateTime(rechargeBillDetailDto.getCreateTime()));
        rechargeBillDetailDto.setFundingDetails(fundingDetailDtos);
        return rechargeBillDetailDto;
    }

    @Override
    public RewardBillDetailDto getRewardBillDetail(String userId, String billId) {
        String userAccountId = userClient.getAccountIdByPeopleId(userId);
        RewardBill rewardBill = billRepositoryLoader.create(RewardBill.class, StringIdentifier.valueOf(billId));
        RewardBillDetailDto rewardBillDetailDto = dozerBeanMapper.map(rewardBill, RewardBillDetailDto.class);
        rewardBillDetailDto.setAmount(fundingDetailQuery.selectBillAmountByAccountIdAndBillId(userAccountId, billId));
        rewardBillDetailDto.setTitle(fundingDetailQuery.selectTitleByAccountIdAndBillId(userAccountId, billId));
        List<com.zqkh.wallet.feign.dto.FundingDetailDto> fundingDetailDtos = rewardBillDetailDto.getFundingDetails().stream().filter(p -> p.getAccountId().equals(userAccountId)).collect
                (Collectors.toList());
        fundingDetailDtos.forEach(j -> j.setCreateTime(rewardBillDetailDto.getCreateTime()));
        rewardBillDetailDto.setFundingDetails(fundingDetailDtos);
        return rewardBillDetailDto;
    }

}
