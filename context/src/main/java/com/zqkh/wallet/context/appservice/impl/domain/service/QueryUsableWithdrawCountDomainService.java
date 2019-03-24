package com.zqkh.wallet.context.appservice.impl.domain.service;

import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.zqkh.wallet.context.appservice.impl.domain.Bill;
import com.zqkh.wallet.context.appservice.impl.domain.repository.query.BillQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author hty
 * @create 2018-04-17 14:22
 **/
@Service
public class QueryUsableWithdrawCountDomainService {

    public static final int WITHDRAW_COUNT_ONE = 1;
    public static final int WITHDRAW_COUNT_ZERO = 0;

    EntityLoader<Bill> billRepositoryLoader = new RepositoryLoader<Bill>(Bill.class);

    @Autowired
    private BillQuery billQuery;

    public int QueryUsableWithdrawCountDomainService(String userId) {
        String billId = billQuery.selectLatestBillIdByUserId(userId);
        if (StringUtils.isEmpty(billId)) {
            return WITHDRAW_COUNT_ONE;
        }
        LocalDateTime createTime = billRepositoryLoader.create(StringIdentifier.valueOf(billId)).getCreateTime();
        if (getCountByDate(createTime.toLocalDate(), LocalDate.now())) {
            return WITHDRAW_COUNT_ZERO;
        } else {
            return WITHDRAW_COUNT_ONE;
        }
    }

    private boolean getCountByDate(LocalDate createTime, LocalDate now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate month;
        LocalDate month2;
        if (now.getDayOfMonth() >= 26) {
            month = LocalDate.parse(now.withDayOfMonth(26).format(formatter), formatter);
            month2 = LocalDate.parse(now.withDayOfMonth(25).plusMonths(1).format(formatter), formatter);
            return (createTime.isAfter(month) || createTime.isEqual(month)) && createTime.isBefore(month2);
        } else {
            month = LocalDate.parse(now.withDayOfMonth(26).minusMonths(1).format(formatter), formatter);
            month2 = LocalDate.parse(now.withDayOfMonth(25).format(formatter), formatter);
            return (createTime.isAfter(month) || createTime.isEqual(month)) && (createTime.isBefore(month2) || createTime.isEqual(month2));
        }
    }
}
