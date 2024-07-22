package br.com.study.ratelimiter.controller;

import br.com.study.ratelimiter.dto.CompraDTO;
import br.com.study.ratelimiter.service.ProcessService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/resilience")
@RequiredArgsConstructor
@Log4j2
public class ResilienceController {

	private final ProcessService processService;

	@PostMapping
	public ResponseEntity<String> simple(@Valid @RequestBody CompraDTO compra) {
		log.info("------------>> INIT APPLY TESTE SEM RESILIENCE <<--------------");
		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}
	//@Retry(name = "compra-fornecedor")
	// framework - resilience4j realiza tentativa RETRY- default 3 tentativas - compra-fornecedor 3 -
	//@Bulkhead(name = "default") // delimita quantas execuções concorrentes vão ter 
	//@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
	//@RateLimiter(name = "max-rate")
	@PostMapping("/v1")
	@RateLimiter(name = "simpleRateLimit")
	public ResponseEntity<String> simpleRateLimit(@Valid @RequestBody CompraDTO compra) {
		log.info("------------>> INIT APPLY POST simpleRateLimit <<--------------");
		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}

	@GetMapping("/v1")
	@RateLimiter(name = "simpleRateLimit")
	public ResponseEntity<String> simpleRateLimit(@RequestParam Integer secondsProcess) {
		log.info("------------>> INIT APPLY GET simpleRateLimit <<--------------");
		return ResponseEntity.ok(processService.buscar(secondsProcess)? "Efetivada com sucesso!!" : null);
	}


	@PostMapping("/v2")
	@RateLimiter(name = "rateLimiterEventsSpecific")
	public ResponseEntity<String> rateLimiterEventsSpecific(@Valid @RequestBody CompraDTO compra) {

		log.info("------------>> INIT APPLY POST rateLimiterEventsSpecific <<--------------");
		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}

	@GetMapping("/v2")
	@RateLimiter(name = "rateLimiterEventsSpecific")
	public ResponseEntity<String> rateLimiterEventsSpecific(@RequestParam Integer secondsProcess) {
		log.info("------------>> INIT APPLY GET rateLimiterEventsSpecific <<--------------");
		return ResponseEntity.ok(processService.buscar(secondsProcess)? "Efetivada com sucesso!!" : null);
	}

	@PostMapping("/v3")
	@Bulkhead(name = "bulkheadWithConcurrentCalls")
	public ResponseEntity<String> bulkheadWithConcurrentCalls(@Valid @RequestBody CompraDTO compra) {
		log.info("------------>> INIT APPLY POST bulkheadWithConcurrentCalls <<--------------");
		return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
	}

	@GetMapping("/v3")
	@RateLimiter(name = "bulkheadWithConcurrentCalls")
	public ResponseEntity<String> bulkheadWithConcurrentCalls(@RequestParam Integer secondsProcess) {
		log.info("------------>> INIT APPLY GET bulkheadWithConcurrentCalls <<--------------");
		return ResponseEntity.ok(processService.buscar(secondsProcess)? "Efetivada com sucesso!!" : null);
	}

}
