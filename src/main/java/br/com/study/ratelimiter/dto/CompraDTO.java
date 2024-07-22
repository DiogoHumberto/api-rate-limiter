package br.com.study.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompraDTO {

	@Valid
	@NotEmpty
	private List<ItemCompraDTO> itens;

	@Valid
	@NotNull
	private EnderecoDTO endereco;

}
