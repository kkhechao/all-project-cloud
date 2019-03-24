package com.zqkh.wallet.context.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by zhaofujun on 2017/8/13.
 */
@Configuration
public class DozerConfiguration {

    @Bean
    public DozerBeanMapper getMapper(ApplicationContext applicationContext) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList("dozer-mapper.xml"));
        applicationContext
                .getBeansOfType(BeanMappingBuilder.class)
                .forEach((p, q) -> {
                    mapper.addMapping(q);
                });
        return mapper;
    }
}
