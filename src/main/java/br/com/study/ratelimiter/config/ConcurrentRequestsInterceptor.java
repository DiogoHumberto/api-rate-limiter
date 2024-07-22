package br.com.study.ratelimiter.config;

import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class ConcurrentRequestsInterceptor  {

    private static final int MAX_CONCURRENT_REQUESTS = 2; // Número máximo de requisições simultâneas

    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_REQUESTS);

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release() {
        semaphore.release();
    }


//    private final Map<String, Semaphore> urlSemaphores = new ConcurrentHashMap<>();
//    private final Map<String, Integer> urlMaxConcurrentRequests = new HashMap<>();
//
//    public void addEndpoint(String endpoint, int maxConcurrentRequests) {
//        Semaphore semaphore = new Semaphore(maxConcurrentRequests);
//        urlSemaphores.put(endpoint, semaphore);
//        urlMaxConcurrentRequests.put(endpoint, maxConcurrentRequests);
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//        Semaphore semaphore = urlSemaphores.get(requestURI);
//        if (semaphore == null) {
//            return true; // Permitir o processamento normal se o endpoint não estiver configurado
//        }
//
//        boolean acquired = semaphore.tryAcquire();
//        if (!acquired) {
//            throw new TooManyRequestsException("Too many requests for endpoint: " + requestURI);
//        }
//        return true; // Permite o processamento normal da requisição
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        String requestURI = request.getRequestURI();
//        Semaphore semaphore = urlSemaphores.get(requestURI);
//        if (semaphore != null) {
//            semaphore.release(); // Libera a permissão após o término do processamento da requisição
//        }
//    }
}
