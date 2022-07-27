package br.ufma.portal_egresso.controller;


import java.util.List;

import br.ufma.portal_egresso.entidade.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.dto.ContatoDTO;
import br.ufma.portal_egresso.service.ContatoService;

import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
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

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestBody ContatoDTO request) {

        try {
            service.remover(request.getId());
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}