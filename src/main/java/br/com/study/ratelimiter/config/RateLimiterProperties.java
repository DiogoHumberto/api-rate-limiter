package br.com.study.ratelimiter.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "resilience4j.ratelimiter")
@Getter
@Setter
public class RateLimiterProperties {


    private Map<String, Configs> instances;


    @Getter
    @Setter
    @ToString
    public static class Configs {


        private int limitForPeriod;
        private Duration limitRefreshPeriod;
        private Duration timeoutDuration;
        private boolean registerHealthIndicator;
        private boolean subscribeForEvents;
        private int eventConsumerBufferSize;
    }
}
