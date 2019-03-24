package com.zqkh.wallet.context.appservice.impl.domain.repository.query;

import com.jovezhao.nest.PageList;
import com.jovezhao.nest.mybatis.PageParames;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hty
 * @create 2018-01-02 16:29
 **/

public interface BillQuery {

    PageList<String> selectBillIdsByAccountId(@Param("userAccountId") String userAccountId, PageParames pageParames);

    String selectBillIdByBillNumber(String billNumber);

    int selectPaidCountByOrderNumber(String orderNumber);

    String selectLatestBillIdByUserId(@Param("userId")String userId);

    PageList<String> searchWithdrawBill(
                                        @Param("cardNumber") String cardNumber,
                                        @Param("billId") String billId,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("status") String status,
                                        @Param("userId") String userId,
                                        @Param("withdrawBankCardUserName") String withdrawBankCardUserName,
                                        @Param("withdrawType") String withdrawType,
                                        PageParames pageParames);

    List<String> searchWithdrawBillBySize(@Param("cardNumber") String cardNumber,
                                          @Param("billId") String billId,
                                          @Param("startTime") String startTime,
                                          @Param("endTime") String endTime,
                                          @Param("status") String status,
                                          @Param("userId") String userId,
                                          @Param("withdrawBankCardUserName") String withdrawBankCardUserName,
                                          @Param("withdrawType") String withdrawType);

    List<String> searchWithdrawBillByBillIds(@Param("billIds") List<String> billIds);

    List<String> searchPublicWithdrawBillByBillIds(@Param("billIds")List<String> billIds);
}
