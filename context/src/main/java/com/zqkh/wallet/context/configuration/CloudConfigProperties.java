package com.zqkh.wallet.context.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author wenjie
 * @date 2017/12/5 0005 15:24
 */
@Getter
@Component
@ConfigurationProperties(prefix="spring")
public final class CloudConfigProperties {

    private final Datasource datasource = new Datasource();
    private final Fee fee = new Fee();
    private final Account account = new Account();
    private final User user = new User();
    private final Redis redis = new Redis();
    private final AMQ amq = new AMQ();

    @Getter
    @Setter
    public static class AMQ{

        @Value("${secretId}")
        private String secretId;
        @Value("${secretKey}")
        private String secretKey;
        @Value("${endpoint}")
        private String endpoint;
    }


    @Setter
    @Getter
    public final static class Redis{
        @Value("${host}")
        private String host;
        @Value("${port}")
        private Integer port;
        @Value("${timeout}")
        private Integer timeout;
        @Value("${password}")
        private String password;
        @Value("${maxActive}")
        private Integer maxActive;
        @Value("${maxWait}")
        private Integer maxWait;
        @Value("${minIdle}")
        private Integer minIdle;
        @Value("${maxIdle}")
        private Integer maxIdle;
        @Value("${testOnBorrow}")
        private Boolean testOnBorrow;
        @Value("${testOnReturn}")
        private Boolean testOnReturn;
    }

    @Getter
    @Setter
    public static class User {
        @Value("${userId}")
        private String userId;
    }

    @Getter
    @Setter
    public static class Fee{
        @Value("${companyPercent}")
        private BigDecimal companyPercent;
        @Value("${bankPercent}")
        private BigDecimal bankPercent;
        @Value("${withdrawPercent}")
        private BigDecimal withdrawPercent;
    }

    @Getter
    @Setter
    public static class Account{
        @Value("${accountId}")
        private String accountId;
    }

    @Setter
    @Getter
    public static class Datasource{

        @Value("${driver-class-name}")
        private String driverClassName;
        @Value("${url}")
        private String url;
        @Value("${username}")
        private String username;
        @Value("${password}")
        private String password;
        @Value("${filters}")
        private String filters;
        @Value("${maxActive}")
        private Integer maxActive;
        @Value("${initialSize}")
        private Integer initialSize;
        @Value("${maxWait}")
        private Integer maxWait;
        @Value("${minIdle}")
        private Integer minIdle;
        @Value("${timeBetweenEvictionRunsMillis}")
        private Integer timeBetweenEvictionRunsMillis;
        @Value("${minEvictableIdleTimeMillis}")
        private Integer minEvictableIdleTimeMillis;
        @Value("${validationQuery}")
        private String validationQuery;
        @Value("${testWhileIdle}")
        private Boolean testWhileIdle;
        @Value("${testOnBorrow}")
        private Boolean testOnBorrow;
        @Value("${testOnReturn}")
        private Boolean testOnReturn;
        @Value("${maxOpenPreparedStatements}")
        private Integer maxOpenPreparedStatements;
        @Value("${removeAbandoned}")
        private Boolean removeAbandoned;
        @Value("${removeAbandonedTimeout}")
        private Integer removeAbandonedTimeout;
        @Value("${logAbandoned}")
        private Boolean logAbandoned;
        @Value("${poolPreparedStatements}")
        private Boolean poolPreparedStatements;
        @Value("${maxPoolPreparedStatementPerConnectionSize}")
        private Integer maxPoolPreparedStatementPerConnectionSize;
    }

}
