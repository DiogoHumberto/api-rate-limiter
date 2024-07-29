package br.com.study.ratelimiter.service;

import br.com.study.ratelimiter.config.PropertiesConfigLoader;
import br.com.study.ratelimiter.config.RateLimiterProperties;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;

import static br.com.study.ratelimiter.utils.ConstantsUltils.RATE_LIMITER_SPECIFIC;

@Log4j2
@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final PropertiesConfigLoader propertiesConfigLoader;

    private final RateLimiterRegistry rateLimiterRegistry;

    private final RateLimiterProperties rateLimiterProperties;

    private final RestTemplate restTemplate;

    private final Environment environment;

    @Scheduled(fixedRate = 60000, initialDelay = 60000) // A cada 60 segundos
    public void refreshConfigurations() {
        String port = environment.getProperty("local.server.port");
        String refreshUrl = "http://localhost:"+ port +"/actuator/refresh"; // URL do endpoint de refresh

        // Configurando os cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        try {
            log.info("---> init schedule refresh scoope config server url: {}", refreshUrl);
            // Enviando a solicitação
              ResponseEntity<String> response = restTemplate.exchange(refreshUrl, HttpMethod.POST, requestEntity, String.class);

            if ( !response.getStatusCode().is2xxSuccessful()) {
                log.error("!!--->> error response schedule body {}, http {}", response.getBody(), response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("!!!!--->> error execution refresh http msg {} ", e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        log.info("--->> rateLimiterProperties maps {}", rateLimiterProperties.getInstances());
        // Código que você deseja executar na inicialização do serviço
        log.info("--->> config init rateLiomiter : {} ", rateLimiterRegistry.getAllRateLimiters());
    }

    public void updateRateLimiterConfig() {

        // Ler a nova configuração
        String limitForPeriodStr = propertiesConfigLoader.getProperty("rateLimiterEventsSpecific.limitForPeriod");
        String timeoutDuration = propertiesConfigLoader.getProperty("rateLimiterEventsSpecific.timeoutDuration");

        // Atualize ou crie o RateLimiter
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(RATE_LIMITER_SPECIFIC);
        rateLimiter.changeLimitForPeriod(Integer.parseInt(limitForPeriodStr));
        rateLimiter.changeTimeoutDuration(Duration.ofSeconds(Integer.parseInt(timeoutDuration)));

        log.info(rateLimiterRegistry.getAllRateLimiters());
    }

}
