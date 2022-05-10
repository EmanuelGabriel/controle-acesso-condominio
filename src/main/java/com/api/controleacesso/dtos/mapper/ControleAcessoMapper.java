package com.api.controleacesso.dtos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.api.controleacesso.dtos.ControleAcessoDTORequest;
import com.api.controleacesso.dtos.ControleAcessoDTOResponse;
import com.api.controleacesso.models.ControleAcessoModel;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Component
public class ControleAcessoMapper implements EntityMapper<ControleAcessoDTOResponse, ControleAcessoModel> {

	private final ModelMapper modelMapper;

	public ControleAcessoMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ControleAcessoModel toEntity(ControleAcessoDTOResponse dto) {
		return modelMapper.map(dto, ControleAcessoModel.class);
	}

	public ControleAcessoModel dtoToEntity(ControleAcessoDTORequest dto) {
		return modelMapper.map(dto, ControleAcessoModel.class);
	}

	@Override
	public ControleAcessoDTOResponse toDto(ControleAcessoModel entity) {
		return modelMapper.map(entity, ControleAcessoDTOResponse.class);
	}

	@Override
	public List<ControleAcessoModel> toEntity(List<ControleAcessoDTOResponse> dtoList) {
		return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
	}

	@Override
	public List<ControleAcessoDTOResponse> toDto(List<ControleAcessoModel> entityList) {
		return entityList.stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public Page<ControleAcessoDTOResponse> mapEntityPageToDTO(Pageable pageable, Page<ControleAcessoModel> pageEntity) {
		var listDTO = toDto(pageEntity.getContent());
		return new PageImpl<>(listDTO, pageable, pageEntity.getTotalElements());
	}

}
