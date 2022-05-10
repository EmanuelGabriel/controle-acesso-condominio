package com.api.controleacesso.repositorys;

import com.api.controleacesso.models.ControleAcessoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

}
