package br.ufma.portal_egresso.controller;


import java.util.List;

import br.ufma.portal_egresso.entidade.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.dto.ContatoDTO;
import br.ufma.portal_egresso.service.ContatoService;

import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/contatos")
public class ContatoController {

    @Autowired
    ContatoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Contato> contatos = service.listar();
            return ResponseEntity.ok(contatos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody ContatoDTO request) {
        Contato contato = Contato.builder()
                .nome(request.getNome())
                .url_logo(request.getUrl_logo()).build();
        try {
            Contato contato_salvo = service.salvar(contato);
            return ResponseEntity.ok(contato_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/remover")
    public ResponseEntity remover(@RequestParam("id_contato") Long id_contato) {

        try {
            service.remover(id_contato);
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}