package br.ufma.portal_egresso.controller;

import java.util.ArrayList;
import java.util.List;

import br.ufma.portal_egresso.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.portal_egresso.entidade.dto.EgressoDTO;
import br.ufma.portal_egresso.entidade.dto.CursoEgressoDTO;
import br.ufma.portal_egresso.entidade.Egresso;
import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.entidade.CursoEgresso;
import br.ufma.portal_egresso.entidade.CursoEgressoPK;
import br.ufma.portal_egresso.service.CursoEgressoService;
import br.ufma.portal_egresso.service.EgressoService;
import br.ufma.portal_egresso.service.CursoService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cursos_egresso")
public class CursoEgressoController {

    @Autowired
    CursoEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    ContatoService serviceContato;

    @Autowired
    CursoService serviceCurso;

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestParam("id_egresso") String id_egresso) {
        try {
            Long id_egresso_long = Long.valueOf(id_egresso).longValue();
            Egresso egresso = serviceEgresso.buscar_por_id(id_egresso_long);
            List<CursoEgresso> cursos = service.buscar_por_Egresso(egresso);

            return ResponseEntity.ok(cursos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody CursoEgressoDTO dto) {


        try {
            Egresso egresso = serviceEgresso.buscar_por_id(dto.getId_egresso());
            Curso curso = serviceCurso.buscarPorId(dto.getId_curso());
            CursoEgresso curso_egr = CursoEgresso.builder()
                    .id(new CursoEgressoPK(egresso.getId(), curso.getId()))
                    .curso(curso)
                    .egresso(egresso)
                    .dataInicio(dto.getData_inicio())
                    .dataConclusao(dto.getData_conclusao())
                    .build();
            CursoEgresso salvo = service.salvar(curso_egr);
            return ResponseEntity.ok(salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestBody CursoEgressoPK id) {

        try {
            CursoEgresso curso_egresso = service.buscar_por_id(id);
            service.remover(curso_egresso);
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }








}