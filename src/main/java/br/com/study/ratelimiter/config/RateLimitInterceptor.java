package br.com.study.ratelimiter.config;

import br.com.study.ratelimiter.exception.TooManyRequestsException;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private ConcurrentRequestsInterceptor concurrentRequestsLimiter;

    // Mapa para armazenar os RateLimiter e Semáforos por endpoint
    private final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();
    private final Map<String, Semaphore> semaphores = new ConcurrentHashMap<>();

    private final RateLimiter rateLimiter = RateLimiter.create(1); // 1 request a cada 30 segundos

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/interceptor")) {
            if (requestURI.equals("/interceptor/active")) {
                if (!rateLimiter.tryAcquire()) {
                    throw new TooManyRequestsException("Too many requests");
                }
            }

            // Aplica controle de concorrência para os mesmos endpoints específicos
            Semaphore semaphore = semaphores.computeIfAbsent(requestURI, k -> new Semaphore(2)); // Limite de 1 requisições simultâneas
            try {
                // Adquire um slot no Semaphore para a requisição
                semaphore.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Mantém a interrupção
                throw new RuntimeException("Semaphore interrupted", e);
            }

            // Verificar limite de requisições simultâneas globalmente
            concurrentRequestsLimiter.acquire();

        }


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Libera o recurso do Semaphore após o processamento da requisição
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/interceptor")) {
            // Libera o RateLimiter se aplicável

            // Libera o Semaphore após o processamento da requisição
            Semaphore semaphore = semaphores.get(requestURI);
            if (semaphore != null) {
                semaphore.release();
            }

            // Libera o controle de concorrência globalmente
            concurrentRequestsLimiter.release();
        }
    }

}
