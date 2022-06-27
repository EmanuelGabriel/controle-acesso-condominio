package com.api.controleacesso.dtos.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControleAcessoDTORequest {

	@NotBlank
	private String numeroVaga;

	@NotBlank
	@Size(max = 10)
	private String numeroPlaca;

	@NotBlank
	private String marcaVeiculo;

	@NotBlank
	private String modeloVeiculo;

	@NotBlank
	private String corVeiculo;

	@NotBlank
	private String nomeResponsavel;

	@NotBlank
	private String numeroApartamento;

	@NotBlank
	private String bloco;

}
