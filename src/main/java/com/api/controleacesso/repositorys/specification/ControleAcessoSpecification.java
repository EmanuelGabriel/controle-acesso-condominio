package com.api.controleacesso.repositorys.specification;

import com.api.controleacesso.models.ControleAcessoModel;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author emanuel.sousa
 */
public class ControleAcessoSpecification {

    public static Specification<ControleAcessoModel> numeroPlaca(String numeroPlaca) {
        return (root, criteriaQuery, builder) ->
                builder.like(builder.lower(root.get("numeroPlaca")), "%" + numeroPlaca.toLowerCase() + "%");
    }

    public static Specification<ControleAcessoModel> numeroVaga(String numeroVaga) {
        return (root, criteriaQuery, builder) ->
                builder.equal(root.get("numeroVaga"), numeroVaga);
    }

    public static Specification<ControleAcessoModel> marcaVeiculo(String marcaVeiculo) {
        return (root, criteriaQuery, builder) ->
                builder.like(builder.lower(root.get("marcaVeiculo")), "%" + marcaVeiculo.toLowerCase() + "%");
    }

    public static Specification<ControleAcessoModel> modeloVeiculo(String modeloVeiculo) {
        return (root, criteriaQuery, builder) ->
                builder.like(builder.lower(root.get("modeloVeiculo")), "%" + modeloVeiculo.toLowerCase() + "%");
    }

    public static Specification<ControleAcessoModel> corVeiculo(String corVeiculo) {
        return (root, criteriaQuery, builder) ->
                builder.like(builder.lower(root.get("corVeiculo")), "%" + corVeiculo.toLowerCase() + "%");
    }


    public static Specification<ControleAcessoModel> nomeResponsavel(String nomeResponsavel) {
        return (root, criteriaQuery, builder) ->
                builder.like(builder.lower(root.get("nomeResponsavel")), "%" + nomeResponsavel.toLowerCase() + "%");
    }

    public static Specification<ControleAcessoModel> numeroApartamento(String numeroApartamento) {
        return (root, criteriaQuery, builder) ->
                builder.equal(root.get("numeroApartamento"), numeroApartamento);
    }

    public static Specification<ControleAcessoModel> bloco(String bloco) {
        return (root, criteriaQuery, builder) ->
                builder.like(builder.lower(root.get("bloco")), "%" + bloco.toLowerCase() + "%");
    }


}
