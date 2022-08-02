package br.ufma.portal_egresso.controller;


import java.time.LocalDate;
import java.util.List;

import br.ufma.portal_egresso.entidade.dto.DepoimentoDTO;
import br.ufma.portal_egresso.entidade.dto.EgressoDTO;
import br.ufma.portal_egresso.entidade.repositorio.DepoimentoRepo;
import br.ufma.portal_egresso.entidade.repositorio.EgressoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.portal_egresso.entidade.Depoimento;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.service.DepoimentoService;
import br.ufma.portal_egresso.service.EgressoService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/depoimento")
public class DepoimentoController {

    @Autowired
    DepoimentoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    EgressoRepo egressoRepo;

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<Depoimento> depoimentos = service.buscar_por_egresso(egresso);

            return ResponseEntity.ok(depoimentos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody DepoimentoDTO request) {

        Egresso egresso = serviceEgresso.buscar_por_id(request.getId_egresso());
        Depoimento depoimento = Depoimento.builder()
                .egresso(egresso)
                .texto(request.getTexto())
                .data(request.getData() ).build();
        try {
            Depoimento depoimento_salvo = service.salvar(depoimento);
            return ResponseEntity.ok(depoimento_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody DepoimentoDTO request) {
        try {
            Depoimento dep = service.buscarPorId(request.getId());
            Depoimento depoimento = Depoimento.builder()
                    .id(request.getId())
                    .egresso(dep.getEgresso())
                    .texto(request.getTexto())
                    .data(LocalDate.now()).build();

            Depoimento salvo = service.editar(depoimento);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestBody DepoimentoDTO request) {

        try {
            service.removerPorId(request.getId());
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}