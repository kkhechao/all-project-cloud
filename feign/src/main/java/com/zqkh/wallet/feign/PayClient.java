package com.zqkh.wallet.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author wenjie
 * @date 2017/12/27 0027 14:10
 */
@FeignClient("microservice-wallet-context")
public interface PayClient {

    /**
     * 得到微信支付参数
     * @return
     */
    @GetMapping("/pay/wechat/param")
    Map getWechatPayParam();

}
