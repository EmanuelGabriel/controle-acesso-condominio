package com.api.controleacesso.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import com.api.controleacesso.repositorys.filter.ControleAcessoFiltro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.controleacesso.dtos.ControleAcessoDTORequest;
import com.api.controleacesso.dtos.ControleAcessoDTOResponse;
import com.api.controleacesso.models.ControleAcessoModel;
import com.api.controleacesso.services.ControleAcessoService;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping(value = "/parking-spot", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControleAcessoController {

    private static final Logger LOG = LoggerFactory.getLogger(ControleAcessoController.class);
    private final ControleAcessoService controleAcessoService;

    public ControleAcessoController(ControleAcessoService controleAcessoService) {
        this.controleAcessoService = controleAcessoService;
    }

    @PostMapping
    public ResponseEntity<ControleAcessoDTOResponse> saveControleAcesso(@RequestBody @Valid ControleAcessoDTORequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(controleAcessoService.salvar(request));
    }

    @GetMapping
    public ResponseEntity<Page<ControleAcessoDTOResponse>> getAllControledeAcesso(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var retorno = controleAcessoService.findAll(pageable);
        return retorno != null ? ResponseEntity.status(HttpStatus.OK).body(retorno) : ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneControledeAcesso(@PathVariable(value = "id") UUID id) {
        Optional<ControleAcessoModel> parkingSpotModelOptional = controleAcessoService.findById(id);
        return parkingSpotModelOptional.<ResponseEntity<Object>>map(controleAcessoModel -> ResponseEntity.status(HttpStatus.OK).body(controleAcessoModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ControleAcessoModel> parkingSpotModelOptional = controleAcessoService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
        }
        controleAcessoService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Vaga de estacionamento excluída com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ControleAcessoDTORequest controleAcessoDTO) {
        Optional<ControleAcessoModel> parkingSpotModelOptional = controleAcessoService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
        }

        var parkingSpotModel = new ControleAcessoModel();
        BeanUtils.copyProperties(controleAcessoDTO, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setDataRegistro(parkingSpotModelOptional.get().getDataRegistro());
        return null; //ResponseEntity.status(HttpStatus.OK).body(controleAcessoService.salvar(parkingSpotModel));
    }


    @GetMapping("/por-bloco")
    public ResponseEntity<?> buscarVeiculosPorBloco(@RequestParam(value = "bloco") String bloco,
                                                    @PageableDefault(page = 0, size = 10, sort = "nomeResponsavel", direction = Sort.Direction.ASC) Pageable pageable) {
        LOG.info("GET /parking-spot/por-bloco - bloco: {};{}", bloco, pageable);
        var veiculosPorBloco = controleAcessoService.buscarPorBlocos(bloco, pageable);
        return veiculosPorBloco != null ? ResponseEntity.status(HttpStatus.OK).body(veiculosPorBloco) : ResponseEntity.notFound().build();
    }

    @GetMapping("/numero-ap")
    public ResponseEntity<?> buscarVeiculosPorNumeroDoApartamento(@RequestParam(value = "numeroAp") String numeroAp,
                                                                  @PageableDefault(page = 0, size = 10, sort = "nomeResponsavel", direction = Sort.Direction.ASC) Pageable pageable) {
        LOG.info("GET /parking-spot/numero-ap - numeroAp: {};{}", numeroAp, pageable);
        var veiculosPorBloco = controleAcessoService.buscarVeiculosPorNumeroApartamento(numeroAp, pageable);
        return veiculosPorBloco != null ? ResponseEntity.status(HttpStatus.OK).body(veiculosPorBloco) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<?> filtrarPor(
            ControleAcessoFiltro filtro,
            @PageableDefault(sort = "numeroVaga", direction = Sort.Direction.ASC) Pageable pageable) {
        LOG.info("GET /parking-spot/filtro - Filtro: {};{}", filtro, pageable);
        var filtroVagasEstacionamento = controleAcessoService.filtrarPor(filtro, pageable);
        return filtroVagasEstacionamento != null ? ResponseEntity.status(HttpStatus.OK).body(filtroVagasEstacionamento) : ResponseEntity.notFound().build();
    }
}

