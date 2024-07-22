package br.com.study.ratelimiter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/interceptor/**"); ///api/interceptor - especifico
    }

//    private final ConcurrentRequestsInterceptor concurrentRequestsInterceptor;
//
//    @Autowired
//    public WebConfig(ConcurrentRequestsInterceptor concurrentRequestsInterceptor) {
//        this.concurrentRequestsInterceptor = concurrentRequestsInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // Configuração do primeiro endpoint com limite de 10 requisições simultâneas
//        concurrentRequestsInterceptor.addEndpoint("/api/endpoint1", 10);
//        registry.addInterceptor(concurrentRequestsInterceptor)
//                .addPathPatterns("/api/endpoint1/**");
//
//        // Configuração do segundo endpoint com limite de 5 requisições simultâneas
//        concurrentRequestsInterceptor.addEndpoint("/api/endpoint2", 5);
//        registry.addInterceptor(concurrentRequestsInterceptor)
//                .addPathPatterns("/api/endpoint2/**");
//
//        // Não aplica o interceptor para todos os outros endpoints
//        // Neste caso, nenhum interceptor será aplicado para endpoints que não correspondem a /api/endpoint1/* e /api/endpoint2/*
//        // Isso significa que esses endpoints não terão controle de concorrência aplicado
//        registry.addInterceptor(concurrentRequestsInterceptor)
//                .excludePathPatterns("/api/endpoint1/*", "/api/endpoint2/*");
//    }
}
