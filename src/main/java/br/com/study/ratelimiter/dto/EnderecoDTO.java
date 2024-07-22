package br.com.study.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDTO {

	@NotBlank
	private String rua;

	@NotBlank
	@Pattern(regexp = "\\d+", message = "O campo deve conter apenas números")
	private String numero;

	@NotBlank
	private String estado;

}
