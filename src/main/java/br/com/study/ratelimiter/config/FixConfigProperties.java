package br.com.study.ratelimiter.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class FixConfigProperties {

    private final String apiUrl;

    private final String apiKey;

    public FixConfigProperties(
            @Value("${api.url}") String apiUrl,
            @Value("${api.key}") String apiKey
    ) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }
}
