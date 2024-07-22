package br.com.study.ratelimiter.controller;

import br.com.study.ratelimiter.dto.CompraDTO;
import br.com.study.ratelimiter.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/interceptor")
@RequiredArgsConstructor
public class InterceptorController {

    private final ProcessService processService;

    @PostMapping("active")
    public ResponseEntity<String> teste(@Valid @RequestBody CompraDTO compra) {

        return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
    }

    @PostMapping
    public ResponseEntity<String> teste2(@Valid @RequestBody CompraDTO compra) {

        return ResponseEntity.ok(processService.realizar(compra)? "Efetivada com sucesso!!" : null);
    }
}
