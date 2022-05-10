package com.api.controleacesso.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_CONTROLE_ACESSO") 
public class ControleAcessoModel implements Serializable {
	
    private static final long serialVersionUID = 1L ;

    @Id /*IDENTIFICADOR*/
    @GeneratedValue(strategy = GenerationType.AUTO) /*GERADOR DE ID AUTOMATICO - NÃO PRECISA DE IMPLEMENTAÇÃO*/
    private UUID id; /*TIPO DE IDENTIFICADOR (UUID)*/
    
    @Column(nullable = false, unique = true, length = 10) /*CAMPO Ñ PODE SER NULO, CAMPO UNICO (Ñ PODE TER 2 CADASTRO, MAX 10 CARACTERES*/
    private String numeroVaga; /*Nº DA VAGA DO MORADOR*/
    
    @Column(nullable = false, unique = true, length = 10) /*CAMPO Ñ PODE SER NULO, CAMPO UNICO (Ñ PODE TER PLACAS REPTIDAS, MAX 10 CARACTERES*/
    private String numeroPlaca; /*Nº DA PLACA*/
    
    @Column(nullable = false, length = 70) /*CAMPO Ñ PODE SER NULO, MAX 70 CARACTERES*/
    private String marcaVeiculo; /*MARCA DO CARRO*/
    
    @Column(nullable = false, length = 70) /*CAMPO Ñ PODE SER NULO, MAX 70 CARACTERES*/
    private String modeloVeiculo; /*MODELO DO CARRO*/
    
    @Column(nullable = false, length = 50) /*CAMPO Ñ PODE SER NULO, MAX 70 CARACTERES*/
    private String corVeiculo; /*COR DO CARRO*/
    
    @Column(nullable = false) /*CAMPO Ñ PODE SER NULO*/
    private LocalDateTime dataRegistro; /*DATA DO REGISTRO*/
    
    @Column(nullable = false, length = 100) /*CAMPO Ñ PODE SER NULO, MAX 130 CARACTERES*/
    private String nomeResponsavel; /*NOME DO RESPONSAVEL PELO VEICULO - PROPRIETARIO/INQUILINO */
    
    @Column(nullable = false, length = 10) /*CAMPO Ñ PODE SER NULO, MAX 30 CARACTERES*/
    private String numeroApartamento; /*Nº DO APARTAMENTO*/
    
    @Column(nullable = false, length = 30) /*CAMPO Ñ PODE SER NULO, MAX 30 CARACTERES*/
    private String bloco; /*NOME DO BLOCO*/


}
