package com.api.controleacesso.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControleAcessoDTOResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String numeroVaga;
	private String numeroPlaca;
	private String marcaVeiculo;
	private String modeloVeiculo;
	private String corVeiculo;
	private String nomeResponsavel;
	private String numeroApartamento;
	private String bloco;

}
