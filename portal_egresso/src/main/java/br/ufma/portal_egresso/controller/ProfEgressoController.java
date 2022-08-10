package br.ufma.portal_egresso.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.ufma.portal_egresso.entidade.dto.FaixaSalarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.FaixaSalario;
import br.ufma.portal_egresso.entidade.ProfEgresso;
import br.ufma.portal_egresso.service.EgressoService;
import br.ufma.portal_egresso.service.ProfEgressoService;
import br.ufma.portal_egresso.service.CargoService;
import br.ufma.portal_egresso.service.FaixaSalarioService;
import br.ufma.portal_egresso.entidade.dto.ProfEgressoDTO;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/profissoes")
public class ProfEgressoController {

    @Autowired
    ProfEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    CargoService serviceCargo;

    @Autowired
    FaixaSalarioService serviceFaixaSalario;


    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<ProfEgresso> profissoes = service.buscar_por_Egresso(egresso);

            return ResponseEntity.ok(profissoes);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody ProfEgressoDTO request) {

        try {
            Egresso egresso = serviceEgresso.buscar_por_id(request.getId());
            Cargo cargo = serviceCargo.buscarPorId(request.getId_cargo());
            FaixaSalario faixaSalario = serviceFaixaSalario.buscarPorId(request.getId_faixa_salario());

            ProfEgresso prof_egr =  ProfEgresso.builder()
                    .empresa(request.getEmpresa())
                    .descricao(request.getDescricao())
                    .dataRegistro(LocalDate.now())
                    .egresso(egresso)
                    .cargo(cargo)
                    .faixaSalario(faixaSalario)
                    .build();

            ProfEgresso salvo = service.salvar(prof_egr);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") Long id,
                                 @RequestParam("id_egresso") Long id_egresso,
                                 @RequestParam("empresa") String empresa,
                                 @RequestParam("descricao") String descricao,
                                 @RequestParam("cargoId") Long cargoId,
                                 @RequestParam("salarioId") Long salarioId) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            Cargo cargo = serviceCargo.buscarPorId(cargoId);
            FaixaSalario faixaSalario = serviceFaixaSalario.buscarPorId(salarioId);

            ProfEgresso prof_egr =  ProfEgresso.builder()
                    .id(id)
                    .empresa(empresa)
                    .descricao(descricao)
                    .dataRegistro(LocalDate.now())
                    .egresso(egresso)
                    .cargo(cargo)
                    .faixaSalario(faixaSalario)
                    .build();

            ProfEgresso salvo = service.editar(prof_egr);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/remover")
    public ResponseEntity remover(@RequestParam("id") Long id) {

        try {
            service.remover(id);
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}