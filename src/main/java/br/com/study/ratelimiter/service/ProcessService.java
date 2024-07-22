package br.com.study.ratelimiter.service;

import br.com.study.ratelimiter.dto.CompraDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProcessService {

	private final long sleepTimeBuscar = 10 * 1000;

	private final long sleepTimeRealizar = 10 * 1000;

//	@RateLimiter(name = "simpleRateLimit") // também funciona em service
	public boolean realizar(CompraDTO compra) {

		try {
			log.info("<--> em processamento Thread ID: {} thread name: {} time: {} segundos", Thread.currentThread().getId() , Thread.currentThread().getName(),
					(sleepTimeRealizar / 1000));
			Thread.sleep(sleepTimeRealizar);
			log.info("--> fimm process thread id {} ", Thread.currentThread().getId());

		} catch (InterruptedException e) {
			log.error("!!---> erro process thread id {}, error {} ", Thread.currentThread().getId(), e.getMessage());
			e.printStackTrace();
		}

		return true;
	}

	public String buscar(String id) {

		try {
			log.info("<--> em processamento Thread ID: {} thread name: {} time: {} segundos", Thread.currentThread().getId() , Thread.currentThread().getName(),
					(sleepTimeBuscar / 1000));
			Thread.sleep(sleepTimeBuscar);
			log.info("--> fimm process thread id {} ", Thread.currentThread().getId());

		} catch (InterruptedException e) {
			log.error("!!---> erro process thread id {}, error {} ", Thread.currentThread().getId(), e.getMessage());
			e.printStackTrace();
		}

		return id;
	}

}
