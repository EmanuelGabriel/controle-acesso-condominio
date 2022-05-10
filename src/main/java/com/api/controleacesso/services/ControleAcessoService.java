package com.api.controleacesso.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.controleacesso.dtos.ControleAcessoDTORequest;
import com.api.controleacesso.dtos.ControleAcessoDTOResponse;
import com.api.controleacesso.dtos.mapper.ControleAcessoMapper;
import com.api.controleacesso.models.ControleAcessoModel;
import com.api.controleacesso.repositorys.ControleAcessoRepository;
import com.api.controleacesso.services.exception.RegraNegocioException;

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
	
}
