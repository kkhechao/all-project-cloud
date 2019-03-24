package com.zqkh.wallet.context.appservice.impl.domain.util;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.identifier.IdGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hty
 * @create 2018-01-23 16:38
 **/
@Component("billIdGenerator")
public class BillIdGenerator implements IdGeneratorStrategy {

    private static int num = 1;
    private static String date;

    @Override
    public synchronized Identifier generate(Class clazz, Object object) {

//        StringBuffer sb = new StringBuffer(String.valueOf(System.nanoTime())).append(String.valueOf((int)(Math.random()*(9999-1000+1))+1000));
//        return StringIdentifier.valueOf(sb.toString());

        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        if (str.equals(date)) {
            num += 1;
        } else {
            date = str;
            num = 1;
        }
        long orderNo = Long.parseLong(date) * 10000;
        orderNo += num;
        return StringIdentifier.valueOf("6666" + orderNo + "");
    }
}