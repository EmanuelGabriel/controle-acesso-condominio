package com.api.controleacesso.repositorys;

import com.api.controleacesso.models.ControleAcessoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author emanuel.sousa
 */

@Repository
public interface ControleAcessoRepository extends JpaRepository<ControleAcessoModel, UUID>, JpaSpecificationExecutor<ControleAcessoModel> {

    boolean existsByNumeroPlaca(String numeroDaPlaca);

    boolean existsByNumeroVaga(String numeroDaVaga);

    boolean existsByNumeroApartamentoAndBloco(String apartamento, String bloco);

    Page<ControleAcessoModel> findByBloco(String bloco, Pageable pageable);

    Page<ControleAcessoModel> findByNumeroApartamento(String numeroApartamento, Pageable pageable);

    @Query("select c from ControleAcessoModel c where upper(c.numeroPlaca) = upper(?1)")
    Page<ControleAcessoModel> buscarPorNumeroPlaca(String numeroPlaca, Pageable pageable);

    @Query("select c from ControleAcessoModel c " +
            "where upper(c.modeloVeiculo) = upper(?1) and upper(c.corVeiculo) = upper(?2) " +
            "order by c.numeroVaga")
    Page<ControleAcessoModel> testTbuscarModeloVeiculoCorVeiculo(String modeloVeiculo, String corVeiculo, Pageable pageable);

    @Query("select c from ControleAcessoModel c " +
            "where upper(c.modeloVeiculo) like upper(?1) and upper(c.corVeiculo) like upper(?2) " +
            "order by c.numeroVaga")
    Page<ControleAcessoModel> test(String modeloVeiculo, String corVeiculo, Pageable pageable);

    @Query("select c from ControleAcessoModel c " +
            "where upper(c.modeloVeiculo) like upper(?1) or upper(c.corVeiculo) like upper(?2) " +
            "order by c.nomeResponsavel")
    Page<ControleAcessoModel> buscarModeloVeiculoCorVeiculo(String modeloVeiculo, String corVeiculo, Pageable pageable);




}
