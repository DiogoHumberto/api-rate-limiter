package br.com.study.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoFornecedorDTO {
	
	private Long id;
	
	private String nome;
	
	private String estado;
	
	private String endereco;

}
