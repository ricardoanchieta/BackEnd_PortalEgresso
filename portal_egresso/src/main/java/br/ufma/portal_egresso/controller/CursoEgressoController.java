package br.ufma.portal_egresso.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufma.portal_egresso.service.ContatoService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/curso_egresso")
public class CursoEgressoController {

    @Autowired
    CursoEgressoService service;

    @Autowired
    EgressoService serviceEgresso;

    @Autowired
    ContatoService serviceContato;

    @Autowired
    CursoService serviceCurso;

    @CrossOrigin(origins = "http://127.0.0.1:5173")
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
    @CrossOrigin(origins = "http://127.0.0.1:5173")
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
    @CrossOrigin(origins = "http://127.0.0.1:5173")
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

//    @GetMapping("/getEgressosPorCurso")
//    public ResponseEntity getEgressosPorCurso(@RequestParam("id_curso") String id_curso){
//        try {
//            Long id_curso_long = Long.valueOf(id_curso).longValue();
//            Long quantidade = service.getQuantidadeEgressoPorCurso(id_curso_long);
//
//            return ResponseEntity.ok(quantidade);
//        } catch(RegraNegocioRunTime e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @CrossOrigin(origins = "http://127.0.0.1:5173")
    @GetMapping("/listarEgressosPorCurso")
    public ResponseEntity listaEgressosPorCurso() {

        try {
            //Curso curso = serviceCurso.buscarPorId(id_curso_long);


            List<Curso> cursos = serviceCurso.listar();
            List<String> respostaFinal = new ArrayList<>();

            for(int i=0; i< cursos.size();i++){
                String nomeCurso = cursos.get(i).getNome();
                nomeCurso = trataNome(nomeCurso);
                Long id = cursos.get(i).getId();
                Curso curso = serviceCurso.buscarPorId(id);
                List<CursoEgresso> resp = service.listarEgressosPorCurso(curso);
                Integer quantidade = resp.size();

                String conteudo = "\""+ nomeCurso +"\": \"" + quantidade + "\"";

                respostaFinal.add(conteudo);
            }

            String respostaFinalString = "{"+ respostaFinal.toString() + "}";

            respostaFinalString = respostaFinalString.replace("[","");
            respostaFinalString = respostaFinalString.replace("]","");


            //List<CursoEgresso> resp = service.listarEgressosPorCurso(curso);
            //Integer quantidade = resp.size();
            return ResponseEntity.ok(respostaFinalString);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String trataNome(String nome){
        nome = nome.replace(" ","_");
        return nome;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173")
    @GetMapping("/TotalDeEgressosEmCursos")
    public ResponseEntity listaTotalEgressosEmCursos() {
        try {
            List<CursoEgresso> egressosComCurso = service.listar();
            String quantidade = String.valueOf((egressosComCurso.size()));
            String resposta = "{\"total_egressos\": \"" + quantidade + "\"}";
            return ResponseEntity.ok(resposta);
        } catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}