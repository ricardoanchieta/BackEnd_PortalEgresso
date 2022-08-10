package br.ufma.portal_egresso.controller;


import java.util.List;

import br.ufma.portal_egresso.entidade.dto.DepoimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.dto.CargoDTO;
import br.ufma.portal_egresso.service.CargoService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/cargo")
public class CargoController {

    @Autowired
    CargoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Cargo> cargos = service.listar();
            return ResponseEntity.ok(cargos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody CargoDTO request) {
        Cargo cargo = Cargo.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao()).build();
        try {
            Cargo cargo_salvo = service.salvar(cargo);
            return ResponseEntity.ok(cargo_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestBody CargoDTO request) {

        try {
            service.remover(request.getId());
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
