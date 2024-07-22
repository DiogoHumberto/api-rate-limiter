package br.com.study.ratelimiter.controller;

import br.com.study.ratelimiter.dto.CompraDTO;
import br.com.study.ratelimiter.service.ProcessService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/resilience")
@RequiredArgsConstructor
public class ResilienceController {

	private final ProcessService processService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ResilienceController.class);
	
	//@Retry(name = "compra-fornecedor")
	// framework - resilience4j realiza tentativa RETRY- default 3 tentativas - compra-fornecedor 3 -
	//@Bulkhead(name = "default") // delimita quantas execuções concorrentes vão ter 
	//@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
	//@RateLimiter(name = "max-rate")
	@PostMapping("v1")
	@RateLimiter(name = "simpleRateLimit")
	public ResponseEntity<String> simpleRateLimit(@Valid @RequestBody CompraDTO compra) {
		LOG.info("Realizando compra dos itens {}", compra.getItens());
		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}


	@PostMapping("v2")
	@RateLimiter(name = "rateLimiterEventsSpecific", fallbackMethod = "fallbackMethod")
	public ResponseEntity<String> rateLimiterEventsSpecific(@Valid @RequestBody CompraDTO compra) {

		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}

	@PostMapping("v3")
	@RateLimiter(name = "myDefaultRateLimiter", fallbackMethod = "fallbackMethod")
	public ResponseEntity<String> realizaCompra2(@Valid @RequestBody CompraDTO compra) {

		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<String> fallbackMethod(Throwable t){
		//Tratando a exception -- verificando erro
		//armazenando em Redis (Cache) para processar posterior
		//Retornando as informações 
		throw new RuntimeException("fallbacl atcion");
	}


}
