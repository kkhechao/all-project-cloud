package com.zqkh.wallet.context.configuration;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.identifier.IdGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("serialNo")
public class SerialNoGeneratorConfiguration implements IdGeneratorStrategy {

    private static int num = 1;
    private static String date;

    @Override
    public Identifier generate(Class clazz, Object object) {
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if (str.equals(date)) {
            num += 1;
        }else{
            date = str;
            num = 1;
        }
        long orderNo = Long.parseLong(date) * 10000;
        orderNo += num;
        return StringIdentifier.valueOf(orderNo + "");
    }
}
