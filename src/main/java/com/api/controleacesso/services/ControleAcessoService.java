package com.api.controleacesso.services;

import com.api.controleacesso.dtos.ControleAcessoDTORequest;
import com.api.controleacesso.dtos.ControleAcessoDTOResponse;
import com.api.controleacesso.dtos.mapper.ControleAcessoMapper;
import com.api.controleacesso.models.ControleAcessoModel;
import com.api.controleacesso.repositorys.ControleAcessoRepository;
import com.api.controleacesso.repositorys.filter.ControleAcessoFiltro;
import com.api.controleacesso.repositorys.projections.ControleAcessoModelProjecao;
import com.api.controleacesso.services.exception.RegraNegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Service
public class ControleAcessoService {

	private static final Logger LOG = LoggerFactory.getLogger(ControleAcessoService.class);

	private final ControleAcessoRepository controleAcessoRepository;
	private final ControleAcessoMapper mapper;

	public ControleAcessoService(ControleAcessoRepository controleAcessoRepository, ControleAcessoMapper mapper) {
		this.controleAcessoRepository = controleAcessoRepository;
		this.mapper = mapper;
	}

	@Transactional
	public ControleAcessoDTOResponse salvar(ControleAcessoDTORequest request) {
		LOG.info("Inserir controle de acesso - body: {}", request);

		if (controleAcessoRepository.existsByNumeroPlaca(request.getNumeroPlaca())) {
			throw new RegraNegocioException("Placa do veículo já está em uso");
		}

		if (controleAcessoRepository.existsByNumeroVaga(request.getNumeroVaga())) {
			throw new RegraNegocioException("A vaga de estacionamento já está em uso");
		}
		
		if (controleAcessoRepository.existsByNumeroApartamentoAndBloco(request.getNumeroApartamento(), request.getBloco())) {
			throw new RegraNegocioException("Vaga de Estacionamento já cadastrada para este apartamento/bloco");
		}

		var controleAcesso = mapper.dtoToEntity(request);
		controleAcesso.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));

		return mapper.toDto(controleAcessoRepository.save(controleAcesso));
	}

	public boolean existsByNumeroDaPlaca(String numeroDaPlaca) {
		LOG.info("Buscar veículo por número da placa: {}", numeroDaPlaca);
		return controleAcessoRepository.existsByNumeroPlaca(numeroDaPlaca);
	}

	public boolean existsByNumeroDaVaga(String numeroDaVaga) {
		return controleAcessoRepository.existsByNumeroVaga(numeroDaVaga);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return controleAcessoRepository.existsByNumeroApartamentoAndBloco(apartment, block);
	}

	public Page<ControleAcessoDTOResponse> findAll(Pageable pageable) {
		LOG.info("Buscar todos - {}", pageable);
		return mapper.mapEntityPageToDTO(pageable, controleAcessoRepository.findAll(pageable));
	}

	public Optional<ControleAcessoModel> findById(UUID id) {
		return controleAcessoRepository.findById(id);
	}

	@Transactional
	public void delete(ControleAcessoModel controleAcessoModel) {
		controleAcessoRepository.delete(controleAcessoModel);
	}
	
	
	public Page<ControleAcessoDTOResponse> buscarPorBlocos(String bloco, Pageable pageable){
		LOG.info("Buscar veículos registrados por blocos - Bloco: {};{}", bloco, pageable);
		var veiculosPorBloco = controleAcessoRepository.findByBloco(bloco.toUpperCase(), pageable);
		return mapper.mapEntityPageToDTO(pageable, veiculosPorBloco);
	}
	
	public Page<ControleAcessoDTOResponse> buscarVeiculosPorNumeroApartamento(String numeroApartamento, Pageable pageable){
		LOG.info("Buscar veículos registrados por número do apartamento - númeroApartamento: {};{}", numeroApartamento, pageable);
		var veiculosPorBloco = controleAcessoRepository.findByNumeroApartamento(numeroApartamento, pageable);
		return mapper.mapEntityPageToDTO(pageable, veiculosPorBloco);
	}

	public Page<ControleAcessoDTOResponse> filtrarPor(ControleAcessoFiltro filtro, Pageable pageable){
		LOG.info("Filtrar por {};{}", filtro, pageable);

		var pageControleAcesso = controleAcessoRepository.findAll((Specification<ControleAcessoModel>)(root, query, builder) -> {

			var predicates = new ArrayList<Predicate>();

			if (!ObjectUtils.isEmpty(filtro.getNumeroVaga())) {
				predicates.add(builder.equal(root.get("numeroVaga"), filtro.getNumeroVaga()));
			}

			if (!ObjectUtils.isEmpty(filtro.getNumeroPlaca())) {
				predicates.add(builder.like(builder.lower(root.get("numeroPlaca")), "%" + filtro.getNumeroPlaca().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isEmpty(filtro.getMarcaVeiculo())) {
				predicates.add(builder.like(builder.lower(root.get("marcaVeiculo")), "%" + filtro.getMarcaVeiculo().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isEmpty(filtro.getModeloVeiculo())) {
				predicates.add(builder.like(builder.lower(root.get("modeloVeiculo")), "%" + filtro.getModeloVeiculo().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isEmpty(filtro.getCorVeiculo())) {
				predicates.add(builder.like(builder.lower(root.get("corVeiculo")), "%" + filtro.getCorVeiculo().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isEmpty(filtro.getNomeResponsavel())) {
				predicates.add(builder.like(builder.lower(root.get("nomeResponsavel")), "%" + filtro.getNomeResponsavel().toLowerCase() + "%"));
			}

			if (!ObjectUtils.isEmpty(filtro.getNumeroApartamento())) {
				predicates.add(builder.equal(root.get("numeroApartamento"), filtro.getNumeroApartamento()));
			}

			if (!ObjectUtils.isEmpty(filtro.getBloco())) {
				predicates.add(builder.like(builder.lower(root.get("bloco")), "%" + filtro.getBloco().toLowerCase() + "%"));
			}

			// ORDENAÇÃO FEITA POR 'NUMERO_VAGA'
			query.orderBy(builder.asc(root.get("numeroVaga")));

			return builder.and(predicates.toArray(new Predicate[0]));
		}, pageable);

		pageControleAcesso.getTotalElements();
		pageControleAcesso.getTotalPages();
		return mapper.mapEntityPageToDTO(pageable, pageControleAcesso);
	}


	public Page<ControleAcessoDTOResponse> buscarPorNumeroPlaca(String numeroPlaca, Pageable pageable){
		LOG.info("Buscar por número da placa {};{}", numeroPlaca, pageable);
		var resultado = controleAcessoRepository.buscarPorNumeroPlaca(numeroPlaca, pageable);
		return mapper.mapEntityPageToDTO(pageable, resultado);
	}

	public Page<ControleAcessoDTOResponse> buscarVeiculoPorModeloVeiculoCorVeiculo(String modeloVeiculo, String corVeiculo, Pageable pageable){
		LOG.info("Buscar veículo modelo e cor {};{};{}", modeloVeiculo, corVeiculo, pageable);
		var pageModelResposta = controleAcessoRepository.buscarModeloVeiculoCorVeiculo(modeloVeiculo, corVeiculo, pageable);
		return mapper.mapEntityPageToDTO(pageable, pageModelResposta);
	}

	public List<ControleAcessoModelProjecao> buscarQuantidadeVeiculosPorBlocos(){
		LOG.info("Buscar quantidade de veículos por blocos");
		return controleAcessoRepository.buscarQuantidadeVeiculosPorBlocos();
	}
	
}
