package com.zqkh.wallet.context.appservice.impl.domain.repository.query;

import com.jovezhao.nest.PageList;
import com.jovezhao.nest.mybatis.PageParames;
import com.zqkh.common.result.PageResult;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.SerialLogDmo;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.TotalMoneyDmo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wenjie
 * @date 2018/1/2 0002 16:05
 */
public interface SerialLogQuery {

    PageList<SerialLogDmo> findByAccountId(@Param("accountId")String accountId, PageParames pageParames);

    List<TotalMoneyDmo> findTodayMoney();

    List<TotalMoneyDmo> findYesterdayTotalMoney();
}
