package com.zqkh.wallet.context.configuration;

import com.jovezhao.nest.amq.AMQChannelProvider;
import com.jovezhao.nest.amq.AMQProviderConfig;
import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConfigItem;
import com.zqkh.im.event.dto.receiver.MessageEventDto;
import com.zqkh.shop.event.dto.CreateWalletAccountEventDto;
import com.zqkh.user.event.dto.UserFirstLoginEventDto;
import com.zqkh.wallet.event.dto.ConsumptionBillEventDto;
import com.zqkh.wallet.event.dto.FreezeEventDto;
import com.zqkh.wallet.event.dto.RewardBillEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wenjie
 * @date 2017/12/4 0004 15:30
 */
@Configuration
public class MQConfiguration {

    @Autowired
    private CloudConfigProperties cloudConfigProperties;

    @Bean
    public AMQProviderConfig getAMQProvider() {
        AMQProviderConfig providerConfig = new AMQProviderConfig();
        providerConfig.setSecretId(cloudConfigProperties.getAmq().getSecretId());
        providerConfig.setSecretKey(cloudConfigProperties.getAmq().getSecretKey());
        providerConfig.setEndpoint(cloudConfigProperties.getAmq().getEndpoint());
        return providerConfig;
    }

    @Bean
    public AMQChannelProvider getAMQChannelProvider(AMQProviderConfig providerConfig) {
        AMQChannelProvider channelProvider = new AMQChannelProvider(providerConfig);
        return channelProvider;
    }

    @Bean
    public EventConfigItem CreateWalletAccountEvent(ChannelProvider provider) {
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setChannelProvider(provider);
        eventConfigItem.setEventName(UserFirstLoginEventDto.EVENT_NAME);
        return eventConfigItem;
    }

    @Bean
    public EventConfigItem getUserFirstLoginEvent(ChannelProvider channelProvider) {
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setChannelProvider(channelProvider);
        eventConfigItem.setEventName(CreateWalletAccountEventDto.EVENT_NAME);
        return eventConfigItem;
    }

    @Bean
    public EventConfigItem sendMessageNotification(ChannelProvider provider) {
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setChannelProvider(provider);
        eventConfigItem.setEventName(MessageEventDto.EVENT_NAME);
        return eventConfigItem;
    }

    @Bean
    public EventConfigItem rewardBill(ChannelProvider provider) {
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setChannelProvider(provider);
        eventConfigItem.setEventName(RewardBillEventDto.EVENT_NAME);
        return eventConfigItem;
    }


    @Bean
    public EventConfigItem getCreateConsumptionBillEvent(ChannelProvider provider){
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setChannelProvider(provider);
        eventConfigItem.setEventName(ConsumptionBillEventDto.EVENT_NAME);
        return eventConfigItem;
    }


    @Bean
    public EventConfigItem getFreezeEvent(ChannelProvider provider){
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setChannelProvider(provider);
        eventConfigItem.setEventName(FreezeEventDto.EVENT_NAME);
        return eventConfigItem;
    }
}
