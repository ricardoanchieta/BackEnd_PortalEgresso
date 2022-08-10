package br.ufma.portal_egresso.controller;


import java.util.List;

import br.ufma.portal_egresso.entidade.dto.ContatoEgressoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.portal_egresso.entidade.dto.FaixaSalarioDTO;
import br.ufma.portal_egresso.entidade.FaixaSalario;
import br.ufma.portal_egresso.service.FaixaSalarioService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/faixa_salario")
public class FaixaSalarioController {

    @Autowired
    FaixaSalarioService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<FaixaSalario> salarios = service.listar();
            return ResponseEntity.ok(salarios);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody FaixaSalarioDTO request) {
        FaixaSalario faixa_salario = FaixaSalario.builder()
                .descricao(request.getDescricao()).build();
        try {
            FaixaSalario faixa_salario_salvo = service.salvar(faixa_salario);
            return ResponseEntity.ok(faixa_salario_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody FaixaSalarioDTO request) {
        try {
            FaixaSalario faixa_salario = FaixaSalario.builder()
                    .id(request.getId())
                    .descricao(request.getDescricao()).build();

            FaixaSalario salvo = service.editar(faixa_salario);
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