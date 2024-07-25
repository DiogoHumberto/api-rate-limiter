package br.com.study.ratelimiter.controller;


import br.com.study.ratelimiter.config.PropertiesConfigLoader;
import br.com.study.ratelimiter.service.RateLimiterConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

    private final PropertiesConfigLoader propertiesConfigLoader;

    private final RateLimiterConfigService rateLimiterConfigService;

    @PostMapping("/refreshRateLimiter")
    public String refreshRateLimiter() {
        propertiesConfigLoader.reloadConfig();
        rateLimiterConfigService.updateRateLimiterConfig();
        return "Rate Limiter config reloaded";
    }

    @GetMapping("/param1")
    public String getParam1() {
        return "Param1: " + propertiesConfigLoader.getProperty("my.config.param1");
    }

    @PostMapping("/refresh")
    public String refreshConfig() {
        propertiesConfigLoader.reloadConfig();
        return "Config reloaded";
    }
}
