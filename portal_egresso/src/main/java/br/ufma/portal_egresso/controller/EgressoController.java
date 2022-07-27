package br.ufma.portal_egresso.controller;

import java.util.ArrayList;
import java.time.LocalDate;
import java.security.Principal;
import java.util.List;

import br.ufma.portal_egresso.entidade.dto.EgressoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.portal_egresso.entidade.dto.ContatoEgressoDTO;
import br.ufma.portal_egresso.entidade.Cargo;
import br.ufma.portal_egresso.entidade.Contato;
import br.ufma.portal_egresso.entidade.ContatoEgresso;
import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.FaixaSalario;
import br.ufma.portal_egresso.entidade.ProfEgresso;
import br.ufma.portal_egresso.service.EgressoService;
import br.ufma.portal_egresso.service.CargoService;
import br.ufma.portal_egresso.service.ContatoService;
import br.ufma.portal_egresso.service.CursoService;
import br.ufma.portal_egresso.service.FaixaSalarioService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/egresso")
public class EgressoController {

    @Autowired
    EgressoService service;

    @Autowired
    CursoService serviceCurso;
    @Autowired
    ContatoService serviceContato;
    @Autowired
    CargoService serviceCargo;
    @Autowired
    FaixaSalarioService serviceFaixaSalario;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<String> egressos = service.listar();
            return ResponseEntity.ok(egressos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/salvar")
    public ResponseEntity salvarEgresso (@RequestBody EgressoDTO request){
        Egresso egresso = Egresso.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .resumo(request.getResumo())
                .url_foto(request.getUrl_foto())
                .build();
        try {
            Egresso salvo = service.salvarEgresso(egresso);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        }catch (RegraNegocioRunTime ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/buscar_dados_egresso")
    public ResponseEntity buscar_dados_egresso(@RequestParam("id") String id) {
        try {
            Long id_egresso_long = Long.valueOf(id).longValue();
            Egresso egresso = service.buscar_por_id(id_egresso_long);
            return ResponseEntity.ok(egresso);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestParam("id") String id,
                                 @RequestParam("nome") String nome,
                                 @RequestParam("email") String email,
                                 @RequestParam("cpf") String cpf,
                                 @RequestParam("resumo") String resumo) {
        try {
            Long id_egresso_long = Long.valueOf(id).longValue();
            Egresso egresso = Egresso.builder()
                    .id(id_egresso_long)
                    .nome(nome)
                    .email(email)
                    .cpf(cpf)
                    .resumo(resumo)
                    .build();

            Egresso salvo = service.editar(egresso);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/dados_egresso")
    public ResponseEntity busca_dados_pagina_egresso(@RequestParam("id") String id) {
        try {
            Long id_egresso_long = Long.valueOf(id).longValue();
            Egresso egresso = service.busca_dados_pagina_egresso(id_egresso_long);
            return ResponseEntity.ok(egresso);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/egresso", method = RequestMethod.GET)
    @ResponseBody
    public Egresso currentUser(Principal principal) {
        return service.obterEgressoPorEmail(principal.getName());
    }

}