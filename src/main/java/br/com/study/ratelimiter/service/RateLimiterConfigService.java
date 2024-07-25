package br.com.study.ratelimiter.service;

import br.com.study.ratelimiter.config.PropertiesConfigLoader;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Log4j2
@Service
@RequiredArgsConstructor
public class RateLimiterConfigService {

    private final PropertiesConfigLoader propertiesConfigLoader;

    private final RateLimiterRegistry rateLimiterRegistry;

    private static final String RATE_LIMITER_NAME = "rateLimiterEventsSpecific";
    public void updateRateLimiterConfig() {

        // Ler a nova configuração
        String limitForPeriodStr = propertiesConfigLoader.getProperty("rateLimiterEventsSpecific.limitForPeriod");
        String timeoutDuration = propertiesConfigLoader.getProperty("rateLimiterEventsSpecific.timeoutDuration");

        // Atualize ou crie o RateLimiter
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(RATE_LIMITER_NAME);
        rateLimiter.changeLimitForPeriod(Integer.parseInt(limitForPeriodStr));
        rateLimiter.changeTimeoutDuration(Duration.ofSeconds(Integer.parseInt(timeoutDuration)));

        log.info(rateLimiterRegistry.getAllRateLimiters());
    }

}
