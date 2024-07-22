package br.com.study.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCompraDTO {

	@Pattern(regexp = "\\d+", message = "O campo deve conter apenas números")
	private String id;

	@Positive(message = "O valor deve ser um número inteiro positivo")
	private String qtd;
	
}
