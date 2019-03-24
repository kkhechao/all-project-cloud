package com.zqkh.wallet.context.appservice.common;

/**
 * @author wenjie
 * @date 2017/11/27 0027 16:59
 */
public final class Constants {

    public final class Event{
        //EventBus冻结记录通道
        public static final String FREEZE_RECORD_TOPIC = "FREEZE_RECORD";

        //EventBus流水日志通道
        public static final String SERIAL_LOG_TOPIC = "SERIAL_LOG";
    }

    public enum Source{
        BANK,
        WECHAT,
        ALIPAY
    }
}
