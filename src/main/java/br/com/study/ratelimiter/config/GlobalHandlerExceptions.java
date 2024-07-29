package br.com.study.ratelimiter.config;

import br.com.study.ratelimiter.exception.CompraNotFound;
import br.com.study.ratelimiter.exception.ExceptionResponseDto;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

import static br.com.study.ratelimiter.utils.ConstantsUltils.MESSAGE;

@ControllerAdvice
@Log4j2
public class GlobalHandlerExceptions {

	@ExceptionHandler({RequestNotPermitted.class, BulkheadFullException.class})
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public ResponseEntity<Map<String, String>> handleBulkheadFullException( RuntimeException ex) {
		log.error("!!!-------->>>> Too Many Requests");
		return new ResponseEntity<>(Collections.singletonMap(MESSAGE, "Too many concurrent requests - please try again later")
				, HttpStatus.TOO_MANY_REQUESTS);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodParsingObjects(MethodArgumentNotValidException e) {
		List<FieldError> l = e.getFieldErrors();
		Map<String, Object> errors = new HashMap<>();

		for (FieldError f : l) {
			errors.put(f.getField(), f.getDefaultMessage());
		}

		Map<String, Object> body = new HashMap<>();
		body.put("errors", errors);
		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler(CompraNotFound.class)
	public final ResponseEntity<ExceptionResponseDto> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(new Date() , ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}



}
