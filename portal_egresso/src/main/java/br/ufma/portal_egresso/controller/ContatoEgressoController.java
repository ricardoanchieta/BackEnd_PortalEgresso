package br.ufma.portal_egresso.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.portal_egresso.entidade.dto.ContatoEgressoDTO;
import br.ufma.portal_egresso.entidade.dto.EgressoDTO;
import  br.ufma.portal_egresso.entidade.Contato;
import  br.ufma.portal_egresso.entidade.ContatoEgresso;
import  br.ufma.portal_egresso.entidade.ContatoEgressoPK;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.service.ContatoEgressoService;
import br.ufma.portal_egresso.service.EgressoService;
import br.ufma.portal_egresso.service.ContatoService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/contato_egresso")
public class ContatoEgressoController {

    @Autowired
    ContatoEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    ContatoService serviceContato;

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") Long id_egresso) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso);
            List<ContatoEgresso> contatos = service.buscar_por_Egresso(egresso);

            return ResponseEntity.ok(contatos); 
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody ContatoEgressoDTO request) {
        Egresso egresso = serviceEgresso.buscar_por_id(request.getEgresso_id());
        Contato contato = serviceContato.buscarPorId(request.getContato_id());

        ContatoEgressoPK contatoEgressoPK = ContatoEgressoPK.builder()
                .contatoId(request.getContato_id())
                .egressoId(request.getEgresso_id())
                .build();

        ContatoEgresso contatoEgresso = ContatoEgresso.builder()
                .id(contatoEgressoPK)
                .egresso(egresso)
                .contato(contato)
                .build();

        try {

            service.salvar(contatoEgresso);
            return ResponseEntity.ok(contatoEgresso);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}