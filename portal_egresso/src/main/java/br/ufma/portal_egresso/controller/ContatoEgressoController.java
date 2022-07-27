package br.ufma.portal_egresso.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/contato_egresso")
public class ContatoEgressoController {

    @Autowired
    ContatoEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    ContatoService serviceContato;

    @PostMapping("/listar")
    public ResponseEntity listar(@RequestParam ContatoEgressoDTO request) {
        try {
            Egresso egresso = serviceEgresso.buscar_por_id(request.getEgresso_id());
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

        ContatoEgresso contatoEgresso = ContatoEgresso.builder()
                .egresso(egresso)
                .contato(contato)
                .descricao(request.getDescricao()).build();
        try {

            service.salvar(contatoEgresso);
            return ResponseEntity.ok(contatoEgresso);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}