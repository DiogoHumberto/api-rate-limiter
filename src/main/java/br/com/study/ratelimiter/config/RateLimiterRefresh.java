package br.com.study.ratelimiter.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static br.com.study.ratelimiter.utils.ConstantsUltils.RATE_LIMITER_SIMPLE;
import static br.com.study.ratelimiter.utils.ConstantsUltils.RATE_LIMITER_SPECIFIC;

@Component
@RequiredArgsConstructor
@Log4j2
public class RateLimiterRefresh {

    private final RateLimiterProperties rateLimiterProperties;

    private final RateLimiterRegistry rateLimiterRegistry;


    @EventListener
    public void handleRefreshEvent(org.springframework.cloud.context.environment.EnvironmentChangeEvent event) {
        // Atualize o RateLimiter se a configuração relevante mudar
        updateRateLimiter();
    }

    @RefreshScope
    public void updateRateLimiter() {

        RateLimiterProperties.Configs simpleRateLimtconfig = rateLimiterProperties.getInstances().get(RATE_LIMITER_SIMPLE);

        execUpdateRegistry(simpleRateLimtconfig, RATE_LIMITER_SIMPLE);

        RateLimiterProperties.Configs specificRateLimtconfig = rateLimiterProperties.getInstances().get(RATE_LIMITER_SPECIFIC);

        execUpdateRegistry(specificRateLimtconfig, RATE_LIMITER_SPECIFIC);

        log.info("--->> call scope refrsh configs {}", rateLimiterRegistry.getAllRateLimiters());
    }

    private void execUpdateRegistry(RateLimiterProperties.Configs configs, String instanceName){

        if (configs == null) {
            log.error("RateLimiter instance {} not found in configuration.", instanceName);
            return;
        }

        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(instanceName);
        rateLimiter.changeLimitForPeriod(configs.getLimitForPeriod());
        rateLimiter.changeTimeoutDuration(configs.getTimeoutDuration());
    }

}
