package com.api.controleacesso.repositorys.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author emanuel.sousa
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControleAcessoFiltro {

    private String numeroVaga;
    private String numeroPlaca;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private String corVeiculo;
    private String nomeResponsavel;
    private String numeroApartamento;
    private String bloco;


}
