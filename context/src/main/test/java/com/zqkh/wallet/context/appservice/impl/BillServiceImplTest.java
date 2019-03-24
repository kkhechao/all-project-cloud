package com.zqkh.wallet.context.appservice.impl;

import com.zqkh.wallet.context.Application;
import com.zqkh.wallet.context.appservice.inter.BillService;
import com.zqkh.wallet.context.configuration.CloudConfigProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wenjie
 * @date 2017/12/7 0007 15:29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class BillServiceImplTest {

    @Autowired
    BillService billService;

    @Autowired
    CloudConfigProperties cloudConfigProperties;

    @Test
    public void testGetConsumptionBill(){
        billService.getConsumptionBillDetail("c9b69c462bf06478396fd62c2bb152630","ce635ccf07cf74c3787bcd7776cde3386");
    }

}