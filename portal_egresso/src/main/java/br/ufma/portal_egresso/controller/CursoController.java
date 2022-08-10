package br.ufma.portal_egresso.controller;

import java.util.List;

import br.ufma.portal_egresso.entidade.dto.EgressoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.portal_egresso.entidade.dto.CursoDTO;
import br.ufma.portal_egresso.entidade.Curso;
import br.ufma.portal_egresso.service.CursoService;
import br.ufma.portal_egresso.service.exceptions.RegraNegocioRunTime;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/api/curso")
public class CursoController {

    @Autowired
    CursoService service;

    @GetMapping("/listar")
    public ResponseEntity listar() {
        try {
            List<Curso> cursos = service.listar();
            return ResponseEntity.ok(cursos);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody CursoDTO request) {
        Curso curso = Curso.builder()
                .nome(request.getNome())
                .nivel(request.getNivel()).build();
        try {
            Curso curso_salvo = service.salvar(curso);
            return ResponseEntity.ok(curso_salvo);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remover")
    public ResponseEntity remover(@RequestBody CursoDTO request) {

        try {
            service.remover(request.getId());
            return ResponseEntity.ok(true);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
